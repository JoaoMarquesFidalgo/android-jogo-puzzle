package org.example.fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import static org.example.fragments.R.raw.k_;


public class Fragment_jogo_puzzle extends Fragment {
    private FragmentJPListener listener;
    private TextView titulo, tempo, pontuacao, tempo_4, pontuacao_4, ocupar_4, ocupar_5, ocupar_6;
    private Button buttonPT, avancar;
    private LinearLayout palavra1, palavra2, palavra3, palavra4, palavracerta, topoesq, topodir, baixoesq,baixodir,
            imagem_centro_4, palavra1_4, palavra2_4, palavra3_4, imagem_centro_5, imagem_centro_6;
    private ImageView ajuda, ajuda_icone;
    private CountDownTimer cdTimer;
    private long total = 30000;
    private int pontuacao_int = 6, pergunta = 0;
    private int pontuacao_final = 0, pergunta_k, pergunta_c, pergunta_o, contador_tds = 0;
    private long temporizador = 0;
    private int[] arrayletras, arraytds, array;
    private TypedArray arrayResources, arrayResources_c, arrayResources_o, arrayResources_tds;
    private boolean comecou = false, resultado = false;
    private RequestQueue mQueue;
    private ProgressBar progressBar;
    private String palavra = null;
    private RelativeLayout front, back, quizz1, quizz2, quizz3;
    private ArrayList<String> lista_perguntas_k, lista_perguntas_c, lista_perguntas_o;

    public interface FragmentJPListener {
        void onInputJPSent(String planeta, String nivel, int score);
    }

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jogo_puzzle, container, false);
        lista_perguntas_k = new ArrayList<>();
        lista_perguntas_c = new ArrayList<>();
        lista_perguntas_o = new ArrayList<>();
        titulo = v.findViewById(R.id.titulo_jogo_puzzle);
        buttonPT = v.findViewById(R.id.voltar);
        palavra1 = v.findViewById(R.id.palavra1);
        palavra2 = v.findViewById(R.id.palavra2);
        palavra3 = v.findViewById(R.id.palavra3);
        palavra4 = v.findViewById(R.id.palavra4);
        topoesq = v.findViewById(R.id.topo_esq);
        topodir = v.findViewById(R.id.topo_dir);
        baixoesq = v.findViewById(R.id.baixo_esq);
        baixodir = v.findViewById(R.id.baixo_dir);
        ajuda = v.findViewById(R.id.ajuda_img);
        ajuda_icone = v.findViewById(R.id.icone);
        tempo = v.findViewById(R.id.tempo);
        pontuacao = v.findViewById(R.id.pontuacao);
        palavracerta = v.findViewById(R.id.palavracerta);
        avancar = v.findViewById(R.id.avancar);
        mQueue = Volley.newRequestQueue(getContext());
        progressBar = v.findViewById(R.id.progress_bar);
        front = v.findViewById(R.id.front);
        back = v.findViewById(R.id.layout_back_4);
        tempo_4 = v.findViewById(R.id.tempo_4);
        pontuacao_4 = v.findViewById(R.id.pontuacao_4);
        ocupar_4 = v.findViewById(R.id.ocupar_4);
        ocupar_5 = v.findViewById(R.id.ocupar_5);
        ocupar_6 = v.findViewById(R.id.ocupar_6);
        imagem_centro_4 = v.findViewById(R.id.imagem_centro_4);
        palavra1_4 = v.findViewById(R.id.palavra1_4);
        palavra2_4 = v.findViewById(R.id.palavra2_4);
        palavra3_4 = v.findViewById(R.id.palavra3_4);
        quizz1 = v.findViewById(R.id.quizz1);
        quizz2 = v.findViewById(R.id.quizz2);
        quizz3 = v.findViewById(R.id.quizz3);
        imagem_centro_5 = v.findViewById(R.id.imagem_centro_5);
        imagem_centro_6 = v.findViewById(R.id.imagem_centro_6);

        arrayResources = getResources().obtainTypedArray(
                R.array.resicon);
        arrayResources_c = getResources().obtainTypedArray(
                R.array.resicon_c);
        arrayResources_o = getResources().obtainTypedArray(
                R.array.resicon_o);
        arrayResources_tds = getResources().obtainTypedArray(
                R.array.resicon_tds);

        topoesq.setOnDragListener(myOnDragListener);
        topodir.setOnDragListener(myOnDragListener);
        baixoesq.setOnDragListener(myOnDragListener);
        baixodir.setOnDragListener(myOnDragListener);
        palavra1.setOnDragListener(myOnDragListener);
        palavra2.setOnDragListener(myOnDragListener);
        palavra3.setOnDragListener(myOnDragListener);
        palavra4.setOnDragListener(myOnDragListener);
        palavra1_4.setOnDragListener(myOnDragListener);
        palavra2_4.setOnDragListener(myOnDragListener);
        palavra3_4.setOnDragListener(myOnDragListener);
        imagem_centro_4.setOnDragListener(myOnDragListener);
        imagem_centro_5.setOnDragListener(myOnDragListener);
        imagem_centro_6.setOnDragListener(myOnDragListener);


        return v;
    }

    public void desenhar_ao_fazer_drag() {
        //countainer.removeAllViews();

        for (int i = 0; i < arrayResources.length()-1; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1.removeAllViews();
                    palavra1.addView(imageView);
                    break;
                case 1:
                    palavra2.removeAllViews();
                    palavra2.addView(imageView);
                    break;
                case 2:
                    palavra3.removeAllViews();
                    palavra3.addView(imageView);
                    break;
                case 3:
                    palavra4.removeAllViews();
                    palavra4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }

    }

    public void desenhar() {
        //countainer.removeAllViews();
        comecou = true;
        arrayletras = formar_random_array();

        Log.d("tag", Arrays.toString(arrayletras));

        for (int i = 0; i < arrayResources.length()-1; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1.removeAllViews();
                    palavra1.addView(imageView);
                    break;
                case 1:
                    palavra2.removeAllViews();
                    palavra2.addView(imageView);
                    break;
                case 2:
                    palavra3.removeAllViews();
                    palavra3.addView(imageView);
                    break;
                case 3:
                    palavra4.removeAllViews();
                    palavra4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }

    }

    private void vitoria(){
        int[] array = {-1, -1, -1, -1};
        View palavra_v;
        ImageView palavra_img;

        if(topoesq.getChildCount() > 0){
            palavra_v = topoesq.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[0] = palavra_img.getId();
        }

        if(topodir.getChildCount() > 0){
            palavra_v = topodir.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[1] = palavra_img.getId();
        }

        if(baixoesq.getChildCount() > 0){
            palavra_v = baixoesq.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[2] = palavra_img.getId();
        }

        if(baixodir.getChildCount() > 0){
            palavra_v = baixodir.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[3] = palavra_img.getId();
        }
        if(array[0] == 0 && array[1] == 1 && array[2] == 2 && array[3] == 3){
            cdTimer.cancel();
            tempo.setText("Ganhou");
            pontuacao_final = pontuacao_int;
            pontuacao.setText("Pontuação: " + pontuacao_final);

            topoesq.setVisibility(View.GONE);
            topodir.setVisibility(View.GONE);
            baixoesq.setVisibility(View.GONE);
            baixodir.setVisibility(View.GONE);

            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(3000);
            AnimationSet animation = new AnimationSet(false); //change to false
            animation.addAnimation(fadeIn);
            palavracerta.setAnimation(animation);

            palavracerta.setVisibility(View.VISIBLE);

            for (int i = 0; i < arrayResources.length(); i++) {
                if(i == 4){
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(arrayResources.getDrawable(i));
                    imageView.setOnLongClickListener(myOnLongClickListener);
                    imageView.setId(i);
                    palavracerta.addView(imageView);
                    palavracerta.setGravity(Gravity.CENTER);
                }
            }

            avancar.setClickable(true);
            Session session = new Session(getContext());
            if(pontuacao_final > session.getUser().getPontuacao().getPontuacao_desaf_1()){
                guardar_bd("terra", 1, pontuacao_final);
                Utilizador utilizador = session.getUser();
                Pontuacao pontuacao = utilizador.getPontuacao();
                pontuacao.setPontuacao_desaf_1(pontuacao_final);
                session.setUser(utilizador);
            }else{
                Toast.makeText(getContext(), "Não conseguiu melhorar a sua pontuação, tente novamente", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void guardar_bd(String terra, int i, int pontuacao_final_) {
        progressBar.setVisibility(View.VISIBLE);
        Session session = new Session(getContext());
        Utilizador utilizador = session.getUser();
        Pontuacao pontuacao = utilizador.getPontuacao();
        String url = null;

        if(terra.contains("terra") && i == 1){
            Log.d("tage", "entrou");
            int pontuacao_total = Integer.parseInt(pontuacao.getPontuacao());
            pontuacao_total = pontuacao_total  - pontuacao.getPontuacao_desaf_1() + pontuacao_final_;
            int tempo = (int) (Integer.parseInt(pontuacao.getTempo()) + temporizador);
            String nome = utilizador.getNome();

            //guardar o desafio 1
            url = String.format("http://jsonbuscatudo.x10host.com/pontuacao_desaf_1.php?pontuacao=%1$s&tempo=%2$s&pontuacao_desaf_1=%3$s&nome=%4$s",
                    pontuacao_total, tempo, pontuacao_final_, nome);

        }else if(terra.contains("terra") && i == 2){

            Log.d("tage", "entrou");
            int pontuacao_total = Integer.parseInt(pontuacao.getPontuacao());
            pontuacao_total = pontuacao_total  - pontuacao.getPontuacao_desaf_2() + pontuacao_final_;
            int tempo = (int) (Integer.parseInt(pontuacao.getTempo()) + temporizador);
            String nome = utilizador.getNome();

            //guardar o desafio 2
            url = String.format("http://jsonbuscatudo.x10host.com/pontuacao_desaf_2.php?pontuacao=%1$s&tempo=%2$s&pontuacao_desaf_2=%3$s&nome=%4$s",
                    pontuacao_total, tempo, pontuacao_final_, nome);
        }else if(terra.contains("terra") && i == 3){

            Log.d("tage", "entrou");
            int pontuacao_total = Integer.parseInt(pontuacao.getPontuacao());
            pontuacao_total = pontuacao_total  - pontuacao.getPontuacao_desaf_3() + pontuacao_final_;
            int tempo = (int) (Integer.parseInt(pontuacao.getTempo()) + temporizador);
            String nome = utilizador.getNome();

            //guardar o desafio 3
            url = String.format("http://jsonbuscatudo.x10host.com/pontuacao_desaf_3.php?pontuacao=%1$s&tempo=%2$s&pontuacao_desaf_2=%3$s&nome=%4$s",
                    pontuacao_total, tempo, pontuacao_final_, nome);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tag", response.toString());
                        resultado = true;
                        Toast.makeText(getContext(), "Parabéns, melhorou a sua pontuação", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }{
                resultado = false;
                Toast.makeText(getContext(), "Aconteceu um erro", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
        progressBar.setVisibility(View.GONE);
    }

    View.OnLongClickListener myOnLongClickListener = new View.OnLongClickListener() {
        @SuppressLint("ResourceType")
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);

            //v.setVisibility(View.INVISIBLE);
            return true;
        }

    };

    View.OnDragListener myOnDragListener = new View.OnDragListener() {

        @SuppressLint({"ResourceType", "NewApi"})
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:

                    View view = (View) event.getLocalState();
                    LinearLayout oldParent = (LinearLayout) view.getParent();
                    oldParent.removeView(view);
                    LinearLayout newParent = (LinearLayout) v;
                    newParent.removeAllViews();
                    newParent.addView(view);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if(palavra.contains("k")){
                        desenhar_ao_fazer_drag();
                        vitoria();
                    }else if(palavra.contains("c")){
                        desenhar_ao_fazer_drag_c();
                        vitoria_c();
                    }else if(palavra.contains("o")){
                        desenhar_ao_fazer_drag_o();
                        vitoria_o();
                    }else if(palavra.contains("t4")){
                        Log.d("tag", "entrou");
                        desenhar_ao_fazer_drag_td();

                        final View droppedView = (View) event.getLocalState();
                        droppedView.post(new Runnable(){
                            @Override
                            public void run() {

                                if(contador_tds == 0){
                                    quizz1.setVisibility(View.GONE);
                                    quizz2.setVisibility(View.VISIBLE);
                                    quizz3.setVisibility(View.GONE);

                                }else if(contador_tds == 1){
                                    Log.d("tag", "entrou");
                                    quizz1.setVisibility(View.GONE);
                                    quizz2.setVisibility(View.GONE);
                                    quizz3.setVisibility(View.VISIBLE);

                                }}
                        });

                        contador_tds ++;

                    }
                    break;
                default:
                    break;
            }
            return true;
        }

    };

    private void desenhar_ao_fazer_drag_td() {

        for (int i = 0; i < arrayResources_tds.length(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources_tds.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1_4.removeAllViews();
                    palavra1_4.addView(imageView);
                    break;
                case 1:
                    palavra2_4.removeAllViews();
                    palavra2_4.addView(imageView);
                    break;
                case 2:
                    palavra3_4.removeAllViews();
                    palavra3_4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }
    }

    private void vitoria_o() {
        int[] array = {-1, -1, -1, -1};
        View palavra_v;
        ImageView palavra_img;

        if(topoesq.getChildCount() > 0){
            palavra_v = topoesq.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[0] = palavra_img.getId();
        }

        if(topodir.getChildCount() > 0){
            palavra_v = topodir.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[1] = palavra_img.getId();
        }

        if(baixoesq.getChildCount() > 0){
            palavra_v = baixoesq.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[2] = palavra_img.getId();
        }

        if(baixodir.getChildCount() > 0){
            palavra_v = baixodir.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[3] = palavra_img.getId();
        }
        if(array[0] == 0 && array[1] == 1 && array[2] == 2 && array[3] == 3){
            cdTimer.cancel();
            tempo.setText("Ganhou");
            pontuacao_final = pontuacao_int;
            pontuacao.setText("Pontuação: " + pontuacao_final);

            topoesq.setVisibility(View.GONE);
            topodir.setVisibility(View.GONE);
            baixoesq.setVisibility(View.GONE);
            baixodir.setVisibility(View.GONE);

            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(3000);
            AnimationSet animation = new AnimationSet(false); //change to false
            animation.addAnimation(fadeIn);
            palavracerta.setAnimation(animation);

            palavracerta.setVisibility(View.VISIBLE);

            for (int i = 0; i < arrayResources_o.length(); i++) {
                if(i == 4){
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(arrayResources_o.getDrawable(i));
                    imageView.setOnLongClickListener(myOnLongClickListener);
                    imageView.setId(i);
                    palavracerta.addView(imageView);
                    palavracerta.setGravity(Gravity.CENTER);
                }
            }

            avancar.setClickable(true);
            Session session = new Session(getContext());
            if(pontuacao_final > session.getUser().getPontuacao().getPontuacao_desaf_3()){
                guardar_bd("terra", 3, pontuacao_final);
                Utilizador utilizador = session.getUser();
                Pontuacao pontuacao = utilizador.getPontuacao();
                pontuacao.setPontuacao_desaf_3(pontuacao_final);
                session.setUser(utilizador);
            }else{
                Toast.makeText(getContext(), "Não conseguiu melhorar a sua pontuação, tente novamente", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void desenhar_ao_fazer_drag_o() {

        for (int i = 0; i < arrayResources_o.length()-1; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources_o.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1.removeAllViews();
                    palavra1.addView(imageView);
                    break;
                case 1:
                    palavra2.removeAllViews();
                    palavra2.addView(imageView);
                    break;
                case 2:
                    palavra3.removeAllViews();
                    palavra3.addView(imageView);
                    break;
                case 3:
                    palavra4.removeAllViews();
                    palavra4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }
    }

    private void vitoria_c() {
        int[] array = {-1, -1, -1, -1};
        View palavra_v;
        ImageView palavra_img;

        if(topoesq.getChildCount() > 0){
            palavra_v = topoesq.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[0] = palavra_img.getId();
        }

        if(topodir.getChildCount() > 0){
            palavra_v = topodir.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[1] = palavra_img.getId();
        }

        if(baixoesq.getChildCount() > 0){
            palavra_v = baixoesq.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[2] = palavra_img.getId();
        }

        if(baixodir.getChildCount() > 0){
            palavra_v = baixodir.getChildAt(0);
            palavra_img = (ImageView) palavra_v;
            array[3] = palavra_img.getId();
        }
        if(array[0] == 0 && array[1] == 1 && array[2] == 2 && array[3] == 3){
            cdTimer.cancel();
            tempo.setText("Ganhou");
            pontuacao_final = pontuacao_int;
            pontuacao.setText("Pontuação: " + pontuacao_final);

            topoesq.setVisibility(View.GONE);
            topodir.setVisibility(View.GONE);
            baixoesq.setVisibility(View.GONE);
            baixodir.setVisibility(View.GONE);

            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(3000);
            AnimationSet animation = new AnimationSet(false); //change to false
            animation.addAnimation(fadeIn);
            palavracerta.setAnimation(animation);

            palavracerta.setVisibility(View.VISIBLE);

            for (int i = 0; i < arrayResources_c.length(); i++) {
                if(i == 4){
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(arrayResources_c.getDrawable(i));
                    imageView.setOnLongClickListener(myOnLongClickListener);
                    imageView.setId(i);
                    palavracerta.addView(imageView);
                    palavracerta.setGravity(Gravity.CENTER);
                }
            }

            avancar.setClickable(true);
            Session session = new Session(getContext());
            if(pontuacao_final > session.getUser().getPontuacao().getPontuacao_desaf_2()){
                guardar_bd("terra", 2, pontuacao_final);
                Utilizador utilizador = session.getUser();
                Pontuacao pontuacao = utilizador.getPontuacao();
                pontuacao.setPontuacao_desaf_2(pontuacao_final);
                session.setUser(utilizador);
            }else{
                Toast.makeText(getContext(), "Não conseguiu melhorar a sua pontuação, tente novamente", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void desenhar_ao_fazer_drag_c() {

        for (int i = 0; i < arrayResources_c.length()-1; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources_c.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1.removeAllViews();
                    palavra1.addView(imageView);
                    break;
                case 1:
                    palavra2.removeAllViews();
                    palavra2.addView(imageView);
                    break;
                case 2:
                    palavra3.removeAllViews();
                    palavra3.addView(imageView);
                    break;
                case 3:
                    palavra4.removeAllViews();
                    palavra4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }
    }

    private void startCountDownTimer() {
        cdTimer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                //update total with the remaining time left
                total = millisUntilFinished;

                temporizador = (1 + millisUntilFinished)/1000;

                if(palavra.contains("t4")){
                    tempo_4.setText("Tempo: " +  millisUntilFinished/ 1000);
                    if((millisUntilFinished/ 1000) % 5 == 0){
                        pontuacao_int = pontuacao_int - 1;
                        pontuacao_4.setText("Pontuação: " + String.valueOf(pontuacao_int));
                    }
                }else{
                    tempo.setText("Tempo: " +  millisUntilFinished/ 1000);
                    if((millisUntilFinished/ 1000) % 5 == 0){
                        pontuacao_int = pontuacao_int - 1;
                        pontuacao.setText("Pontuação: " + String.valueOf(pontuacao_int));
                    }
                }
            }
            public void onFinish() {
                cdTimer.cancel();
                pontuacao.setText("Pontuação: " + String.valueOf(0));
            }
        }.start();
    }

    public void updateEditText(final String planeta, final String nivel, final int score) {

        buttonPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "carregou");
                CharSequence input = "Venho de Jogo";
                listener.onInputJPSent(planeta, nivel, score);
            }
        });

        if(planeta.contains("terra") && nivel.contains("1")){
            //puzle e palavra K
            back.setVisibility(View.GONE);
            front.setVisibility(View.VISIBLE);
            titulo.setText("Planeta Terra, nível 1");
            palavra = "k";
            comecar_k();
        }else if(planeta.contains("terra") && nivel.contains("2")){
            back.setVisibility(View.GONE);
            front.setVisibility(View.VISIBLE);
            titulo.setText("Planeta Terra, nível 2");
            palavra = "c";
            comecar_c();
        }else if(planeta.contains("terra") && nivel.contains("3")){
            back.setVisibility(View.GONE);
            front.setVisibility(View.VISIBLE);
            titulo.setText("Planeta Terra, nível 3");
            palavra = "o";
            comecar_o();

        }else if(planeta.contains("terra") && nivel.contains("4")){
            titulo.setText("Planeta Terra, nível 4");
            palavra = "t4";
            pontuacao_int = 12;
            pontuacao_4.setText("Pontuação: " + String.valueOf(pontuacao_int));
            comecar_t4();
        }else if(planeta.contains("jupiter") && nivel.contains("1")){

        }else if(planeta.contains("jupiter") && nivel.contains("2")){

        }else if(planeta.contains("jupiter") && nivel.contains("3")){

        }else if(planeta.contains("jupiter") && nivel.contains("4")){

        }
    }

    private void comecar_t4() {
        front.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        if(comecou == true){
            cdTimer.cancel();
            resetar_td();
        }

        desenhar_td();
        total = 60000;
        startCountDownTimer();
    }

    private void desenhar_td() {
        comecou = true;
        arrayletras = formar_random_array_tds();

        Log.d("tag", Arrays.toString(arrayletras));

        for (int i = 0; i < arrayResources_tds.length(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources_tds.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1_4.removeAllViews();
                    palavra1_4.addView(imageView);
                    break;
                case 1:
                    palavra2_4.removeAllViews();
                    palavra2_4.addView(imageView);
                    break;
                case 2:
                    palavra3_4.removeAllViews();
                    palavra3_4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }
    }

    @SuppressLint("ResourceType")
    private void resetar_td() {
        total = 60000;
        cdTimer.cancel();

        quizz1.setVisibility(View.VISIBLE);
        quizz2.setVisibility(View.GONE);
        quizz3.setVisibility(View.GONE);

        ImageView imagem = new ImageView(getContext());
        imagem.setImageResource(R.raw.preto);
        imagem_centro_4.removeAllViews();
        imagem_centro_4.addView(imagem);

        imagem = new ImageView(getContext());
        imagem.setImageResource(R.raw.preto);
        imagem_centro_5.removeAllViews();
        imagem_centro_5.addView(imagem);

        imagem = new ImageView(getContext());
        imagem.setImageResource(R.raw.preto);
        imagem_centro_6.removeAllViews();
        imagem_centro_6.addView(imagem);
        desenhar();
    }


    private ArrayList<String> perguntas_k(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Qual destes elementos é empregado na formação de cristais?");
        lista.add("Qual destes elementos está presente na banana?");
        lista.add("Qual destes elementos pertence aos metais alcalinos");
        return lista;
    }
    private ArrayList<String> perguntas_o(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Qual destes elementos é usado durante a respiração?");
        lista.add("Qual destes elementos está presente na água?");
        lista.add("Qual destes elementos é o terceiro mais abundante do universo?");
        return lista;
    }
    private ArrayList<String> perguntas_c(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Qual destes elementos se usa no petróleo?");
        lista.add("Qual destes elementos está presente em diamantes?");
        lista.add("Qual destes elementos é libertado na queima das florestas?");
        return lista;
    }

    private void comecar_o() {
        if(comecou == true){
            cdTimer.cancel();
            resetar();
        }
        desenhar_o();
        startCountDownTimer();
    }

    private void desenhar_o() {
        comecou = true;
        arrayletras = formar_random_array();

        Log.d("tag", Arrays.toString(arrayletras));

        for (int i = 0; i < arrayResources_o.length()-1; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources_o.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1.removeAllViews();
                    palavra1.addView(imageView);
                    break;
                case 1:
                    palavra2.removeAllViews();
                    palavra2.addView(imageView);
                    break;
                case 2:
                    palavra3.removeAllViews();
                    palavra3.addView(imageView);
                    break;
                case 3:
                    palavra4.removeAllViews();
                    palavra4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }

    }

    private void comecar_c() {
        if(comecou == true){
            cdTimer.cancel();
            resetar();
        }
        desenhar_c();
        startCountDownTimer();
    }

    private void desenhar_c() {
        //countainer.removeAllViews();
        comecou = true;
        arrayletras = formar_random_array();

        Log.d("tag", Arrays.toString(arrayletras));

        for (int i = 0; i < arrayResources_c.length()-1; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(arrayResources_c.getDrawable(i));
            imageView.setOnLongClickListener(myOnLongClickListener);
            imageView.setId(i);
            switch (arrayletras[i]){
                case 0:
                    palavra1.removeAllViews();
                    palavra1.addView(imageView);
                    break;
                case 1:
                    palavra2.removeAllViews();
                    palavra2.addView(imageView);
                    break;
                case 2:
                    palavra3.removeAllViews();
                    palavra3.addView(imageView);
                    break;
                case 3:
                    palavra4.removeAllViews();
                    palavra4.addView(imageView);
                    break;
            }
            //countainer.addView(imageView);
        }
    }

    @SuppressLint("ResourceType")
    private void comecar_k() {
        if(comecou == true){
            cdTimer.cancel();
            resetar();
        }
        desenhar();
        startCountDownTimer();
    }

    @SuppressLint("ResourceType")
    private void resetar() {
        total = 30000;
        pontuacao_int = 6;
        palavracerta.setVisibility(View.GONE);
        topoesq.setVisibility(View.VISIBLE);
        topodir.setVisibility(View.VISIBLE);
        baixodir.setVisibility(View.VISIBLE);
        baixoesq.setVisibility(View.VISIBLE);

        ImageView imagem = new ImageView(getContext());
        imagem.setImageResource(R.raw.preto);
        topoesq.removeAllViews();
        topoesq.addView(imagem);

        imagem = new ImageView(getContext());
        imagem.setImageResource(R.raw.preto);
        topodir.removeAllViews();
        topodir.addView(imagem);

        imagem = new ImageView(getContext());
        imagem.setImageResource(R.raw.preto);
        baixoesq.removeAllViews();
        baixoesq.addView(imagem);

        imagem = new ImageView(getContext());
        imagem.setImageResource(R.raw.preto);
        baixodir.removeAllViews();
        baixodir.addView(imagem);


        pontuacao.setText("Pontuação 5");
        ajuda.setImageResource(R.raw.k_);
        ajuda_icone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ajuda.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        ajuda.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    private int[] formar_random_array_tds() {
        int[] meuArray = {-1, -1, -1};
        boolean boleano = false;

        for (int i = 0; i < meuArray.length; i++) {
            boleano = false;
            int choice = (int) (Math.random() * meuArray.length);
            //System.out.println(choice);
            if (meuArray[i] == -1) {

                //System.out.println(intArrayContains(meuArray, choice));
                if(intArrayContains(meuArray, choice) == false){
                    meuArray[i] = choice;
                }else{
                    i--;
                }

            }

        }
        return meuArray;
    }

    private int[] formar_random_array() {
        int[] meuArray = {-1, -1, -1, -1};
        boolean boleano = false;

        for (int i = 0; i < meuArray.length; i++) {
            boleano = false;
            int choice = (int) (Math.random() * meuArray.length);
            //System.out.println(choice);
            if (meuArray[i] == -1) {

                //System.out.println(intArrayContains(meuArray, choice));
                if(intArrayContains(meuArray, choice) == false){
                    meuArray[i] = choice;
                }else{
                    i--;
                }

            }

        }
        return meuArray;
    }

    private boolean intArrayContains(int[] intArray, int value) {
        boolean result = false;
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] == value) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentJPListener) {
            listener = (FragmentJPListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentJPListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
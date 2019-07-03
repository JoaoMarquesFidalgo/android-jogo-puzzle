package org.example.fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class Fragment_jogo_puzzle_quizz extends Fragment {
    private FragmentJPQListener listener;
    private Button voltar;
    private TextView titulo_jogo_puzzle;
    LinearLayout imagem_centro_4, palavra1_4, palavra2_4, palavra3_4;
    TextView tempo_4, pontuacao_4, ocupar_4;
    private int[] arrayletras, arrayrespostas, arraycruzado;
    private boolean comecou;
    private TypedArray arrayResources_tds;
    private int contador_tds;
    ArrayList<String> respostas;
    TypedArray imgs;
    private CountDownTimer cdTimer;
    private long total = 60000;
    private int pontuacao_int = 12;
    private long temporizador;

    public interface FragmentJPQListener {
        void onInputJPQSent(CharSequence input);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jogo_puzzle_quizz, container, false);
        voltar = v.findViewById(R.id.voltar);
        titulo_jogo_puzzle = v.findViewById(R.id.titulo_jogo_puzzle);
        ocupar_4 = v.findViewById(R.id.ocupar_4);
        pontuacao_4 = v.findViewById(R.id.pontuacao_4);
        tempo_4 = v.findViewById(R.id.tempo_4);
        palavra3_4 = v.findViewById(R.id.palavra3_4);
        palavra2_4 = v.findViewById(R.id.palavra2_4);
        palavra1_4 = v.findViewById(R.id.palavra1_4);
        imagem_centro_4 = v.findViewById(R.id.imagem_centro_4);

        arrayResources_tds = getResources().obtainTypedArray(R.array.resicon_tds);

        imagem_centro_4.setOnDragListener(myOnDragListener);
        arrayrespostas = new int[]{-1, -1, -1};

        imgs = getResources().obtainTypedArray(R.array.resicon_tds);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = null;
                listener.onInputJPQSent(input);
            }
        });

        return v;
    }

    public void updateEditText(String planeta, String nivel, int score) {
        titulo_jogo_puzzle.setText("Planeta Terra, nível 4");
        desenhar();

    }

    @SuppressLint("ResourceType")
    private void desenhar() {
        comecou = true;
        arrayletras = formar_random_array_tds();
        Log.d("tag", Arrays.toString(arrayletras));
        ImageView imageView = new ImageView(getContext());

        imageView.setImageDrawable(arrayResources_tds.getDrawable(0));
        imageView.setOnLongClickListener(myOnLongClickListener);
        imageView.setId(0);
        imageView.setTag(0);
        palavra1_4.removeAllViews();
        palavra1_4.addView(imageView);

        ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageDrawable(arrayResources_tds.getDrawable(1));
        imageView2.setOnLongClickListener(myOnLongClickListener);
        imageView2.setId(1);
        imageView2.setTag(1);
        palavra2_4.removeAllViews();
        palavra2_4.addView(imageView2);

        ImageView imageView3 = new ImageView(getContext());
        imageView3.setImageDrawable(arrayResources_tds.getDrawable(2));
        imageView3.setOnLongClickListener(myOnLongClickListener);
        imageView3.setId(2);
        imageView3.setTag(2);
        palavra3_4.removeAllViews();
        palavra3_4.addView(imageView3);

        startCountDownTimer();
        ocupar_4.setText(perguntas_k().get((int) (Math.random() * perguntas_k().size())));
        //countainer.addView(imageView);

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
                    desenhar_ao_fazer_drag_td();
                    passar();
                    //Toast.makeText(getApplicationContext(), "check", Toast.LENGTH_SHORT).show();


                    break;
                default:
                    break;
            }
            return true;
        }

    };

    @SuppressLint("ResourceType")
    private void passar() {

        if(contador_tds == 1){
            ocupar_4.setText(perguntas_c().get((int) (Math.random() * perguntas_c().size())));
            if(imagem_centro_4.getChildCount() > 0){
                View palavra_v = imagem_centro_4.getChildAt(0);
                ImageView palavra_img = (ImageView) palavra_v;
                if(palavra_img.getId() == 0){
                    arrayrespostas[1] = 0;
                }
            }
        }else if(contador_tds == 2){
            ocupar_4.setText(perguntas_o().get((int) (Math.random() * perguntas_o().size())));

            if(imagem_centro_4.getChildCount() > 0){
                View palavra_v = imagem_centro_4.getChildAt(0);
                ImageView palavra_img = (ImageView) palavra_v;
                if(palavra_img.getId() == 2){
                    arrayrespostas[2] = 0;
                }
            }
            vitoria();
        }else if(contador_tds == 0){
            if(imagem_centro_4.getChildCount() > 0){
                View palavra_v = imagem_centro_4.getChildAt(0);
                ImageView palavra_img = (ImageView) palavra_v;
                Log.d("tag", "" + palavra_img.getId());
                if(palavra_img.getId() == 1){
                    arrayrespostas[0] = 0;
                }
            }
            contador_tds ++;

        }
    }

    private void vitoria() {
        if(Arrays.asList(arrayrespostas).contains(-1)){
            Toast.makeText(getContext(), "Perdeu", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Ganhou", Toast.LENGTH_SHORT).show();
            //gravar na bd e na session
        }
    }

    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }

    @SuppressLint("ResourceType")
    private void desenhar_ao_fazer_drag_td() {
        ImageView imageView = new ImageView(getContext());

        imageView.setImageDrawable(arrayResources_tds.getDrawable(0));
        imageView.setOnLongClickListener(myOnLongClickListener);
        imageView.setId(0);
        imageView.setTag(0);
        palavra1_4.removeAllViews();
        palavra1_4.addView(imageView);

        ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageDrawable(arrayResources_tds.getDrawable(1));
        imageView2.setOnLongClickListener(myOnLongClickListener);
        imageView2.setId(1);
        imageView2.setTag(1);
        palavra2_4.removeAllViews();
        palavra2_4.addView(imageView2);

        ImageView imageView3 = new ImageView(getContext());
        imageView3.setImageDrawable(arrayResources_tds.getDrawable(2));
        imageView3.setOnLongClickListener(myOnLongClickListener);
        imageView3.setId(2);
        imageView3.setTag(2);
        palavra3_4.removeAllViews();
        palavra3_4.addView(imageView3);

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

    private void startCountDownTimer() {
        cdTimer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                //update total with the remaining time left
                total = millisUntilFinished;
                temporizador = (1 + millisUntilFinished)/1000;

                    tempo_4.setText("Tempo: " +  millisUntilFinished/ 1000);
                    if((millisUntilFinished/ 1000) % 5 == 0){
                        pontuacao_int = pontuacao_int - 1;
                        pontuacao_4.setText("Pontuação: " + String.valueOf(pontuacao_int));
                    }


            }
            public void onFinish() {
                cdTimer.cancel();
                pontuacao_4.setText("Pontuação: " + String.valueOf(0));
            }
        }.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentJPQListener) {
            listener = (FragmentJPQListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentJPQListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}

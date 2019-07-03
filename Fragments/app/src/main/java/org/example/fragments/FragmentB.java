package org.example.fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentB extends Fragment {
    private FragmentBListener listener;
    private EditText editText;
    private Button buttonOk, button_jogar, button_ranking, button_enciclopedia, button_opcoes;
    private TextView txt_bem_vindo, planeta_inicio, pontuacao_inicio;
    private ImageView img_profile;
    private RequestQueue mQueue;
    private String nome = null;
    private ProgressBar progressBar;
    private LinearLayout LL_container;
    boolean entrou = false;
    private SharedPreferences sp;


    public interface FragmentBListener {
        void onInputBSent(CharSequence input, int num);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b, container, false);

        button_enciclopedia = v.findViewById(R.id.btn_enciclopedia);
        button_jogar = v.findViewById(R.id.btn_jogar);
        button_ranking = v.findViewById(R.id.btn_ranking);
        button_opcoes = v.findViewById(R.id.btn_opcoes);
        editText = v.findViewById(R.id.edit_text);
        buttonOk = v.findViewById(R.id.button_ok);
        txt_bem_vindo = v.findViewById(R.id.txt_bem_vindo);
        img_profile = v.findViewById(R.id.imagem_profile);
        progressBar = v.findViewById(R.id.progress_bar);
        planeta_inicio = v.findViewById(R.id.planeta_inicio);
        pontuacao_inicio = v.findViewById(R.id.pontuacao_inicio);
        LL_container = v.findViewById(R.id.LL_container);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = editText.getText();
                listener.onInputBSent(input, -1);
            }
        });

        button_opcoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = nome;
                listener.onInputBSent(input, 3);
            }
        });

        button_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = nome;
                listener.onInputBSent(input, 1);
            }
        });

        button_enciclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = nome;
                listener.onInputBSent(input, 2);
            }
        });

        button_jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = nome;
                listener.onInputBSent(input, 0);
            }
        });
        return v;
    }

    @SuppressLint("ResourceType")
    public void updateDados(CharSequence input, String cara_){

        //buscando a session, posso fazer update aos campos sem ter de fazer nova conexao a base de dados
        Session session = new Session(getContext());
        txt_bem_vindo.setText("Bem vindo/a, " + session.getUser().getNome());
        switch (session.getUser().getFoto().getUrl()){
            case "carahattrick1.png":
                img_profile.setImageResource(R.raw.carahattrick1);
                break;
            case "carahattrick2.png":
                img_profile.setImageResource(R.raw.carahattrick2);
                break;
            case "carahattrick3.png":
                img_profile.setImageResource(R.raw.carahattrick3);
                break;
            case "carahattrick4.png":
                img_profile.setImageResource(R.raw.carahattrick4);
                break;
        }
        if(session.getUser().getOpcoes().getMusica().contains("97202") && session.getUser().getOpcoes().getSom() == 1 ){
            Log.d("tag", "entrou");
            getActivity().stopService(new Intent(getActivity(), Serv2.class));
            getActivity().startService(new Intent(getActivity(),Serv.class));
        }else if(session.getUser().getOpcoes().getMusica().contains("wtirp") && session.getUser().getOpcoes().getSom() == 1 ){
            Log.d("tag", "entrou2");
            getActivity().stopService(new Intent(getActivity(), Serv.class));
            getActivity().startService(new Intent(getActivity(), Serv2.class));
        }else{
            getActivity().stopService(new Intent(getActivity(), Serv.class));
            getActivity().stopService(new Intent(getActivity(), Serv2.class));
        }
    }

    @SuppressLint("ResourceType")
    public void updateEditText(CharSequence newText) {

        nome = newText.toString();
        mQueue = Volley.newRequestQueue(getContext());

        if(entrou == false){
            login(nome);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentBListener) {
            listener = (FragmentBListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentBListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @SuppressLint("ResourceType")
    private void login(String nomeb){
        progressBar.setVisibility(View.VISIBLE);
        String url = String.format("http://jsonbuscatudo.x10host.com/buscar.php?nome=%1$s", nomeb);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mapear(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                inserir(nome);
            }
        });

        mQueue.add(request);
        entrou = true;
    }

    @SuppressLint("ResourceType")
    private void mapear(JSONObject response) throws JSONException {
        //instanciar e iniciar objetos
        Utilizador utilizador = new Utilizador();
        utilizador.setNome(response.getString("utilizador_nome"));
        utilizador.setFacebook(response.getString("utilizador_facebook"));

        Planeta planeta = new Planeta();
        planeta.setDificuldade(Integer.parseInt(response.getString("planeta_dificuldade")));
        planeta.setNome(response.getString("planeta_nome"));

        Pontuacao pontuacao = new Pontuacao();
        pontuacao.setPontuacao(response.getString("pontuacao_pontuacao"));
        pontuacao.setTempo(response.getString("pontuacao_tempo"));
        pontuacao.setPontuacao_desaf_1(Integer.parseInt(response.getString("pontuacao_desaf_1")));
        pontuacao.setPontuacao_desaf_2(Integer.parseInt(response.getString("pontuacao_desaf_2")));
        pontuacao.setPontuacao_desaf_3(Integer.parseInt(response.getString("pontuacao_desaf_3")));
        pontuacao.setPontuacao_desaf_4(Integer.parseInt(response.getString("pontuacao_desaf_4")));
        pontuacao.setPontuacao_desaf_5(Integer.parseInt(response.getString("pontuacao_desaf_5")));
        pontuacao.setPontuacao_desaf_6(Integer.parseInt(response.getString("pontuacao_desaf_6")));
        pontuacao.setPontuacao_desaf_7(Integer.parseInt(response.getString("pontuacao_desaf_7")));
        pontuacao.setPontuacao_desaf_8(Integer.parseInt(response.getString("pontuacao_desaf_8")));

        Opcoes opcoes = new Opcoes();
        opcoes.setMusica(response.getString("opcoes_musica"));
        opcoes.setSom(response.getInt("opcoes_som"));

        Foto foto = new Foto();
        foto.setUrl(response.getString("foto_url"));

        utilizador.setFoto(foto);
        utilizador.setOpcoes(opcoes);
        utilizador.setPlaneta(planeta);
        utilizador.setPontuacao(pontuacao);

        // guardar tudo em SharedPreferences
        Session session = new Session(getContext());
        session.setUser(utilizador);


        //tratar da parte grafica
        switch (utilizador.getFoto().getUrl()){
            case "carahattrick1.png":
                img_profile.setImageResource(R.raw.carahattrick1);
                break;
            case "carahattrick2.png":
                img_profile.setImageResource(R.raw.carahattrick2);
                break;
            case "carahattrick3.png":
                img_profile.setImageResource(R.raw.carahattrick3);
                break;
            case "carahattrick4.png":
                img_profile.setImageResource(R.raw.carahattrick4);
                break;
        }
        if(utilizador.getOpcoes().getMusica().contains("97202") && utilizador.getOpcoes().getSom() == 1 ){
            Log.d("tag", "entrou");
            getActivity().startService(new Intent(getActivity(),Serv.class));
        }else if(utilizador.getOpcoes().getMusica().contains("wtirp")){
            Log.d("tag", "entrou2");
            getActivity().startService(new Intent(getActivity(), Serv2.class));
        }else{
            getActivity().stopService(new Intent(getActivity(), Serv.class));
            getActivity().stopService(new Intent(getActivity(), Serv2.class));
        }

        txt_bem_vindo.setText("Bem vindo/a, " + utilizador.getNome());
        planeta_inicio.setText("Planeta: " + utilizador.getPlaneta().getNome());
        pontuacao_inicio.setText("Pontuação: " + utilizador.getPontuacao().getPontuacao());
        LL_container.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        nome = utilizador.getNome();
    }

    private void inserir(final String nomeb){
        String url = String.format("http://jsonbuscatudo.x10host.com/inserir.php?nome=%1$s&facebook=%2$s", nomeb, null);
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getContext(), "Adicionado novo user ", Toast.LENGTH_LONG).show();
                            login(nomeb);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    txt_bem_vindo.setText("Nome já existe");
                    Log.i("tagconvertstr", "["+error+"]");
                    Toast.makeText(getContext(), "Nome já existe", Toast.LENGTH_LONG).show();
                }
            });
            mQueue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
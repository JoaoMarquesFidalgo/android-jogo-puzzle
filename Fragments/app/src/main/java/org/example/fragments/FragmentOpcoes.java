package org.example.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class FragmentOpcoes extends Fragment {
    private FragmentOpcoesListener listener;
    TextView opcoes, opcoes_nome;
    Button buttonPT, opcoes_mudar_nome_img, conf_opc;
    Spinner dynamicSpinner;
    ImageView imagemProfile, mudar_img_1, mudar_img_2, mudar_img_3, mudar_img_4;
    RelativeLayout LL_container;
    FrameLayout FF_mudar_dados;
    boolean mudar, resultado;
    CheckBox cb;
    String cara = null, musica = null;
    EditText editText;
    private RequestQueue mQueue;
    private ProgressBar progressBar;
    private String novoNome;
    private String cara_;
    private String musica_;
    private int som;

    public interface FragmentOpcoesListener {
        void onInputOSent(CharSequence input, String cara_);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_opcoes, container, false);
        opcoes = v.findViewById(R.id.titulo_opcoes);
        buttonPT = v.findViewById(R.id.pt_opc);
        dynamicSpinner = (Spinner) v.findViewById(R.id.dynamic_spinner);
        opcoes_nome = v.findViewById(R.id.opcoes_nome);
        imagemProfile = (ImageView) v.findViewById(R.id.opcoes_img);
        opcoes_mudar_nome_img =  v.findViewById(R.id.opcoes_mudar_nome_img);
        LL_container = v.findViewById(R.id.LL_container);
        conf_opc = v.findViewById(R.id.conf_opc);
        FF_mudar_dados = v.findViewById(R.id.FF_mudar_dados);
        mudar_img_1 = v.findViewById(R.id.mudar_img_1);
        mudar_img_2 = v.findViewById(R.id.mudar_img_2);
        mudar_img_3 = v.findViewById(R.id.mudar_img_3);
        mudar_img_4 = v.findViewById(R.id.mudar_img_4);
        mudar = false;
        resultado = false;
        editText = v.findViewById(R.id.novo_nome);
        cb = v.findViewById(R.id.checkbox);
        mQueue = Volley.newRequestQueue(getContext());
        progressBar = v.findViewById(R.id.progress_bar);

        return v;
    }

    public void updateEditText(CharSequence newText) {
        cara = new Session(getContext()).getUser().getFoto().getUrl();
        opcoes.setText("Opções");
        popularSpinner();
        popularImgNome();
        popularMudarDados();


    }

    private void popularMudarDados() {

        buttonPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(mudar == true){
                    editarDados();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(resultado == true){
                                Toast.makeText(v.getContext(), "Alterações feitas com sucesso", Toast.LENGTH_SHORT).show();

                                //Tenho que fazer update da session para depois ir buscar a outros fragmentos,
                                // depois do update na base de dados

                                Session session = new Session(getContext());
                                Utilizador utilizador = session.getUser();
                                Opcoes opcoes = utilizador.getOpcoes();
                                Foto foto = utilizador.getFoto();
                                foto.setUrl(cara_);
                                opcoes.setMusica(musica_);
                                opcoes.setSom(som);
                                utilizador.setNome(novoNome);
                                utilizador.setOpcoes(opcoes);
                                utilizador.setFoto(foto);
                                session.setUser(utilizador);

                                CharSequence input = novoNome;
                                listener.onInputOSent(input, cara_);
                            }else{
                                //Session session = new Session(getContext());
                                //listener.onInputOSent(session.getUser().getNome(), session.getUser().getFoto().getUrl());
                                Log.d("tag3", "erro");
                                Toast.makeText(v.getContext(), "Nome duplicado, não foram feitas alterações", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 1000);

                }else{
                    Session session = new Session(getContext());
                    listener.onInputOSent(session.getUser().getNome(), session.getUser().getFoto().getUrl());

                }
            }
        });

        opcoes_mudar_nome_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL_container.setVisibility(View.INVISIBLE);
                FF_mudar_dados.setVisibility(View.VISIBLE);
            }
        });

        conf_opc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FF_mudar_dados.setVisibility(View.INVISIBLE);
                buttonPT.setText("Confirmar");
                buttonPT.setBackgroundColor(getResources().getColor(R.color.green_light));
                mudar = true;
                Session session = new Session(getContext());
                String name = session.getUser().getNome();
                String novoNome = editText.getText().length() > 0 ? editText.getText().toString() : name;
                opcoes_nome.setText("Novo nome: " + novoNome);
                LL_container.setVisibility(View.VISIBLE);
            }
        });
    }

    private void editarDados() {
        progressBar.setVisibility(View.VISIBLE);
        Session session = new Session(getContext());
        String name = session.getUser().getNome();
        som = cb.isChecked() ? 1 : 0;
        musica_ = musica;
        cara_ = cara;
        novoNome = editText.getText().length() > 0 ? editText.getText().toString() : name;

        String url = String.format("http://jsonbuscatudo.x10host.com/editar_opcoes.php?nome=%1$s&som=%2$s&musica=%3$s&cara=%4$s&novonome=%5$s", name, som, musica_, cara_, novoNome);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("erro")){
                            resultado = false;
                        }else{
                            resultado = true;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }{
                resultado = false;
            }
        });
        mQueue.add(stringRequest);
        progressBar.setVisibility(View.GONE);
    }

    private void popularSpinner() {
        //spinner
        String[] items = new String[] { "a_97202_mp4", "b_wtirp_mp4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
        dynamicSpinner.setAdapter(adapter);
        dynamicSpinner.setBackgroundColor(getResources().getColor(R.color.white));
        Session session = new Session(getContext());
        if(session.getUser().getOpcoes().getMusica().contains("97")){
            dynamicSpinner.setSelection(0);
        }else{
            dynamicSpinner.setSelection(1);
        }
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                Log.d("item", "" + parent.getSelectedItemPosition());
                if( parent.getSelectedItemPosition() == 0 && som == 1){
                    getActivity().stopService(new Intent(getActivity(), Serv2.class));
                    getActivity().startService(new Intent(getActivity(), Serv.class));
                }else if(parent.getSelectedItemPosition() == 1 && som == 1){
                    getActivity().stopService(new Intent(getActivity(), Serv.class));
                    getActivity().startService(new Intent(getActivity(), Serv2.class));
                }
                musica = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @SuppressLint("ResourceType")
    private void popularImgNome(){
        Session session = new Session(getContext());
        opcoes.setText("Opções");
        opcoes_nome.setText("Nome: " + session.getUser().getNome());
        switch (session.getUser().getFoto().getUrl()){
            case "carahattrick1.png":
                imagemProfile.setImageResource(R.raw.carahattrick1);
                break;
            case "carahattrick2.png":
                imagemProfile.setImageResource(R.raw.carahattrick2);
                break;
            case "carahattrick3.png":
                imagemProfile.setImageResource(R.raw.carahattrick3);
                break;
            case "carahattrick4.png":
                imagemProfile.setImageResource(R.raw.carahattrick4);
                break;
            default:
                imagemProfile.setImageResource(R.raw.carahattrick1);
                break;
        }

        if(session.getUser().getOpcoes().getSom() == 1){
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }

        mudar_img_1.setImageResource(R.raw.carahattrick1);
        mudar_img_2.setImageResource(R.raw.carahattrick2);
        mudar_img_3.setImageResource(R.raw.carahattrick3);
        mudar_img_4.setImageResource(R.raw.carahattrick4);

        mudar_img_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mudar_img_1.setColorFilter(Color.argb(50, 0, 0, 0));
                mudar_img_2.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_3.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_4.setColorFilter(Color.argb(0, 0, 0, 0));
                imagemProfile.setImageResource(R.raw.carahattrick1);
                cara = "carahattrick1.png";
                return false;
            }
        });

        mudar_img_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mudar_img_1.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_2.setColorFilter(Color.argb(50, 0, 0, 0));
                mudar_img_3.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_4.setColorFilter(Color.argb(0, 0, 0, 0));
                imagemProfile.setImageResource(R.raw.carahattrick2);
                cara = "carahattrick2.png";
                return false;
            }
        });
        mudar_img_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mudar_img_1.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_2.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_3.setColorFilter(Color.argb(50, 0, 0, 0));
                mudar_img_4.setColorFilter(Color.argb(0, 0, 0, 0));
                imagemProfile.setImageResource(R.raw.carahattrick3);
                cara = "carahattrick3.png";
                return false;
            }
        });
        mudar_img_4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mudar_img_1.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_2.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_3.setColorFilter(Color.argb(0, 0, 0, 0));
                mudar_img_4.setColorFilter(Color.argb(50, 0, 0, 0));
                imagemProfile.setImageResource(R.raw.carahattrick4);
                cara = "carahattrick4.png";
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentOpcoesListener) {
            listener = (FragmentOpcoesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
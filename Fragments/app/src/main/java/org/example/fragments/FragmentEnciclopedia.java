package org.example.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifTextView;
import pl.droidsonroids.gif.GifTextureView;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentEnciclopedia extends Fragment {
    private FragmentEnciclopediaListener listener;
    private Button buttonPT;
    TextView enciclopedia, nome_planeta, materia1, materia2, materia3, ocupar, ocuparb;
    Button next, next2;
    GifTextView gifTextView;
    TableLayout encher;
    int numero = 1, numeroelemento = 0;
    String planeta = null;
    private RequestQueue mQueue;
    RelativeLayout RRocupar, RL_back;

    public interface FragmentEnciclopediaListener {
        void onInputESent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_enciclopedia, container, false);

        mQueue = Volley.newRequestQueue(getContext());
        enciclopedia = v.findViewById(R.id.titulo_enciclopedia);
        buttonPT = v.findViewById(R.id.pt_enc);
        gifTextView = v.findViewById(R.id.imagem_rodar);
        nome_planeta = v.findViewById(R.id.nome_planeta);
        next = (Button) v.findViewById(R.id.next);
        nome_planeta.setText("Planeta Terra");
        encher = v.findViewById(R.id.encher);
        next2 = v.findViewById(R.id.next2);
        materia1 = v.findViewById(R.id.materia1);
        materia2 = v.findViewById(R.id.materia2);
        materia3 = v.findViewById(R.id.materia3);
        ocupar = v.findViewById(R.id.ocupar);
        RRocupar = v.findViewById(R.id.RRocupar);
        ocuparb = v.findViewById(R.id.ocuparb);
        RL_back = v.findViewById(R.id.RL_back);

        return v;
    }

    public void updateEditText(CharSequence newText) {

        buttonPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = "Venho de Enciclopedia";
                listener.onInputESent(input);
            }
        });
        planeta = "terra";
        preencher_inicio(planeta);

        gifTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planeta.contains("terra")){
                    preencher_inicio("terra");
                }else if(planeta.contains("jupiter")){
                    preencher_inicio("jupiter");
                }
            }
        });

        ocuparb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enciclopedia.setVisibility(View.VISIBLE);
                RRocupar.setVisibility(View.GONE);
                RL_back.setVisibility(View.VISIBLE);
            }
        });

        materia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planeta.contains("terra")){
                    preencher("oxigenio");
                }else if(planeta.contains("jupiter")){
                    preencher("fosforo");
                }
            }
        });
        materia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planeta.contains("terra")){
                    preencher("potassio");
                }else if(planeta.contains("jupiter")){
                    preencher("enxofre");
                }
            }
        });
        materia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planeta.contains("terra")){
                    preencher("carbono");
                }else if(planeta.contains("jupiter")){
                    preencher("neon");
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero++;
                if(numero % 2 == 0) {
                    planeta = "jupiter";
                    materia1.setText("Fósforo");
                    materia2.setText("Enxofre");
                    materia3.setText("Néon");
                    nome_planeta.setText("Planeta Jupiter");
                    ocupar.setText("");
                    gifTextView.setBackgroundResource(R.drawable.jupiter_spinning);
                    preencher_inicio("jupiter");
                }else{
                    planeta = "terra";
                    materia1.setText("Oxigénio");
                    materia2.setText("Potássio");
                    materia3.setText("Carbono");
                    nome_planeta.setText("Planeta Terra");
                    ocupar.setText("");
                    gifTextView.setBackgroundResource(R.drawable.earth_spinning);
                    preencher_inicio("terra");
                }
            }
        });

        ocupar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enciclopedia.setVisibility(View.GONE);
                preencher_grande();
            }
        });

    }

    private void preencher_inicio(String planeta) {
    ocupar.setText("");
    String url = null;
    if(planeta.contains("terra")){
        url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Terra");
        numeroelemento = 3;
    }else if(planeta.contains("jupiter")){
        url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Júpiter (planeta)");
        numeroelemento = 4;
    }
        try{
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject query = response.getJSONObject("query");
                                JSONObject pages = query.getJSONObject("pages");
                                JSONObject pageid = null;

                                if(numeroelemento == 3){
                                    pageid = pages.getJSONObject("1800");
                                }else if(numeroelemento == 4){
                                    pageid = pages.getJSONObject("9889");
                                }

                                String titulo = pageid.getString("title");
                                String extrato = pageid.getString("extract");
                                Log.d("tag", extrato);
                                /// prof. Mário
                                String[] palavras = extrato.split(" ");
                                StringBuilder sb= new StringBuilder();
                                for(int i = 0; i < 100 && i < palavras.length; i++) {
                                    sb.append(palavras[i]);
                                    sb.append(" ");
                                }
                                if( palavras.length > 100)
                                    sb.append("...");
                                ///

                                ocupar.append("Titulo: " + titulo + "\n\n");
                                ocupar.append("Resumo: " + sb.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            mQueue.add(request);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void preencher_grande() {
        ocuparb.setText("");
        RL_back.setVisibility(View.GONE);
        RRocupar.setVisibility(View.VISIBLE);
        String url = null;
        if(numeroelemento == 0){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Oxigénio");
        }else if(numeroelemento == 1){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Potássio");
        }else if(numeroelemento == 2){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Carbono");
        }else if(numeroelemento == 3){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Terra");
        }else if(numeroelemento == 4){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Júpiter (planeta)");
        }else if(numeroelemento == 5){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Fósforo");
        }else if(numeroelemento == 6){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Enxofre");
        }else if(numeroelemento == 7){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Néon");
        }

        try{
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject query = response.getJSONObject("query");
                                JSONObject pages = query.getJSONObject("pages");
                                JSONObject pageid = null;

                                if(numeroelemento == 0){
                                    pageid = pages.getJSONObject("1381");
                                }else if(numeroelemento == 1){
                                    pageid = pages.getJSONObject("5690");
                                }else if(numeroelemento == 2){
                                    pageid = pages.getJSONObject("5673");
                                }if(numeroelemento == 3){
                                    pageid = pages.getJSONObject("1800");
                                }else if(numeroelemento == 4){
                                    pageid = pages.getJSONObject("9889");
                                }else if(numeroelemento == 5){
                                    pageid = pages.getJSONObject("5684");
                                }else if(numeroelemento == 6){
                                    pageid = pages.getJSONObject("5685");
                                }else if(numeroelemento == 7){
                                    pageid = pages.getJSONObject("5676");
                                }


                                String titulo = pageid.getString("title");
                                String extrato = pageid.getString("extract");

                                /// prof. Mário
                                String[] palavras = extrato.split(" ");
                                StringBuilder sb= new StringBuilder();
                                for(int i = 0; i < 100 && i < palavras.length; i++) {
                                    sb.append(palavras[i]);
                                    sb.append(" ");
                                }
                                if( palavras.length > 100)
                                    sb.append("...");
                                ///

                                ocuparb.append("Titulo: " + titulo + "\n\n");
                                ocuparb.append("Resumo: " + sb.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            mQueue.add(request);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("ResourceType")
    private void preencher(String recurso) {
        ocupar.setText("");
        String url = null;
        if(recurso.contains("oxigenio")){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Oxigénio");
            numeroelemento = 0;
        }else if(recurso.contains("potassio")){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Potássio");
            numeroelemento = 1;
        }else if(recurso.contains("carbono")){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Carbono");
            numeroelemento = 2;
        }else if(recurso.contains("fosforo")){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Fósforo");
            numeroelemento = 5;
        }else if(recurso.contains("enxofre")){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Enxofre");
            numeroelemento = 6;
        }else if(recurso.contains("neon")){
            url = String.format("https://pt.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Néon");
            numeroelemento = 7;
        }

        try{
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject query = response.getJSONObject("query");
                                JSONObject pages = query.getJSONObject("pages");
                                JSONObject pageid = null;

                                if(numeroelemento == 0){
                                    pageid = pages.getJSONObject("1381");
                                }else if(numeroelemento == 1){
                                    pageid = pages.getJSONObject("5690");
                                }else if(numeroelemento == 2){
                                    pageid = pages.getJSONObject("5673");
                                }else if(numeroelemento == 5){
                                    pageid = pages.getJSONObject("5684");
                                }else if(numeroelemento == 6){
                                    pageid = pages.getJSONObject("5685");
                                }else if(numeroelemento == 7){
                                    pageid = pages.getJSONObject("5676");
                                }


                                String titulo = pageid.getString("title");
                                String extrato = pageid.getString("extract");

                                /// prof. Mário
                                String[] palavras = extrato.split(" ");
                                StringBuilder sb= new StringBuilder();
                                for(int i = 0; i < 100 && i < palavras.length; i++) {
                                    sb.append(palavras[i]);
                                    sb.append(" ");
                                }
                                if( palavras.length > 100)
                                    sb.append("...");
                                ///

                                ocupar.append("Titulo: " + titulo + "\n\n");
                                ocupar.append("Resumo: " + sb.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            mQueue.add(request);
        }catch (Exception e){
            e.printStackTrace();
        }


        /*
        if(numero % 2 == 0){
            gifTextView.setBackgroundResource(R.drawable.jupiter_spinning);
            nome_planeta.setText("Planeta Júpiter");
            Log.d("tag", "jupiter");

            //tabela
            TableRow tr2 = new TableRow(getApplicationContext());
            tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tv2 = new TextView(getApplicationContext());
            tv2.setText("Material A");
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams(new TableRow.LayoutParams(380, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv2.setTextColor(getResources().getColor(R.color.white));
            tv2.setTextSize(15);
            tv2.isClickable();
            tv2.setId(3);
            tr2.addView(tv2);
            tr2.setBackgroundResource(R.color.black);
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "material A");
                }
            });


            tv2 = new TextView(getApplicationContext());
            tv2.setText("Material B");
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams(new TableRow.LayoutParams(380, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv2.setTextColor(getResources().getColor(R.color.white));
            tv2.setTextSize(15);
            tv2.isClickable();
            tv2.setId(3);
            tr2.addView(tv2);
            tr2.setBackgroundResource(R.color.black);
            encher.addView(tr2, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "material B");
                }
            });
        }else{
            gifTextView.setBackgroundResource(R.drawable.earth_spinning);
            nome_planeta.setText("Planeta Terra");

            Log.d("tag", "terra");
            //tabela
            TableRow tr2 = new TableRow(getApplicationContext());
            tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv2 = new TextView(getApplicationContext());
            tv2.setText("Oxigénio");
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams(new TableRow.LayoutParams(380, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv2.setTextColor(getResources().getColor(R.color.white));
            tv2.setTextSize(15);
            tv2.isClickable();
            tv2.setId(1);
            tr2.addView(tv2);
            tr2.setBackgroundResource(R.color.black);
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "Oxigenio");
                }
            });

            tv2 = new TextView(getApplicationContext());
            tv2.setText("Potássio");
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams(new TableRow.LayoutParams(380, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv2.setTextColor(getResources().getColor(R.color.white));
            tv2.setTextSize(15);
            tv2.isClickable();
            tv2.setId(2);
            tr2.addView(tv2);
            tr2.setBackgroundResource(R.color.black);
            encher.addView(tr2, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "Potássio");
                }
            });
        }*/

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentEnciclopediaListener) {
            listener = (FragmentEnciclopediaListener) context;
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
package org.example.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentRanking extends Fragment {
    private FragmentRankingListener listener;
    private Button buttonPT;
    private TextView ranking;
    private TableLayout tableLayout;
    private String nome;
    private SharedPreferences sp;

    public interface FragmentRankingListener {
        void onInputRSent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking, container, false);
        ranking = v.findViewById(R.id.titulo_ranking);
        buttonPT = v.findViewById(R.id.pt_ran);
        tableLayout = v.findViewById(R.id.table_layout);

        buttonPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = "Venho de Ranking";
                listener.onInputRSent(input);
            }
        });

        return v;
    }

    public void updateEditText(CharSequence newText) {
        ranking.setText("Ranking");
        tableLayout.removeAllViews();
        popularTabela(getView(), newText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentRankingListener) {
            listener = (FragmentRankingListener) context;
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

    public void popularTabela(View v, final CharSequence newText){
        tableLayout = v.findViewById(R.id.table_layout);
        RequestQueue mQueue = Volley.newRequestQueue(getContext());


        //vai buscar os primeiros cinco pontuacoes
        //lugar - nome - planeta - pontuacao

        String url = String.format("http://jsonbuscatudo.x10host.com/ranking.php");

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            int num = 1;

                            TableRow tr2 = new TableRow(getApplicationContext());
                            tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            TextView tv2 = new TextView(getApplicationContext());
                            tv2.setText("Rank");
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setLayoutParams(new TableRow.LayoutParams(112, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tr2.addView(tv2);
                            tv2.setTextColor(getResources().getColor(R.color.white));
                            tv2.setTextSize(15);
                            tr2.setBackgroundResource(R.color.black);

                            tv2 = new TextView(getApplicationContext());
                            tv2.setText("Nome");
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setLayoutParams(new TableRow.LayoutParams(112, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tr2.addView(tv2);
                            tv2.setTextColor(getResources().getColor(R.color.white));
                            tv2.setTextSize(15);
                            tr2.setBackgroundResource(R.color.black);

                            tv2 = new TextView(getApplicationContext());
                            tv2.setText("Planeta");
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setLayoutParams(new TableRow.LayoutParams(112, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tr2.addView(tv2);
                            tv2.setTextColor(getResources().getColor(R.color.white));
                            tv2.setTextSize(15);
                            tr2.setBackgroundResource(R.color.black);

                            tv2 = new TextView(getApplicationContext());
                            tv2.setText("Pontuação");
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setLayoutParams(new TableRow.LayoutParams(112, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tr2.addView(tv2);
                            tv2.setTextColor(getResources().getColor(R.color.white));
                            tr2.setBackgroundResource(R.color.black);
                            tv2.setTextSize(15);
                            tableLayout.addView(tr2, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));


                            for(int i=0;i<response.length();i++){
                                JSONObject student = response.getJSONObject(i);
                                String nome = student.getString("utilizador_nome");
                                String planeta = student.getString("planeta_nome");
                                String pontuacao = student.getString("pontuacao");

                                int width = 300;
                                int height = 100;
                                TableRow tr;
                                TextView tv;
                                Session session = new Session(getContext());
                                String nome_ = session.getUser().getNome();

                                if(nome_.contains(nome)){
                                    Log.d("tag", "entrou");
                                    tr = new TableRow(getApplicationContext());
                                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                                    // 1º coluna
                                    tv = new TextView(getApplicationContext());
                                    tv.setText(String.valueOf(num));
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.colorAccent);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(nome);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.colorAccent);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(planeta);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.colorAccent);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(pontuacao);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.colorAccent);
                                    tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                }else{

                                if(num % 2 == 0){
                                    //uma linha
                                    tr = new TableRow(getApplicationContext());
                                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                                    // 1º coluna
                                    tv = new TextView(getApplicationContext());
                                    tv.setText(String.valueOf(num));
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.amarelo);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(nome);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.amarelo);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(planeta);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.amarelo);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(pontuacao);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.amarelo);
                                    tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                }else{
                                    //uma linha
                                    tr = new TableRow(getApplicationContext());
                                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                                    // 1º coluna
                                    tv = new TextView(getApplicationContext());
                                    tv.setText(String.valueOf(num));
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.azul);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(nome);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.azul);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(planeta);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.azul);

                                    tv = new TextView(getApplicationContext());
                                    tv.setText(pontuacao);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setLayoutParams(new TableRow.LayoutParams(width, height));
                                    tr.addView(tv);
                                    tr.setBackgroundResource(R.color.azul);
                                    tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                }
                                }
                                num++;
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                }
        );

        mQueue.add(jsonArrayRequest);
    }
}
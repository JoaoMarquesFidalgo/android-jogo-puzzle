package org.example.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements FragmentA.FragmentAListener, FragmentB.FragmentBListener, FragmentOpcoes.FragmentOpcoesListener,
        FragmentJogo.FragmentJogoListener, FragmentRanking.FragmentRankingListener, FragmentEnciclopedia.FragmentEnciclopediaListener, Fragment_jogo_puzzle.FragmentJPListener,
Fragment_jogo_puzzle_quizz.FragmentJPQListener{
    private FragmentA fragmentA;
    private FragmentB fragmentB;
    private FragmentOpcoes fragmentO;
    private FragmentEnciclopedia fragmentE;
    private FragmentRanking fragmentR;
    private FragmentJogo fragmentJ;
    private Fragment_jogo_puzzle framentJP;
    private Fragment_jogo_puzzle_quizz fragmentJPQ;
    private FrameLayout containerA;
    private FrameLayout containerB;
    private FrameLayout containerO;
    private FrameLayout containerJ;
    private FrameLayout containerR;
    private FrameLayout containerE;
    private FrameLayout containerJP;
    private FrameLayout containerJPQ;
    private TextView imagemfundo;
    private ImageView titulo;
    private SharedPreferences sp;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDefaultSharedPreferences(this).edit().clear().commit();

        //iniciar fragmentos
        fragmentA = new FragmentA();
        fragmentB = new FragmentB();
        fragmentO = new FragmentOpcoes();
        fragmentE = new FragmentEnciclopedia();
        fragmentJ = new FragmentJogo();
        fragmentR = new FragmentRanking();
        framentJP = new Fragment_jogo_puzzle();
        fragmentJPQ = new Fragment_jogo_puzzle_quizz();

        //settar containers dos fragmentos
        containerA = findViewById(R.id.container_a);
        containerB = findViewById(R.id.container_b);
        containerO = findViewById(R.id.container_opcoes);
        containerE = findViewById(R.id.container_enciclopedia);
        containerJ = findViewById(R.id.container_jogo);
        containerR = findViewById(R.id.container_ranking);
        containerJP = findViewById(R.id.container_jogo_puzzle);
        containerJPQ = findViewById(R.id.container_jogo_puzzle_quizz);

        //settar background
        imagemfundo = findViewById(R.id.background);
        titulo = findViewById(R.id.titulo);
        imagemfundo.setBackgroundResource(R.raw.imagem_fundo);
        titulo.setImageResource(R.raw.titulo);

        //fazer transaction dos fragmentos para nos containers em xml
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragmentA)
                .replace(R.id.container_b, fragmentB)
                .replace(R.id.container_opcoes, fragmentO)
                .replace(R.id.container_enciclopedia, fragmentE)
                .replace(R.id.container_jogo, fragmentJ)
                .replace(R.id.container_ranking, fragmentR)
                .replace(R.id.container_jogo_puzzle, framentJP)
                .replace(R.id.container_jogo_puzzle_quizz, fragmentJPQ)
                .commit();
    }

    //chamado quando saimos da classe FragmentA
    @Override
    public void onInputASent(CharSequence input) {
        fragmentB.updateEditText(input);
        containerA.setVisibility(View.GONE);
        containerB.setVisibility(View.VISIBLE);
        containerO.setVisibility(View.GONE);
    }

    //chamado quando saimos da classe FragmentB que tem varios botoes
    @Override
    public void onInputBSent(CharSequence input, int num) {
        switch (num){
            //entra em jogar
            case 0:
                fragmentJ.updateEditText(input);
                containerA.setVisibility(View.GONE);
                containerB.setVisibility(View.GONE);
                containerO.setVisibility(View.GONE);
                containerR.setVisibility(View.GONE);
                containerJ.setVisibility(View.VISIBLE);
                containerE.setVisibility(View.GONE);
                break;
            //entra em ranking
            case 1:
                fragmentR.updateEditText(input);
                containerA.setVisibility(View.GONE);
                containerB.setVisibility(View.GONE);
                containerO.setVisibility(View.GONE);
                containerR.setVisibility(View.VISIBLE);
                containerJ.setVisibility(View.GONE);
                containerE.setVisibility(View.GONE);
                break;
            //entra em enciclopedia
            case 2:
                fragmentE.updateEditText(input);
                containerA.setVisibility(View.GONE);
                containerB.setVisibility(View.GONE);
                containerO.setVisibility(View.GONE);
                containerR.setVisibility(View.GONE);
                containerJ.setVisibility(View.GONE);
                containerE.setVisibility(View.VISIBLE);
                break;
            //entra em opcoes
            case 3:
                fragmentO.updateEditText(input);
                containerA.setVisibility(View.GONE);
                containerB.setVisibility(View.GONE);
                containerO.setVisibility(View.VISIBLE);
                containerR.setVisibility(View.GONE);
                containerJ.setVisibility(View.GONE);
                containerE.setVisibility(View.GONE);
                break;
            //volta atras para a pagina login
            case -1:
                fragmentA.updateEditText(input);
                containerA.setVisibility(View.VISIBLE);
                containerB.setVisibility(View.GONE);
                containerO.setVisibility(View.GONE);
                containerR.setVisibility(View.GONE);
                containerJ.setVisibility(View.GONE);
                containerE.setVisibility(View.GONE);
                break;
            default:
                try{
                    Log.d("tag", "ocorreu um erro");
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
        }


        //chamado quando saimos das classes FragmentO/J/R/E e vamos para o menu principal
    @Override
    public void onInputOSent(CharSequence input, String cara_) {
        fragmentB.updateDados(input, cara_);
        containerA.setVisibility(View.GONE);
        containerB.setVisibility(View.VISIBLE);
        containerO.setVisibility(View.GONE);
        containerR.setVisibility(View.GONE);
        containerJ.setVisibility(View.GONE);
        containerE.setVisibility(View.GONE);
    }

    @Override
    public void onInputESent(CharSequence input) {
        fragmentB.updateEditText(input);
        containerA.setVisibility(View.GONE);
        containerB.setVisibility(View.VISIBLE);
        containerO.setVisibility(View.GONE);
        containerR.setVisibility(View.GONE);
        containerJ.setVisibility(View.GONE);
        containerE.setVisibility(View.GONE);
    }

    @Override
    public void onInputJSent(CharSequence input)    {
        fragmentB.updateEditText(input);
        containerA.setVisibility(View.GONE);
        containerB.setVisibility(View.VISIBLE);
        containerO.setVisibility(View.GONE);
        containerR.setVisibility(View.GONE);
        containerJ.setVisibility(View.GONE);
        containerE.setVisibility(View.GONE);
    }

    @Override
    public void onInputRSent(CharSequence input) {
        fragmentB.updateEditText(input);
        containerA.setVisibility(View.GONE);
        containerB.setVisibility(View.VISIBLE);
        containerO.setVisibility(View.GONE);
        containerR.setVisibility(View.GONE);
        containerJ.setVisibility(View.GONE);
        containerE.setVisibility(View.GONE);
    }

    @Override
    public void onInputJSent(String planeta, String nivel, int score) {
        if(nivel.contains("4")){
            fragmentJPQ.updateEditText(planeta, nivel, score);
            containerJ.setVisibility(View.GONE);
            containerJP.setVisibility(View.GONE);
            containerJPQ.setVisibility(View.VISIBLE);
        }else{
            framentJP.updateEditText(planeta, nivel, score);
            containerJ.setVisibility(View.GONE);
            containerJP.setVisibility(View.VISIBLE);
            containerJPQ.setVisibility(View.GONE);
        }
    }

    @Override
    public void onInputJPSent(String planeta, String nivel, int score) {
        fragmentJ.updateEditText("something");
        containerJ.setVisibility(View.VISIBLE);
        containerJP.setVisibility(View.GONE);
    }

    @Override
    public void onInputJPQSent(CharSequence input) {
        framentJP.updateEditText("something", "something", 1);
        containerJP.setVisibility(View.VISIBLE);
        containerJ.setVisibility(View.GONE);
        containerJPQ.setVisibility(View.GONE);
    }
}
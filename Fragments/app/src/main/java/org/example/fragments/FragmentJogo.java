package org.example.fragments;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FragmentJogo extends Fragment{
    private FragmentJogoListener listener;
    private Button buttonPT;
    TextView jogo, titulo_jogo2;
    ImageView terra, jupiter, nivel1, nivel2, nivel3, nivel4, imagem_planeta_grande;
    RelativeLayout layout_planeta, CL_background;
    boolean jupiter_desbl = false;
    int desaf4 = 0;

    public interface FragmentJogoListener {
        void onInputJSent(CharSequence input);

        void onInputJSent(String planeta, String nivel, int score);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jogo, container, false);
        jogo = v.findViewById(R.id.titulo_jogo);
        buttonPT = v.findViewById(R.id.pt_jogo);
        terra = v.findViewById(R.id.terra);
        jupiter = v.findViewById(R.id.jupiter);
        layout_planeta = v.findViewById(R.id.layout_planeta);
        nivel1 = v.findViewById(R.id.nivel1);
        nivel2 = v.findViewById(R.id.nivel2);
        nivel3 = v.findViewById(R.id.nivel3);
        nivel4 = v.findViewById(R.id.nivel4);
        CL_background = v.findViewById(R.id.CL_background);
        titulo_jogo2 = v.findViewById(R.id.titulo_jogo2);
        imagem_planeta_grande = v.findViewById(R.id.imagem_planeta_grande);

        buttonPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = "Venho de Jogo";
                listener.onInputJSent(input);
            }
        });





        //subfragment
        /*
        container_jogo_puzzle = v.findViewById(R.id.container_jogo_puzzle);
        fragment = new Fragment_jogo_puzzle();
        fragment2 = new FragmentJogo();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container_jogo_puzzle, fragment).commit();
*/



        return v;
    }

    private void preencher(String planeta) {
        Session session = new Session(getContext());
        if(planeta.contains("terra")){
            imagem_planeta_grande.setImageResource(R.drawable.terra);
            final int desaf1 = session.getUser().getPontuacao().getPontuacao_desaf_1();
            titulo_jogo2.setText("Planeta Terra");
            if(desaf1 == 0){
                nivel1.setImageResource(R.drawable.orange);
            }else if(desaf1 == 5){
                nivel1.setImageResource(R.drawable.green);
            }else if(desaf1 > 0 && desaf1 < 5){
                nivel1.setImageResource(R.drawable.white);
            }

            nivel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "nivel1 click");
                    listener.onInputJSent("terra", "nivel1", desaf1);
                }
            });

            if(desaf1 > 0){
                final int desaf2 = session.getUser().getPontuacao().getPontuacao_desaf_2();
                if(desaf2  == 0){
                    nivel2.setImageResource(R.drawable.orange);
                }else if(desaf2  == 5){
                    nivel2.setImageResource(R.drawable.green);
                }else if(desaf2  > 0 && desaf2 < 5){
                    nivel2.setImageResource(R.drawable.white);
                }else{
                    nivel2.setImageResource(R.drawable.red);
                }

                nivel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("tag", "nivel2 click");
                        listener.onInputJSent("terra", "nivel2", desaf2);
                    }
                });

                if(desaf2 > 0){
                    final int desaf3 = session.getUser().getPontuacao().getPontuacao_desaf_3();

                    if(desaf3  == 0){
                        nivel3.setImageResource(R.drawable.orange);
                    }else if(desaf3  == 5){
                        nivel3.setImageResource(R.drawable.green);
                    }else if(desaf3  > 0 && desaf2 < 5){
                        nivel3.setImageResource(R.drawable.white);
                    }else{
                        nivel3.setImageResource(R.drawable.red);
                    }

                    nivel3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("tag", "nivel3 click");
                            listener.onInputJSent("terra", "nivel3", desaf3);
                        }
                    });


                    if(desaf3 > 0){
                        desaf4 = session.getUser().getPontuacao().getPontuacao_desaf_4();

                        if(desaf4  == 0){
                            nivel4.setImageResource(R.drawable.orange);
                        }else if(desaf4  == 5){
                            nivel4.setImageResource(R.drawable.green);
                        }else if(desaf4  > 0 && desaf4 < 5){
                            nivel4.setImageResource(R.drawable.white);
                        }else{
                            nivel4.setImageResource(R.drawable.red);
                        }

                        nivel4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("tag", "nivel4 click");
                                listener.onInputJSent("terra", "nivel4", desaf4);
                            }
                        });


                        if(desaf4 > 0){
                            //desbloqueia jupiter
                            jupiter.setClickable(true);
                            jupiter_desbl = true;

                        }
                    }
                }

            }

        }else{
            titulo_jogo2.setText("Planeta Júpiter");
            imagem_planeta_grande.setImageResource(R.drawable.jupiter2);

            final int desaf5 = session.getUser().getPontuacao().getPontuacao_desaf_5();

            if(desaf5  == 0){
                nivel1.setImageResource(R.drawable.orange);
            }else if(desaf5  == 5){
                nivel1.setImageResource(R.drawable.green);
            }else if(desaf5  > 0 && desaf5 < 5){
                nivel1.setImageResource(R.drawable.white);
            }else{
                nivel1.setImageResource(R.drawable.red);
            }

            nivel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "nivel1 click");
                    listener.onInputJSent("jupiter", "nivel5", desaf5);
                }
            });

            if(desaf5 > 0){
                final int desaf6 = session.getUser().getPontuacao().getPontuacao_desaf_6();

                if(desaf6  == 0){
                    nivel2.setImageResource(R.drawable.orange);
                }else if(desaf6  == 5){
                    nivel2.setImageResource(R.drawable.green);
                }else if(desaf6  > 0 && desaf6 < 5){
                    nivel2.setImageResource(R.drawable.white);
                }else{
                    nivel2.setImageResource(R.drawable.red);
                }

                nivel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("tag", "nivel2 click");
                        listener.onInputJSent("jupiter", "nivel6", desaf6);
                    }
                });

                if(desaf6 > 0) {
                    final int desaf7 = session.getUser().getPontuacao().getPontuacao_desaf_7();

                    if (desaf7 == 0) {
                        nivel3.setImageResource(R.drawable.orange);
                    } else if (desaf7 == 5) {
                        nivel3.setImageResource(R.drawable.green);
                    } else if (desaf7 > 0 && desaf7 < 5) {
                        nivel3.setImageResource(R.drawable.white);
                    } else {
                        nivel3.setImageResource(R.drawable.red);
                    }

                    nivel3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("tag", "nivel4 click");
                            listener.onInputJSent("jupiter", "nivel7", desaf7);
                        }
                    });


                    if(desaf7 > 0) {
                        final int desaf8 = session.getUser().getPontuacao().getPontuacao_desaf_8();

                        if (desaf8 == 0) {
                            nivel4.setImageResource(R.drawable.orange);
                        } else if (desaf8 == 5) {
                            nivel4.setImageResource(R.drawable.green);
                        } else if (desaf8 > 0 && desaf8 < 5) {
                            nivel4.setImageResource(R.drawable.white);
                        } else {
                            nivel4.setImageResource(R.drawable.red);
                        }

                        nivel4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("tag", "nivel4 click");
                                listener.onInputJSent("jupiter", "nivel8", desaf8);
                            }
                        });
                    }

                }

            }
        }
    }


    public void updateEditText(CharSequence newText) {
        jogo.setText("Escolha o nível");

        terra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL_background.setVisibility(View.GONE);
                layout_planeta.setVisibility(View.VISIBLE);
                preencher("terra");
            }
        });
        Session session = new Session(getContext());
        final int desaf4 = session.getUser().getPontuacao().getPontuacao_desaf_4();

        if(desaf4 > 0){
            jupiter.setClickable(true);
            jupiter.setImageResource(R.drawable.jupiter2);
            jupiter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CL_background.setVisibility(View.GONE);
                    layout_planeta.setVisibility(View.VISIBLE);
                    preencher("jupiter");
                }
            });

        }

        layout_planeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_planeta.setVisibility(View.GONE);
                CL_background.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentJogoListener) {
            listener = (FragmentJogoListener) context;
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
package org.example.fragments;

public class Utilizador {
    private String nome;
    private String facebook;
    private Planeta planeta;
    private Pontuacao pontuacao;
    private Opcoes opcoes;
    private Foto foto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public Pontuacao getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Pontuacao pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Opcoes getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(Opcoes opcoes) {
        this.opcoes = opcoes;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}

package com.trabalhoandroid.appdereceitas;

public class Receita {

    private String nomeReceita;
    private String ingredientes;
    private String instrucoes;

    public String getNomeReceita()
    {
        return nomeReceita;
    }
    public void setNomeReceita(String nomeReceita) { this.nomeReceita = nomeReceita; }

    public String getIngredientes()
    {
        return ingredientes;
    }
    public void setIngredientes(String ingredientes)
    {
        this.ingredientes = ingredientes;
    }

    public String getInstrucoes()
    {
        return instrucoes;
    }
    public void setInstrucoes(String instrucoes)
    {
        this.instrucoes = instrucoes;
    }
}

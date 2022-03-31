package com.trabalhoandroid.receitasv2;

//Criação da classe que vai conter as informações
public class DadosReceita {

    private String itemNome;
    private String itemIngredientes;
    private String itemDescricao;
    private String itemImagem;
    private String key;

    public DadosReceita() {

    }

    //Este é um simples construtor
    public DadosReceita(String itemNome, String itemIngredientes, String itemDescricao, String itemImagem) {
        this.itemNome = itemNome;
        this.itemIngredientes = itemIngredientes;
        this.itemDescricao = itemDescricao;
        this.itemImagem = itemImagem;
    }

    //Estes são getters
    public String getItemNome() {
        return itemNome;
    }

    public String getItemIngredientes() {
        return itemIngredientes;
    }

    public String getItemDescricao() {
        return itemDescricao;
    }

    public String getItemImagem() {
        return itemImagem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}

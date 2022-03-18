package com.trabalhoandroid.testereceitas;

public class DadosReceita {

    private String itemNome;
    private String itemIngredientes;
    private String itemDescricao;
    private String itemImage;

    public DadosReceita() {

    }

    public DadosReceita(String itemNome, String itemIngredientes, String itemDescricao, String itemImage) {
        this.itemNome = itemNome;
        this.itemIngredientes = itemIngredientes;
        this.itemDescricao = itemDescricao;
        this.itemImage = itemImage;
    }

    public String getItemNome() {
        return itemNome;
    }

    public String getItemIngredientes() {
        return itemIngredientes;
    }

    public String getItemDescricao() {
        return itemDescricao;
    }

    public String getItemImage() {
        return itemImage;
    }
}

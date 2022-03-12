package com.trabalhoandroid.appdereceitas;

import java.util.ArrayList;
import java.util.List;

public class ListaReceitas {
    private static final ListaReceitas instance = new ListaReceitas();
    private List<Receita> receitas;

    public ListaReceitas()
    {
        receitas = new ArrayList<>();
    }

    public static ListaReceitas getInstance()
    {
        return instance;
    }

    public List<Receita> getReceitas()
    {
        return receitas;
    }

    public void addReceita(Receita receita)
    {
        this.receitas.add(receita);
    }
}

package com.trabalhoandroid.appdereceitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ListaReceitas receitas = ListaReceitas.getInstance();
        int indx = getIntent().getExtras().getInt("index");
        Receita receita = receitas.getReceitas().get(indx);

        ((TextView)findViewById(R.id.txtNomeReceita)).setText(receita.getNomeReceita());
        ((TextView)findViewById(R.id.txtIngredientes)).setText(receita.getNomeReceita());
        ((TextView)findViewById(R.id.txtInstrucoes)).setText(receita.getNomeReceita());
    }

    public void VoltarMenu(View v) {
        Intent intent = new Intent(MainActivity4.this,
                com.trabalhoandroid.appdereceitas.MainActivity.class);
        startActivity(intent);
    }
}
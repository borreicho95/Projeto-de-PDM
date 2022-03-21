package com.trabalhoandroid.testereceitas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetalhesActivity extends AppCompatActivity {

    TextView receitaDescricao, receitaIngredientes;
    ImageView receitaImagem;

    //Esta classe é antiga MainActivity4
    //Serve para ver as informações da aplicação.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        receitaIngredientes = (TextView)findViewById(R.id.txtIngredientes);
        receitaDescricao = (TextView)findViewById(R.id.txtDescricao);
        receitaImagem = (ImageView)findViewById(R.id.idImagem2);

        Bundle mBundle = getIntent().getExtras();

        //O que isto faz é ir buscar as informações que estão guardadas e insere-as nos seu sitios.
        if(mBundle!=null){

            receitaIngredientes.setText(mBundle.getString("Ingredientes"));
            receitaDescricao.setText(mBundle.getString("Descricao"));

            Glide.with(this).load(mBundle.getString("Imagem")).into(receitaImagem);
        }

    }
}
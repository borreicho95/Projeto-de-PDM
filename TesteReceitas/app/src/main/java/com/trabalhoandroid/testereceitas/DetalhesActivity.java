package com.trabalhoandroid.testereceitas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetalhesActivity extends AppCompatActivity {

    TextView receitaDescricao;
    ImageView receitaImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        receitaDescricao = (TextView)findViewById(R.id.txtDescricao);
        receitaImagem = (ImageView)findViewById(R.id.idImagem2);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){

            receitaDescricao.setText(mBundle.getString("Descricao"));

            Glide.with(this).load(mBundle.getString("Imagem")).into(receitaImagem);
        }

    }
}
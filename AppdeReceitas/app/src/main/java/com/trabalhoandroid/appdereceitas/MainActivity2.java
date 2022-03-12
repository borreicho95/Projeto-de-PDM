package com.trabalhoandroid.appdereceitas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnImgReceita = findViewById(R.id.btnImgReceita);
        Button btnadicionarreceita = findViewById(R.id.btnadicionarreceita);

        btnImgReceita.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(selectedImage);
        }
    }

    public void AddReceita(View v) {
        ListaReceitas receitas = ListaReceitas.getInstance();

        Receita receita = new Receita();
        String nomeReceita = ((EditText)findViewById(R.id.txtNomeReceita)).getText().toString();
        receita.setNomeReceita(nomeReceita);
        String ingredientes = ((EditText)findViewById(R.id.listIngredientes)).getText().toString();
        receita.setIngredientes(ingredientes);
        String instrucoes = ((EditText)findViewById(R.id.listInstrucoes)).getText().toString();
        receita.setInstrucoes(instrucoes);

        receitas.addReceita(receita);

        Intent intent = new Intent(MainActivity2.this,
                MainActivity.class);
        startActivity(intent);

    }
}
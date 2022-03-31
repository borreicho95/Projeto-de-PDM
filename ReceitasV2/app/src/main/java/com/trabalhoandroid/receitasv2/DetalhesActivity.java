package com.trabalhoandroid.receitasv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetalhesActivity extends AppCompatActivity {

    TextView receitaDescricao, receitaIngredientes, receitaNome;
    ImageView receitaImagem;
    String key="";
    String imageUrl="";

    //Esta classe é antiga MainActivity4
    //Serve para ver as informações da aplicação.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        receitaNome = (TextView)findViewById(R.id.txtNomeReceita);
        receitaIngredientes = (TextView)findViewById(R.id.txtIngredientes);
        receitaDescricao = (TextView)findViewById(R.id.txtDescricao);
        receitaImagem = (ImageView)findViewById(R.id.idImagem2);

        Bundle mBundle = getIntent().getExtras();

        //O que isto faz é ir buscar as informações que estão guardadas e insere-as nos seu sitios.
        if(mBundle!=null){

            receitaNome.setText(mBundle.getString("Titulo"));
            receitaIngredientes.setText(mBundle.getString("Ingredientes"));
            receitaDescricao.setText(mBundle.getString("Descricao"));
            key = mBundle.getString("keyValue");
            imageUrl = mBundle.getString("Imagem");

            Glide.with(this).load(mBundle.getString("Imagem")).into(receitaImagem);
        }

    }

    public void btnApagarReceita(View view) {

        final DatabaseReference reference = FirebaseDatabase.getInstance("https://receitas-bb125-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Receitas");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                reference.child(key).removeValue();
                Toast.makeText(getApplicationContext(),"Receita Apagada",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }

    public void btnEditarReceita(View view) {
        startActivity(new Intent(getApplicationContext(),EditarReceita.class)
                .putExtra("TituloKey",receitaNome.getText().toString())
                .putExtra("DescricaoKey",receitaDescricao.getText().toString())
                .putExtra("IngredientesKey",receitaIngredientes.getText().toString())
                .putExtra("imagemUrlAntiga", imageUrl)
                .putExtra("key",key)
        );
    }
}
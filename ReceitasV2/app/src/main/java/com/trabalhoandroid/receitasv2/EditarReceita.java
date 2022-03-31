package com.trabalhoandroid.receitasv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditarReceita extends AppCompatActivity {

    ImageView receitaImagem;
    Uri uri;
    EditText txt_nome,txt_ingredientes,txt_descricao;
    String imagemUrl;
    String key, imagemUrlAntiga;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String receitaDescricao, receitaIngredientes, receitaNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_receita);

        receitaImagem = (ImageView)findViewById(R.id.idReceitaImagem);
        txt_nome = (EditText)findViewById(R.id.txtNomeReceita);
        txt_ingredientes = (EditText)findViewById(R.id.txtIngredientes2);
        txt_descricao = (EditText)findViewById(R.id.txtDescricao2);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            Glide.with(getApplicationContext()).load(bundle.getString("imagemUrlAntiga")).into(receitaImagem);

            txt_nome.setText(bundle.getString("TituloKey"));
            txt_ingredientes.setText(bundle.getString("IngredientesKey"));
            txt_descricao.setText(bundle.getString("DescricaoKey"));
            key = bundle.getString("key");
            imagemUrlAntiga = bundle.getString("imagemUrlAntiga");

        }

        databaseReference = FirebaseDatabase.getInstance("https://receitas-bb125-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Receitas").child(key);


    }

    public void btnAdicionarImagem(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            uri = data.getData();
            receitaImagem.setImageURI(uri);

        }
        else Toast.makeText(getApplicationContext(), "Imagem nao selecionada", Toast.LENGTH_SHORT).show();

    }

    public void btnAtualizarReceita(View view) {

        receitaNome = txt_nome.getText().toString().trim();
        receitaIngredientes = txt_ingredientes.getText().toString().trim();
        receitaDescricao = txt_descricao.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Receita Uplading....");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance().getReference().child("ReceitaImagem").child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {

            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while(!uriTask.isComplete());
            Uri urlImagem = uriTask.getResult();
            imagemUrl = urlImagem.toString();
            //Chamar a funçao que envia a receita para o firebase
            uploadReceita();
            progressDialog.dismiss();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }).addOnFailureListener(e -> progressDialog.dismiss());

    }

    public void uploadReceita(){


        //Guardar informações no Array
        DadosReceita dadosReceita = new DadosReceita(
                receitaNome,
                receitaIngredientes,
                receitaDescricao,
                imagemUrl

        );

        databaseReference.setValue(dadosReceita).addOnCompleteListener(task -> {
            StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(imagemUrlAntiga);
            storageReferenceNew.delete();
            Toast.makeText(getApplicationContext(), "Dados Atualizados", Toast.LENGTH_SHORT).show();
        });

    }
}
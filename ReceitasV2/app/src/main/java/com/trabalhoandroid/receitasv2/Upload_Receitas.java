package com.trabalhoandroid.receitasv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Calendar;

public class Upload_Receitas extends AppCompatActivity {

    ImageView receitaImagem;
    Uri uri;
    EditText txt_nome,txt_ingredientes,txt_descricao;
    String imagemUrl;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_receitas);

        receitaImagem = (ImageView)findViewById(R.id.idReceitaImagem);
        txt_nome = (EditText)findViewById(R.id.txtNomeReceita);
        txt_ingredientes = (EditText)findViewById(R.id.txtIngredientes2);
        txt_descricao = (EditText)findViewById(R.id.txtDescricao2);

    }

    //Adiciona a imagem ao ImageView
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
        else Toast.makeText(this, "Imagem nao selecionada", Toast.LENGTH_SHORT).show();

    }

    //Função de upload da imagem para o firebase
    public void uploadImagem(){

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("ReceitaImagem").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Receita Uplading....");
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {

            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while(!uriTask.isComplete());
            Uri urlImagem = uriTask.getResult();
            imagemUrl = urlImagem.toString();
            //Chamar a funçao que envia a receita para o firebase
            uploadReceita();
            progressDialog.dismiss();
        }).addOnFailureListener(e -> progressDialog.dismiss());



    }

    //Chama a função acima que envia a imagem e envia também a receita
    public void btnUploadReceita(View view) {

        uploadImagem();

    }


    //Função para que seja possível fazer o upload da receita
    public void uploadReceita(){


        //Guardar informações no Array
        DadosReceita dadosReceita = new DadosReceita(
                txt_nome.getText().toString(),
                txt_ingredientes.getText().toString(),
                txt_descricao.getText().toString(),
                imagemUrl
        );

        //Usar a data para guardar as informações no firebase


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        databaseReference = FirebaseDatabase.getInstance("https://receitas-bb125-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Receitas");

        try {
            databaseReference.child(uid).setValue(dadosReceita).addOnCompleteListener(task -> {

                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Receita Uploaded", Toast.LENGTH_SHORT).show();
                    finish();

                }



            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show());
        }catch (Exception exception){
            Log.d("kebab", exception.getMessage());
        }





    }


}
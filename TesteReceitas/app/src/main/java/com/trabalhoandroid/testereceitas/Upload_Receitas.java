package com.trabalhoandroid.testereceitas;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Upload_Receitas extends AppCompatActivity {

    ImageView receitaImagem;
    Uri uri;
    EditText txt_nome,txt_ingredientes,txt_descricao;
    String imagemUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_receitas);

        receitaImagem = (ImageView)findViewById(R.id.idReceitaImagem);
        txt_nome = (EditText)findViewById(R.id.txtNomeReceita);
        txt_ingredientes = (EditText)findViewById(R.id.txtIngredientes);
        txt_descricao = (EditText)findViewById(R.id.txtDescricao2);

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
        else Toast.makeText(this, "Imagem nao selecionada", Toast.LENGTH_SHORT).show();

    }

    public void uploadImage(){

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("ReceitaImagem").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Receita Uplading....");
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImagem = uriTask.getResult();
                imagemUrl = urlImagem.toString();
                uploadRecipe();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });



    }


    public void btnUploadReceita(View view) {

        uploadImage();

    }

    public void uploadRecipe(){



        DadosReceita dadosReceita = new DadosReceita(
                txt_nome.getText().toString(),
                txt_ingredientes.getText().toString(),
                txt_descricao.getText().toString(),
                imagemUrl
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Receita")
                .child(myCurrentDateTime).setValue(dadosReceita).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(Upload_Receitas.this, "Receita Uploaded", Toast.LENGTH_SHORT).show();

                    finish();

                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload_Receitas.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });




    }


}
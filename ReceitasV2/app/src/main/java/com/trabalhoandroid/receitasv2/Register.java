package com.trabalhoandroid.receitasv2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText txtNomeCompleto, txtEmail, txtPassword, txtPhone;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNomeCompleto = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword= findViewById(R.id.txtPassword);
        txtPhone= findViewById(R.id.txtPhone);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }

    public void txtFazerLogin(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

    public void btnRegistar(View view) {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        //Teste para ver se o utilizador inseriu o email e a password
        if(TextUtils.isEmpty(email)) {
            txtEmail.setError("É necessário email.");
            return;
        }

        if(TextUtils.isEmpty(password)) {
            txtPassword.setError("É necessário password.");
            return;
        }

        //registar utilizador no firebase

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                Toast.makeText(getApplicationContext(), "Utilizador Criado",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }else {
                Toast.makeText(getApplicationContext(), "Erro ao criar utilizador",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
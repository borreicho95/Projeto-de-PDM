package com.trabalhoandroid.testereceitas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<DadosReceita> mReceitasList;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Isto serve para gerir as informações e colocá-las numa grid
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        //Isto é o que faz aparecer aquela pequena janela com a rodinha de carregar
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("A carregar receitas...");

        mReceitasList = new ArrayList<>();

        mAdapter myAdapter = new mAdapter(MainActivity.this,mReceitasList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Receita");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mReceitasList.clear();
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    DadosReceita dadosReceita = itemSnapshot.getValue(DadosReceita.class);

                    mReceitasList.add(dadosReceita);
                }
                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
            }
        });
    }

    //Isto é só para abrir a próxima activity
    public void btnUploadReceita(View view) {
        startActivity(new Intent(this, Upload_Receitas.class));
    }
}
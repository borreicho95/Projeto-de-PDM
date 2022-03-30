package com.trabalhoandroid.testereceitas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<DadosReceita> mReceitasList;
    MyAdapter myAdapter;
    ValueEventListener eventListener;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    EditText txt_Procurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Isto serve para gerir as informações e colocá-las numa grid
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        txt_Procurar = (EditText)findViewById(R.id.txt_procurar);

        //Isto é o que faz aparecer aquela pequena janela com a rodinha de carregar
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("A carregar receitas...");

        mReceitasList = new ArrayList<>();

        myAdapter = new MyAdapter(MainActivity.this,mReceitasList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance("https://receitas-bb125-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Receitas");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mReceitasList.clear();
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    DadosReceita dadosReceita = itemSnapshot.getValue(DadosReceita.class);
                    dadosReceita.setKey(itemSnapshot.getKey());
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

        txt_Procurar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());

            }
        });
    }

    private void filter(String text) {

        ArrayList<DadosReceita> filterList = new ArrayList<>();

        for(DadosReceita item: mReceitasList) {

            if(item.getItemNome().toLowerCase().contains(text.toLowerCase())){

                filterList.add(item);

            }

        }

        myAdapter.filteredList(filterList);

    }

    //Isto é só para abrir a próxima activity
    public void btnUploadReceita(View view) {
        startActivity(new Intent(this, Upload_Receitas.class));
    }
}
package com.trabalhoandroid.appdereceitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnadicionarreceita;
    Button btnverreceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadicionarreceita = findViewById(R.id.btnadicionarreceita);
        btnverreceitas = findViewById(R.id.btnverreceitas);

        btnadicionarreceita.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    MainActivity2.class);
            startActivity(intent);
        });

        btnverreceitas.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this,
                        MainActivity3.class);
                startActivity(intent);
        });


    }
}
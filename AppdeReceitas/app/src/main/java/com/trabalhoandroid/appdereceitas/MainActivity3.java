package com.trabalhoandroid.appdereceitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        ListaReceitas receitas = ListaReceitas.getInstance();
        LinearLayout container = findViewById(R.id.llContainer);
        container.removeAllViews();
        for (Receita receita : receitas.getReceitas())
        {
            TextView txtView = new TextView(getApplicationContext());
            txtView.setText(receita.getNomeReceita());
            txtView.setBackgroundColor(getResources().getColor(R.color.purple_200));
            txtView.setPadding(40,40,40,40);
            txtView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent inAct3 = new Intent(getApplicationContext(),MainActivity4.class);
                    int index = receitas.getReceitas().indexOf(receita);
                    inAct3.putExtra("index",index);
                    startActivity(inAct3);
                }
            });
            container.addView(txtView);
        }
    }
}
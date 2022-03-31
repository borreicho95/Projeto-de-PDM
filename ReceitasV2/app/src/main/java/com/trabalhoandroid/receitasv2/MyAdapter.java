package com.trabalhoandroid.receitasv2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<ReceitasViewHolder>{

    private Context mContext;
    private List<DadosReceita> mReceitasList;

    public MyAdapter(Context mContext, List<DadosReceita> mReceitasList) {
        this.mContext = mContext;
        this.mReceitasList = mReceitasList;
    }

    @NonNull
    @Override
    public ReceitasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_item,viewGroup,false);

        return new ReceitasViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReceitasViewHolder receitasViewHolder, int i) {

        Glide.with(mContext).load(mReceitasList.get(i).getItemImagem())
                .into(receitasViewHolder.imageView);

        receitasViewHolder.mTitulo.setText(mReceitasList.get(i).getItemNome());
        receitasViewHolder.mIngredientes.setText(mReceitasList.get(i).getItemIngredientes());
        receitasViewHolder.mDescricao.setText(mReceitasList.get(i).getItemDescricao());
        receitasViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,DetalhesActivity.class);
                intent.putExtra("Imagem",mReceitasList.get(receitasViewHolder.getAdapterPosition()).getItemImagem());
                intent.putExtra("Titulo",mReceitasList.get(receitasViewHolder.getAdapterPosition()).getItemNome());
                intent.putExtra("Ingredientes",mReceitasList.get(receitasViewHolder.getAdapterPosition()).getItemIngredientes());
                intent.putExtra("Descricao",mReceitasList.get(receitasViewHolder.getAdapterPosition()).getItemDescricao());
                intent.putExtra("keyValue",mReceitasList.get(receitasViewHolder.getAdapterPosition()).getKey());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mReceitasList.size();
    }

    public void filteredList(ArrayList<DadosReceita> filterList) {

        mReceitasList = filterList;
        notifyDataSetChanged();

    }
}

class ReceitasViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView mTitulo, mIngredientes ,mDescricao, mUserName;
    CardView mCardView;

    public ReceitasViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.idImagem);
        mTitulo = itemView.findViewById(R.id.idTitulo);
        mIngredientes = itemView.findViewById(R.id.idIngredientes);
        mDescricao= itemView.findViewById(R.id.idDescricao);
        mCardView = itemView.findViewById(R.id.myCardView);
    }
}

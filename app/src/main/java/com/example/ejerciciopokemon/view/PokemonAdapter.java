package com.example.ejerciciopokemon.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ejerciciopokemon.MainActivity;
import com.example.ejerciciopokemon.R;
import com.example.ejerciciopokemon.activity_edit_delete;
import com.example.ejerciciopokemon.entity.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<Pokemon> misPokemons;
    private Context context;
    private OnItemClickListener listener;

    public PokemonAdapter(Context context, OnItemClickListener listener){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Pokemon pokemon);

    }
    @NonNull
    @Override
    public PokemonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item,parent,false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.MyViewHolder holder, int position) {
        if(misPokemons != null){
            final Pokemon current = misPokemons.get(position);
            Uri imageUri = Uri.parse(current.getImageID());

            Glide.with(context)
                    .load(imageUri)
                    .override(500, 500)
                    .into(holder.imgPokemon);
            holder.tvPokemon.setText(current.getName());
            holder.tvType.setText(current.getType());


            holder.pokemonCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   listener.onItemClick(current);
                }

            });


        }
    }


    @Override
    public int getItemCount() {
        int elementos = 0;
        if(misPokemons != null){
            elementos = misPokemons.size();
        }
        return elementos;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPokemon, tvType;
        ImageView imgPokemon;
        CardView pokemonCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPokemon = itemView.findViewById(R.id.imgPokemon);
            tvPokemon = itemView.findViewById(R.id.tvPokemon);
            tvType = itemView.findViewById(R.id.tvType);
            pokemonCard = itemView.findViewById(R.id.pokemonCard);

        }
    }

    public void setMisPokemons(List<Pokemon> misPokemons){
        this.misPokemons = misPokemons;
        notifyDataSetChanged();
    }
}

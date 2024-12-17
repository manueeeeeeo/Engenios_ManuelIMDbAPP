package com.clase.engenios_manuelimdbapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clase.engenios_manuelimdbapp.MovieDetailsActivity;
import com.clase.engenios_manuelimdbapp.R;
import com.clase.engenios_manuelimdbapp.models.MovieOverviewResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapters extends RecyclerView.Adapter<MovieAdapters.MovieViewHolder> {

    private Context context;
    private List<MovieOverviewResponse> movieList;
    private Toast mensajeToast = null;

    public MovieAdapters(Context context, List<MovieOverviewResponse> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieOverviewResponse movie = movieList.get(position);

        Picasso.get()
                .load(movie.getImageUrl())
                .placeholder(R.drawable.baseline_autorenew_24)
                .error(R.drawable.por_defecto)
                .into(holder.movieImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieOverviewResponse movieDetail = movie;
                Log.d("MovieAdapter", "Title: " + movie.getTitle());
                Log.d("MovieAdapter", "Image URL: " + movie.getImageUrl());

                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movieDetails", movieDetail);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showToast("Película "+movie.getTitle()+" agregada a favoritas");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movieImageView);
        }
    }

    /**
     * @param mensaje
     * Método para ir matando los Toast y mostrar todos en el mismo para evitar
     * colas de Toasts y que se ralentice el dispositivo*/
    public void showToast(String mensaje){
        // Comprobamos si existe algun toast cargado en el toast de la variable global
        if (mensajeToast != null) { // En caso de que si que exista
            mensajeToast.cancel(); // Le cancelamos, es decir le "matamos"
        }

        // Creamos un nuevo Toast con el mensaje que nos dan de argumento en el método
        mensajeToast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
        // Mostramos dicho Toast
        mensajeToast.show();
    }
}
package com.clase.engenios_manuelimdbapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clase.engenios_manuelimdbapp.R;
import com.clase.engenios_manuelimdbapp.models.FavoriteMoviesDatabase;
import com.clase.engenios_manuelimdbapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapters extends RecyclerView.Adapter<FavoriteAdapters.FavoriteViewHolder> {
    private Context context;
    private List<Movie> favoriteMoviesList; // Lista de películas favoritas
    private Toast mensajeToast = null; // Toast para mostrar mensajes

    public FavoriteAdapters(Context context, List<Movie> favoriteMoviesList) {
        this.context = context;
        this.favoriteMoviesList = favoriteMoviesList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Movie movie = favoriteMoviesList.get(position);

        Picasso.get()
                .load(movie.getImageUrl())
                .placeholder(R.drawable.baseline_autorenew_24)
                .error(R.drawable.por_defecto)
                .into(holder.movieImageView);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FavoriteMoviesDatabase database = new FavoriteMoviesDatabase(context);

                if (database.deleteFavorite(movie.getId())>0) {
                    showToast("Película eliminada de favoritos");
                    favoriteMoviesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, favoriteMoviesList.size());
                } else {
                    showToast("Error al eliminar la película de favoritos");
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteMoviesList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImageView;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movieImageView);
        }
    }

    /**
     * @param mensaje Mensaje a mostrar
     * Método para mostrar Toasts sin que se acumulen
     */
    public void showToast(String mensaje) {
        if (mensajeToast != null) {
            mensajeToast.cancel();
        }
        mensajeToast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
        mensajeToast.show();
    }
}

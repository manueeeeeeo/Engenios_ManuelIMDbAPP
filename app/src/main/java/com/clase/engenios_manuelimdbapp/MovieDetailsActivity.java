package com.clase.engenios_manuelimdbapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.clase.engenios_manuelimdbapp.api.IMDBApiService;
import com.clase.engenios_manuelimdbapp.models.MovieOverviewResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {
    private ImageView imagenPeli;
    private TextView titleView, valora, fecha, descrip;
    private IMDBApiService imdbApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imagenPeli = (ImageView) findViewById(R.id.imagenPeli);
        titleView = (TextView) findViewById(R.id.txtTituloPeli);
        fecha = (TextView) findViewById(R.id.txtFe);
        valora = (TextView) findViewById(R.id.txtRating);
        descrip = (TextView) findViewById(R.id.txtDescr);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("X-RapidAPI-Key", "45157d396bmshf14702227e85da3p1ff7a9jsna860971b474a")
                            .addHeader("X-RapidAPI-Host", "imdb-com.p.rapidapi.com")
                            .build();
                    return chain.proceed(newRequest);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://imdb-com.p.rapidapi.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        imdbApiService = retrofit.create(IMDBApiService.class);

        MovieOverviewResponse movie = getIntent().getParcelableExtra("movieDetails");
        if (movie != null) {
            loadMovieDetails(movie);
        }
    }

    private void loadMovieDetails(MovieOverviewResponse movie) {
        titleView.setText(movie.getTitle());
        fecha.setText("Release Date: "+movie.getYear());
        valora.setText("Rating: "+movie.getRanking());
        Picasso.get()
                .load(movie.getImageUrl())
                .placeholder(R.drawable.baseline_autorenew_24)
                .into(imagenPeli);
        Log.d("API_RESPONSE", "ID Movie: " + movie.getId());

        Call<MovieOverviewResponse> call = imdbApiService.getMovieOverview(movie.getId());
        call.enqueue(new Callback<MovieOverviewResponse>() {
            @Override
            public void onResponse(Call<MovieOverviewResponse> call, Response<MovieOverviewResponse> response) {
                Log.d("API_RESPONSE", "Código HTTP: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", "Respuesta completa: " + new Gson().toJson(response.body()));

                    String description = response.body().getDescription();
                    if (description != null && !description.isEmpty()) {
                        descrip.setText(description);
                    } else {
                        descrip.setText("Descripción no disponible.");
                    }
                } else {
                    Log.e("API_ERROR", "Respuesta vacía o incorrecta. Código: " + response.code());
                    descrip.setText("Descripción no disponible.");
                }
            }


            @Override
            public void onFailure(Call<MovieOverviewResponse> call, Throwable t) {
                descrip.setText("Error al cargar la descripción.");
            }
        });
    }
}
package com.clase.engenios_manuelimdbapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clase.engenios_manuelimdbapp.adapters.MovieAdapters;
import com.clase.engenios_manuelimdbapp.api.IMDBApiService;
import com.clase.engenios_manuelimdbapp.databinding.FragmentHomeBinding;
import com.clase.engenios_manuelimdbapp.models.Movie;
import com.clase.engenios_manuelimdbapp.models.MovieOverviewResponse;
import com.clase.engenios_manuelimdbapp.models.MovieResponse;
import com.clase.engenios_manuelimdbapp.models.PopularMovieResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private MovieAdapters adapter;
    private List<MovieOverviewResponse> movieList = new ArrayList<>();
    private IMDBApiService imdbApiService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.d("HomeFragment", "Se ha cargado el HomeFragment");

        // Configuración RecyclerView
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MovieAdapters(getContext(), movieList);
        recyclerView.setAdapter(adapter);

        // Configuración Retrofit y cliente OkHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("X-RapidAPI-Key", "45157d396bmshf14702227e85da3p1ff7a9jsna860971b474a")
                            .addHeader("X-RapidAPI-Host", "imdb-com.p.rapidapi.com")
                            .build();
                    return chain.proceed(newRequest);
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://imdb-com.p.rapidapi.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        imdbApiService = retrofit.create(IMDBApiService.class);
        loadTopRatedMoviesAndTvShows();

        return root;
    }

    private void loadTopRatedMoviesAndTvShows() {
        Call<PopularMovieResponse> call = imdbApiService.getTopMeterTitles("US");
        call.enqueue(new Callback<PopularMovieResponse>() {
            @Override
            public void onResponse(Call<PopularMovieResponse> call, Response<PopularMovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("HomeFragment", "JSON recibido: " + new Gson().toJson(response.body()));

                    List<PopularMovieResponse.Edge> edges = response.body().getData().getTopMeterTitles().getEdges();

                    if (edges != null && !edges.isEmpty()) {
                        movieList.clear();

                        int limit = Math.min(edges.size(), 10);

                        for (int i = 0; i < limit; i++) {
                            PopularMovieResponse.Edge edge = edges.get(i);
                            PopularMovieResponse.Node node = edge.getNode();

                            MovieOverviewResponse movie = new MovieOverviewResponse();
                            movie.setTitle(node.getTitleText().getText());
                            movie.setImageUrl(node.getPrimaryImage().getUrl());
                            movie.setRanking(String.valueOf(node.getMeterRanking().getCurrentRank()));
                            movie.setYear(node.getReleaseDate().toString());
                            movie.setId(node.getId());

                            movieList.add(movie);
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("HomeFragment", "No hay títulos disponibles");
                    }
                } else {
                    Log.e("HomeFragment", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PopularMovieResponse> call, Throwable t) {
                Log.e("HomeFragment", "Error en la llamada API", t);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

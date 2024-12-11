package com.clase.engenios_manuelimdbapp.api;

import com.clase.engenios_manuelimdbapp.models.Movie;
import com.clase.engenios_manuelimdbapp.models.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IMDBApiService {
    @Headers({
            "X-RapidAPI-Host: imdb8.p.rapidapi.com",
            "X-RapidAPI-Key: 45157d396bmshef14702227e85da3p1ff7a9jsna860971b474a"
    })
    @GET("title/get-top-meter")
    Call<List<String>> getTopRatedMovies(@Query("limit") int limit);

    @Headers({
            "X-RapidAPI-Host: imdb8.p.rapidapi.com",
            "X-RapidAPI-Key: 45157d396bmshef14702227e85da3p1ff7a9jsna860971b474a"
    })
    @GET("title/get-overview-details")
    Call<Movie> getMovieDetails(@Query("tconst") String movieId, @Query("currentCountry") String country);
}
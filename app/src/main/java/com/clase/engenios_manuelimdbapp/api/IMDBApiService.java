package com.clase.engenios_manuelimdbapp.api;

import com.clase.engenios_manuelimdbapp.models.MovieOverviewResponse;
import com.clase.engenios_manuelimdbapp.models.PopularMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Manuel
 * @version 1.0*/

public interface IMDBApiService {
    // Consulta a la API para obtener el top 10 de peliculas y series
    @GET("title/get-top-meter")
    Call<PopularMovieResponse> getTopMeterTitles(@Query("Country") String country);

    // Consulta a la API para obtener toda la información de una pelicula o serie
    @GET("title/get-overview")
    Call<MovieOverviewResponse> getMovieOverview(@Query("tconst") String movieId);
}

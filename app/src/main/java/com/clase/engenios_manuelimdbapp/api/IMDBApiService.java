package com.clase.engenios_manuelimdbapp.api;

import com.clase.engenios_manuelimdbapp.models.MovieOverviewResponse;
import com.clase.engenios_manuelimdbapp.models.PopularMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMDBApiService {
    @GET("title/get-top-meter")
    Call<PopularMovieResponse> getTopMeterTitles(@Query("Country") String country);

    @GET("title/get-overview")
    Call<MovieOverviewResponse> getMovieOverview(@Query("tconst") String movieId);
}

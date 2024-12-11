package com.clase.engenios_manuelimdbapp.models;

import java.util.List;

public class MovieResponse {
    private List<Movie> results; // Lista de películas o series

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
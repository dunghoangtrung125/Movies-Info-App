package com.trungdunghoang125.moviesinfoapp.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungdunghoang125.moviesinfoapp.model.MovieModel;

/**
 * Created by trungdunghoang125 on 12/09/2022
 */
// single movie request
public class MovieResponse {
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie() {
        return movie;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie response " + movie;
    }
}

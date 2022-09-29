package com.trungdunghoang125.moviesinfoapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungdunghoang125.moviesinfoapp.model.MovieTrailerModel;

import java.util.List;

/**
 * Created by trungdunghoang125 on 29/09/2022
 */
public class MovieTrailerResponse {
    @SerializedName("results")
    @Expose
    private List<MovieTrailerModel> trailerList;

    public List<MovieTrailerModel> getTrailerList() {
        return trailerList;
    }
}

package com.trungdunghoang125.moviesinfoapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungdunghoang125.moviesinfoapp.model.MovieModel;

import java.util.List;

/**
 * Created by trungdunghoang125 on 12/09/2022
 */

// multiple movie request
public class MovieSearchResponse {
    @SerializedName("total_results")
    private int mTotalCount;

    @SerializedName("results")
    private List<MovieModel> mMoviesList;

    public int getTotalCount() {
        return mTotalCount;
    }

    public List<MovieModel> getMovies() {
        return mMoviesList;
    }
}

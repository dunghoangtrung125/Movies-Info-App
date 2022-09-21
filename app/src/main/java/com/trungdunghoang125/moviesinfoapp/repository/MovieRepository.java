package com.trungdunghoang125.moviesinfoapp.repository;

import androidx.lifecycle.LiveData;

import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.request.MovieApiClient;

import java.util.List;

/**
 * Created by trungdunghoang125 on 13/09/2022
 */
public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    public void searchMoviesApi(String query, int pageNum) {
        movieApiClient.searchMoviesApi(query, pageNum);
    }
}

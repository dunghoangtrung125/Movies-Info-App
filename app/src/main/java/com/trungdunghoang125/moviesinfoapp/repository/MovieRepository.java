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
    private String currentQuery;
    private int currentPage;

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

    public LiveData<List<MovieModel>> getPopMovies() {
        return movieApiClient.getPopMovies();
    }

    public void searchMoviesApi(String query, int pageNum) {
        currentQuery = query;
        currentPage = pageNum;
        movieApiClient.searchMoviesApi(query, pageNum);
    }

    public void searchPopularMovies(int pageNum) {
        currentPage = pageNum;
        movieApiClient.searchPopularMovies(pageNum);
    }

    public void loadNextPage() {
        searchMoviesApi(currentQuery, currentPage + 1);
    }
}

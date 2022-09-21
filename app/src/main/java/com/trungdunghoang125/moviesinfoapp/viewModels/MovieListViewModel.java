package com.trungdunghoang125.moviesinfoapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.repository.MovieRepository;

import java.util.List;

/**
 * Created by trungdunghoang125 on 13/09/2022
 */
public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    // Constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public void searchMoviesApi(String query, int pageNum) {
        movieRepository.searchMoviesApi(query, pageNum);
    }
}

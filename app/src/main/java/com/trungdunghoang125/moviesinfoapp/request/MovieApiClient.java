package com.trungdunghoang125.moviesinfoapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.trungdunghoang125.moviesinfoapp.AppExecutors;
import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.response.MovieSearchResponse;
import com.trungdunghoang125.moviesinfoapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by trungdunghoang125 on 13/09/2022
 */
public class MovieApiClient {
    private static final String TAG = "MovieApiClient";
    private static MovieApiClient instance;
    private static RetrieveMovies retrieveMovies;
    private static RetrievePopularMovies retrievePopularMovies;
    private final MutableLiveData<List<MovieModel>> moviesList;
    private final MutableLiveData<List<MovieModel>> popMoviesList;

    // Constructor
    private MovieApiClient() {
        moviesList = new MutableLiveData<>();
        popMoviesList = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return moviesList;
    }

    public LiveData<List<MovieModel>> getPopMovies() {
        return popMoviesList;
    }

    public void searchMoviesApi(String query, int pageNum) {
        if (retrieveMovies != null) {
            retrieveMovies = null;
        }

        retrieveMovies = new RetrieveMovies(query, pageNum);
        final Future handler = AppExecutors.getInstance().getNetWorkIO().submit(retrieveMovies);

        AppExecutors.getInstance().getNetWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    public void searchPopularMovies(int pageNum) {
        if (retrievePopularMovies != null) {
            retrievePopularMovies = null;
        }

        retrievePopularMovies = new RetrievePopularMovies(pageNum);
        final Future handler = AppExecutors.getInstance().getNetWorkIO().submit(retrievePopularMovies);

        AppExecutors.getInstance().getNetWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMovies implements Runnable {
        private final String query;
        private final int pageNumber;
        private boolean cancelRequest;

        // constructor
        public RetrieveMovies(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(query, pageNumber).execute();

                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        // update value to live data from background thread
                        moviesList.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = moviesList.getValue();
                        currentMovies.addAll(list);
                        moviesList.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.d(TAG, "Error: " + error);
                    moviesList.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                moviesList.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            Log.d(TAG, "getMovies: " + query + " " + pageNumber);
            return RetrofitService.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void setCancelRequest() {
            cancelRequest = true;
        }
    }

    private class RetrievePopularMovies implements Runnable {
        private final int pageNumber;
        private boolean cancelRequest;

        // constructor
        public RetrievePopularMovies(int pageNumber) {
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(pageNumber).execute();

                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        // update value to live data from background thread
                        popMoviesList.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = popMoviesList.getValue();
                        currentMovies.addAll(list);
                        popMoviesList.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.d(TAG, "Error: " + error);
                    popMoviesList.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                popMoviesList.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getMovies(int pageNumber) {
            Log.d(TAG, "getPopMovies: " + pageNumber);
            return RetrofitService.getMovieApi().getPopularMovies(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void setCancelRequest() {
            cancelRequest = true;
        }
    }
}

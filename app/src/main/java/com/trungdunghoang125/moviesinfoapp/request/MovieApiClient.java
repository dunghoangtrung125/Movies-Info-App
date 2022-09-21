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
    private MutableLiveData<List<MovieModel>> mMovies;

    private static RetrieveMovies retrieveMovies;

    // Constructor
    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
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
        }, 10000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMovies implements Runnable {
        private String query;
        private int pageNumber;
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
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.d(TAG, "Error: " + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            Log.d(TAG, "getMovies: ");
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
}

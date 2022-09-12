package com.trungdunghoang125.moviesinfoapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.request.MovieApi;
import com.trungdunghoang125.moviesinfoapp.request.RetrofitService;
import com.trungdunghoang125.moviesinfoapp.utils.Credentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getButton = findViewById(R.id.request_btn);

        getButton.setOnClickListener(view -> {
            getRetrofitResponse();
        });
    }

    private void getRetrofitResponse() {
        MovieApi movieApi = RetrofitService.getMovieApi();

        Call<MovieModel> call = movieApi.getMovieById(
                343611,
                Credentials.API_KEY
        );

        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    MovieModel movie = response.body();
                    Log.d(TAG, "onResponse: " + movie.getTitle());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
}
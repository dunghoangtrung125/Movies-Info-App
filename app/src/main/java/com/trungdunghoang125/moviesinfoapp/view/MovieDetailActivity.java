package com.trungdunghoang125.moviesinfoapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.trungdunghoang125.moviesinfoapp.databinding.ActivityMovieDetailBinding;
import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.model.MovieTrailerModel;
import com.trungdunghoang125.moviesinfoapp.request.MovieApi;
import com.trungdunghoang125.moviesinfoapp.request.RetrofitService;
import com.trungdunghoang125.moviesinfoapp.response.MovieTrailerResponse;
import com.trungdunghoang125.moviesinfoapp.utils.Constant;
import com.trungdunghoang125.moviesinfoapp.utils.Credentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private ImageView moviePoster, movieTrailer;
    private RatingBar movieRating;
    private TextView movieTitle, movieOverview;
    private ActivityMovieDetailBinding binding;
    private int movieId;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        moviePoster = binding.moviePosterDetail;
        movieRating = binding.movieRatingDetail;
        movieTitle = binding.movieTitleDetail;
        movieOverview = binding.movieOverviewDetail;
        movieTrailer = binding.movieTrailer;

        getData();

        movieTrailer.setOnClickListener(view -> {
            viewTrailer();
        });
    }

    private void getData() {
        if (getIntent().hasExtra(Constant.INTENT_KEY)) {
            MovieModel movieModel = getIntent().getParcelableExtra(Constant.INTENT_KEY);
            movieTitle.setText(movieModel.getTitle());
            movieRating.setRating(movieModel.getVoteAverage() / 2);
            movieOverview.setText(movieModel.getMovieOverview());
            Glide.with(this).load(Constant.BASE_IMG_URL + movieModel.getPosterPath()).into(moviePoster);
            movieId = movieModel.getMovieId();
        }
    }

    private void viewTrailer() {
        MovieApi movieApi = RetrofitService.getMovieApi();

        Call<MovieTrailerResponse> call = movieApi.getTrailerLink(movieId, Credentials.API_KEY);

        call.enqueue(new Callback<MovieTrailerResponse>() {
            @Override
            public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getTrailerList().size() != 0) {
                        MovieTrailerModel trailerModel = response.body().getTrailerList().get(0);
                        key = trailerModel.getKey();
                        Log.d("TAG", "onResponse: " + key);
                        String youtubeUrl = YOUTUBE_BASE_URL + key;
                        Log.d("TAG", "viewTrailer: " + youtubeUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Trailer unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {

            }
        });
    }
}
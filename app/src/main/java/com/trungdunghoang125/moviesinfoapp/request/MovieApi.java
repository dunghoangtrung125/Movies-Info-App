package com.trungdunghoang125.moviesinfoapp.request;

import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.response.MovieSearchResponse;
import com.trungdunghoang125.moviesinfoapp.response.MovieTrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by trungdunghoang125 on 12/09/2022
 */
public interface MovieApi {
    // Search movie
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // Search by ID
    @GET("/3/movie/{movie_id}")
    Call<MovieModel> getMovieById(
            @Path("movie_id") int movieId,
            @Query("api_key") String key
    );

    // Get popular movie
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );

    // Get movie trailer
    @GET("/3/movie/{movie_id}/videos")
    Call<MovieTrailerResponse> getTrailerLink(
            @Path("movie_id") int movieId,
            @Query("api_key") String key
    );
}

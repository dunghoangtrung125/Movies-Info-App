package com.trungdunghoang125.moviesinfoapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.trungdunghoang125.moviesinfoapp.R;
import com.trungdunghoang125.moviesinfoapp.adapter.ItemClick;
import com.trungdunghoang125.moviesinfoapp.adapter.MovieListAdapter;
import com.trungdunghoang125.moviesinfoapp.databinding.ActivityMovieListBinding;
import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.utils.Constant;
import com.trungdunghoang125.moviesinfoapp.viewModels.MovieListViewModel;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements ItemClick {
    private MovieListViewModel viewModel;
    private RecyclerView rcMovieList;
    private MovieListAdapter adapter;
    private SearchView searchView;
    private ActivityMovieListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView = binding.searchView;
        rcMovieList = binding.rcMovieList;

        // instance of viewModel
        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        // set support for action bar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);

        // Observe any data change
        configRecyclerViewMovie();
        observerDataChange();
        observerPopMovies();
        searchPopularMovies();
        setUpSearchView();
    }

    private void searchPopularMovies() {
        viewModel.searchPopularMovies(1);
    }

    private void configRecyclerViewMovie() {
        adapter = new MovieListAdapter(this);
        rcMovieList.setAdapter(adapter);
    }

    private void observerDataChange() {
        viewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    for (MovieModel movie : movieModels) {
                        Log.d(Constant.TAG, "onChanged: " + movie.getTitle());
                    }
                    adapter.setMovieList(movieModels);
                }
            }
        });
    }

    private void observerPopMovies() {
        viewModel.getPopMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    for (MovieModel movie : movieModels) {
                        Log.d(Constant.TAG, "onChanged: " + movie.getTitle());
                    }
                    adapter.setMovieList(movieModels);
                }
            }
        });
    }

    private void setUpSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchMoviesApi(query, 1);
                // set the recycler view inflate search movie layout
                Constant.IS_POPULAR = false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        // Send movie intent to MovieDetail
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constant.INTENT_KEY, adapter.getSelectedMovie(pos));
        startActivity(intent);
    }
}
package com.trungdunghoang125.moviesinfoapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.trungdunghoang125.moviesinfoapp.adapter.ItemClick;
import com.trungdunghoang125.moviesinfoapp.adapter.MovieListAdapter;
import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.viewModels.MovieListViewModel;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements ItemClick {
    private static final String TAG = "MovieListActivityTag";
    private MovieListViewModel viewModel;
    private RecyclerView rcMovieList;
    private MovieListAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.search_view);
        rcMovieList = findViewById(R.id.rc_movie_list);

        // instance of viewModel
        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        // set support for action bar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);

        // Observe any data change
        configRecyclerViewMovie();
        observerDataChange();
        setUpSearchView();
    }

    private void configRecyclerViewMovie() {
        adapter = new MovieListAdapter(this);
        rcMovieList.setAdapter(adapter);
        rcMovieList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observerDataChange() {
        viewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    for (MovieModel movie : movieModels) {
                        Log.d(TAG, "onChanged: " + movie.getTitle());
                        adapter.setMovieList(movieModels);
                    }
                }
            }
        });
    }

    private void setUpSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchMoviesApi(query, 1);
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
        Toast.makeText(this, "Item at " + pos + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(String category) {

    }
}
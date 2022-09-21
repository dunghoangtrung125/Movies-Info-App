package com.trungdunghoang125.moviesinfoapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcMovieList = findViewById(R.id.rc_movie_list);
        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        // Observe any data change
        configRecyclerViewMovie();
        observerDataChange();
        searchMoviesApi("Harry potter", 1);
    }

    private void configRecyclerViewMovie() {
        adapter = new MovieListAdapter(this);
        rcMovieList.setAdapter(adapter);
        rcMovieList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchMoviesApi(String query, int pageNum) {
        viewModel.searchMoviesApi(query, pageNum);
    }

    private void observerDataChange() {
        viewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    for (MovieModel movie : movieModels) {
                        Log.d(TAG, "onChanged: " + movie.getRuntime());
                        adapter.setMovieList(movieModels);
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(int pos) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
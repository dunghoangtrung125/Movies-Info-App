package com.trungdunghoang125.moviesinfoapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trungdunghoang125.moviesinfoapp.R;
import com.trungdunghoang125.moviesinfoapp.model.MovieModel;
import com.trungdunghoang125.moviesinfoapp.utils.Constant;

import java.util.List;

/**
 * Created by trungdunghoang125 on 19/09/2022
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "testMultiviewType";

    private static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500";
    private ItemClick itemClick;
    private List<MovieModel> movieList;
    private static final int POP_MOVIES_LIST = 1;
    private static final int SEARCH_MOVIES_LIST = 2;

    public MovieListAdapter(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == POP_MOVIES_LIST) {
            Log.d(TAG, "onCreateViewHolder: " + viewType);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_pop_item, parent, false);
            return new PopMovieViewHolder(view);
        } else {
            Log.d(TAG, "onCreateViewHolder: " + viewType);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_item, parent, false);
            return new MovieSearchViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        Log.d(TAG, "onBindViewHolder: " + type);
        MovieModel movieModel = movieList.get(position);
        if (type == POP_MOVIES_LIST) {
            ((PopMovieViewHolder) holder).bind(movieModel, ((PopMovieViewHolder) holder));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClick(holder.getAdapterPosition());
                }
            });
        } else {
            ((MovieSearchViewHolder) holder).bind(movieModel, (MovieSearchViewHolder) holder);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (Constant.IS_POPULAR) {
            Log.d(TAG, "getItemViewType: " + "popular");
            return POP_MOVIES_LIST;
        } else {
            Log.d(TAG, "getItemViewType: " + "search");
            return SEARCH_MOVIES_LIST;
        }
    }

    public void setMovieList(List<MovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int pos) {
        if (movieList.size() > 0) {
            return movieList.get(pos);
        }
        return null;
    }

    static class MovieSearchViewHolder extends RecyclerView.ViewHolder {
        private TextView title, release_date;
        private ImageView moviePoster;
        private RatingBar rating;

        public MovieSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            release_date = itemView.findViewById(R.id.release_date);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            rating = itemView.findViewById(R.id.rating);
        }

        public void bind(MovieModel movieModel, MovieSearchViewHolder holder) {
            title.setText(movieModel.getTitle());
            release_date.setText(movieModel.getReleaseDate());
            rating.setRating(movieModel.getVoteAverage() / 2);
            // Glide to load image
            String url = movieModel.getPosterPath();
            Glide.with(holder.itemView.getContext())
                    .load(BASE_IMG_URL + url)
                    .into(moviePoster);
        }
    }

    static class PopMovieViewHolder extends RecyclerView.ViewHolder {
        private TextView popMovieTitle;
        private ImageView popMoviePoster;

        public PopMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            popMovieTitle = itemView.findViewById(R.id.pop_movie_title);
            popMoviePoster = itemView.findViewById(R.id.pop_movie_img);
        }

        public void bind(MovieModel movieModel, PopMovieViewHolder holder) {
            popMovieTitle.setText(movieModel.getTitle());
            Glide.with(holder.itemView.getContext())
                    .load(BASE_IMG_URL + movieModel.getPosterPath())
                    .into(popMoviePoster);
        }
    }
}

package com.trungdunghoang125.moviesinfoapp.adapter;

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

import java.util.List;


/**
 * Created by trungdunghoang125 on 19/09/2022
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500";
    private ItemClick itemClick;
    private List<MovieModel> movieList;

    public void setMovieList(List<MovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public MovieListAdapter(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movieModel = movieList.get(position);
        holder.bind(movieModel, holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, release_date, duration;
        private ImageView moviePoster;
        private RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            release_date = itemView.findViewById(R.id.release_date);
            duration = itemView.findViewById(R.id.duration);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            rating = itemView.findViewById(R.id.rating);
        }

        public void bind(MovieModel movieModel, ViewHolder holder) {
            title.setText(movieModel.getTitle());
            release_date.setText(movieModel.getReleaseDate());
            //duration.setText(movieModel.getRuntime());
            rating.setRating(movieModel.getVoteAverage() / 2);
            // Glide to load image
            String url = movieModel.getPosterPath();
            Glide.with(holder.itemView.getContext())
                    .load(BASE_IMG_URL + url)
                    .into(moviePoster);
        }
    }
}

package com.trungdunghoang125.moviesinfoapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by trungdunghoang125 on 12/09/2022
 */
public class MovieModel implements Parcelable {
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private int movieId;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("overview")
    private String movieOverview;

    public MovieModel(String title, String posterPath, String releaseDate, int movieId, float voteAverage, String movieOverview) {
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.voteAverage = voteAverage;
        this.movieOverview = movieOverview;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        movieId = in.readInt();
        voteAverage = in.readFloat();
        movieOverview = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    // getter
    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(releaseDate);
        parcel.writeInt(movieId);
        parcel.writeFloat(voteAverage);
        parcel.writeString(movieOverview);
    }
}

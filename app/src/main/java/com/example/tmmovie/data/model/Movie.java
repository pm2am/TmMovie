package com.example.tmmovie.data.model;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


public class Movie {

    @PrimaryKey
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("overview")
    public String overview;
    @SerializedName("vote_average")
    public String voteAverage;

}

package com.example.tmmovie.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tmmovie.data.model.Movie;

@Database(
        entities = {Movie.class},
        version = 1
)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}

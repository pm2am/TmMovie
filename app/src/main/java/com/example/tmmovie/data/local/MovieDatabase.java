package com.example.tmmovie.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tmmovie.data.model.BookmarkMovie;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;

@Database(
        entities = {TrendingMovie.class, NowPlayingMovie.class, BookmarkMovie.class},
        version = 1
)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}

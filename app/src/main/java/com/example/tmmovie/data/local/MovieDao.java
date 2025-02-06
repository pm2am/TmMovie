package com.example.tmmovie.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tmmovie.data.model.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovies(List<Movie> movies);

    @Query("SELECT * FROM movies")
    Single<List<Movie>> getTrendingMovie();

}

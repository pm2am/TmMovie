package com.example.tmmovie.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.TrendingMovie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTrendingMovies(List<TrendingMovie> movies);

    @Query("SELECT * FROM trending_movies")
    Single<List<TrendingMovie>> getTrendingMovies();

}

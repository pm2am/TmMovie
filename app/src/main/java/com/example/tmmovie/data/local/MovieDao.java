package com.example.tmmovie.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tmmovie.data.model.BookmarkMovie;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.SharedMovie;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertNowPlayingMovies(List<NowPlayingMovie> movies);

    @Query("SELECT * FROM now_playing_movies")
    Single<List<NowPlayingMovie>> getNowPlayingMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertBookmarkMovie(BookmarkMovie movie);

    @Query("SELECT * FROM bookmark_movies")
    Single<List<BookmarkMovie>> getBookmarkedMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSharedMovie(SharedMovie movie);

    @Query("SELECT * FROM shared_movies WHERE id=:id")
    Single<List<SharedMovie>> getSharedMovies(int id);
}

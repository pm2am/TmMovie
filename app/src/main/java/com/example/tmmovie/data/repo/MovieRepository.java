package com.example.tmmovie.data.repo;

import android.util.Log;

import com.example.tmmovie.BuildConfig;
import com.example.tmmovie.data.local.MovieDao;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.MovieResponse;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;
import com.example.tmmovie.data.remote.MovieService;
import com.example.tmmovie.util.MovieMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class MovieRepository {

    private final MovieService service;
    private final MovieDao dao;

    @Inject
    public MovieRepository(MovieService service, MovieDao dao) {
        this.service = service;
        this.dao = dao;
    }

    public Single<List<TrendingMovie>> getTrendingMovies() {
        return dao.getTrendingMovies()
                .flatMap(movies -> {
                    if (movies!=null && !movies.isEmpty()) {
                        return Single.just(movies);
                    } else {
                        return service.getTrendingMovies(BuildConfig.API_KEY)
                                .map(MovieResponse::getResults)
                                .map(MovieMapper::mapToTrending)
                                .flatMap(moviesList -> dao.insertTrendingMovies(moviesList).andThen(Single.just(moviesList))
                                );
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<NowPlayingMovie>> getNowPlayingMovies() {
        return dao.getNowPlayingMovies()
                .flatMap(movies -> {
                    if (movies!=null && !movies.isEmpty()) {
                        return Single.just(movies);
                    } else {
                        return service.getNowPlayingMovies(BuildConfig.API_KEY,0)
                                .map(MovieResponse::getResults)
                                .map(MovieMapper::mapToNowPlaying)
                                .flatMap(movies1 -> dao.insertNowPlayingMovies(movies1).andThen(Single.just(movies1))
                                );
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}

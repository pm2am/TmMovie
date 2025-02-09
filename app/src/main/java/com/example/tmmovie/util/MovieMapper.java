package com.example.tmmovie.util;

import com.example.tmmovie.data.model.BookmarkMovie;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {
    public static List<NowPlayingMovie> mapToNowPlaying(List<Movie> movies) {
        List<NowPlayingMovie> nowPlayingMovies = new ArrayList<>();
        for (Movie movie : movies) {
            NowPlayingMovie nowPlaying = new NowPlayingMovie();
            nowPlaying.id = movie.id;
            nowPlaying.title = movie.title;
            nowPlaying.posterPath = movie.posterPath;
            nowPlaying.overview = movie.overview;
            nowPlaying.voteAverage = movie.voteAverage;
            nowPlayingMovies.add(nowPlaying);
        }
        return nowPlayingMovies;
    }

    public static List<TrendingMovie> mapToTrending(List<Movie> movies) {
        List<TrendingMovie> trendingMovies = new ArrayList<>();
        for (Movie movie : movies) {
            TrendingMovie trending = new TrendingMovie();
            trending.id = movie.id;
            trending.title = movie.title;
            trending.posterPath = movie.posterPath;
            trending.overview = movie.overview;
            trending.voteAverage = movie.voteAverage;
            trendingMovies.add(trending);
        }
        return trendingMovies;
    }

    public static BookmarkMovie mapToBookmarkMovie(Movie movie) {
        BookmarkMovie bookmarkMovie = new BookmarkMovie();
        bookmarkMovie.id = movie.id;
        bookmarkMovie.title = movie.title;
        bookmarkMovie.posterPath = movie.posterPath;
        bookmarkMovie.overview = movie.overview;
        bookmarkMovie.voteAverage = movie.voteAverage;
        return bookmarkMovie;
    }

}

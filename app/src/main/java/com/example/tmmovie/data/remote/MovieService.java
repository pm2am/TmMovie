package com.example.tmmovie.data.remote;

import com.example.tmmovie.data.model.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("trending/movie/day")
    Single<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey);

}

package com.example.tmmovie.screens;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;
import com.example.tmmovie.data.repo.MovieRepository;
import com.example.tmmovie.util.MovieMapper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MovieRepository repository;

    private final CompositeDisposable disposable;

    private final MutableLiveData<List<TrendingMovie>> _trendingMovieLiveData = new MutableLiveData<>();
    public LiveData<List<TrendingMovie>> trendingLiveData = _trendingMovieLiveData;

    private final MutableLiveData<List<NowPlayingMovie>> _nowPlayingMovieLiveData = new MutableLiveData<>();
    public LiveData<List<NowPlayingMovie>> nowPlayingMovieLiveData = _nowPlayingMovieLiveData;

    public Movie selectedMovie;

    @Inject
    public MainViewModel(MovieRepository repository) {
        this.repository = repository;
        this.disposable = new CompositeDisposable();
    }

    public void loadData() {
        disposable.addAll(
                repository.getTrendingMovies()
                        .subscribe(
                                _trendingMovieLiveData::setValue,
                                throwable -> {
                                    Log.i(TAG, "onError"+throwable);
                                }
                        ),
                repository.getNowPlayingMovies().subscribe(
                        _nowPlayingMovieLiveData::setValue,
                        throwable -> {
                            Log.i(TAG, "onError " + throwable);
                        }
                )

        );
    }

    public void onAddBookmarkButtonClicked() {
        disposable.add(
                repository.insertBookmarkMovie(MovieMapper.mapToBookmarkMovie(selectedMovie))
                        .subscribe()
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

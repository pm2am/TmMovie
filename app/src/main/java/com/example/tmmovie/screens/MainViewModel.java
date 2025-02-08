package com.example.tmmovie.screens;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tmmovie.data.model.TrendingMovie;
import com.example.tmmovie.data.repo.MovieRepository;

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

    @Inject
    public MainViewModel(MovieRepository repository) {
        this.repository = repository;
        this.disposable = new CompositeDisposable();
    }

    public void loadData() {
        disposable.add(
                repository.getTrendingMovies()
                        .subscribe(
                                _trendingMovieLiveData::setValue,
                                throwable -> {
                                    Log.i(TAG, "onError"+throwable);
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

package com.example.tmmovie.screens;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tmmovie.data.model.BookmarkMovie;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;
import com.example.tmmovie.data.repo.MovieRepository;
import com.example.tmmovie.util.MovieMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MovieRepository repository;

    private final CompositeDisposable compositeDisposable;
    private final PublishSubject<String> subject;


    private final MutableLiveData<List<TrendingMovie>> _trendingMovieLiveData = new MutableLiveData<>();
    public LiveData<List<TrendingMovie>> trendingLiveData = _trendingMovieLiveData;

    private final MutableLiveData<List<NowPlayingMovie>> _nowPlayingMovieLiveData = new MutableLiveData<>();
    public LiveData<List<NowPlayingMovie>> nowPlayingMovieLiveData = _nowPlayingMovieLiveData;

    private final MutableLiveData<List<BookmarkMovie>> _bookmarkMovieLiveData = new MutableLiveData<>();
    public LiveData<List<BookmarkMovie>> bookmarkMovieLiveData = _bookmarkMovieLiveData;

    private final MutableLiveData<List<Movie>> _searchedMovieLiveData = new MutableLiveData<>();
    public LiveData<List<Movie>> searchedMovieLiveData = _searchedMovieLiveData;

    public Movie selectedMovie;

    @Inject
    public MainViewModel(MovieRepository repository) {
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
        subject = PublishSubject.create();
        Disposable disposable = subject
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .switchMapSingle(repository::getSearchedMovies)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getResults()!=null) {
                                _searchedMovieLiveData.postValue(response.getResults());
                            } else {
                                _searchedMovieLiveData.postValue(new ArrayList<>());
                            }
                        },
                        throwable -> {
                            Log.e("API_ERROR", "Error: " + throwable.getMessage());
                        });
        compositeDisposable.add(disposable);

    }

    public void loadData() {
        compositeDisposable.addAll(
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
        compositeDisposable.add(
                repository.insertBookmarkMovie(MovieMapper.mapToBookmarkMovie(selectedMovie))
                        .subscribe()
        );
    }

    public void loadBookmarkData() {
        compositeDisposable.add(
                repository.getBookmarkedMovies().subscribe(
                        _bookmarkMovieLiveData::setValue,
                        throwable -> {
                            Log.i(TAG, "onError " + throwable);
                        }
                )
        );
    }

    @SuppressLint("CheckResult")
    public void loadSearch(String query) {
        subject.onNext(query);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}

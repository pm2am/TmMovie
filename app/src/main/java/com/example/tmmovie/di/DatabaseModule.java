package com.example.tmmovie.di;

import android.app.Application;

import androidx.room.Room;

import com.example.tmmovie.data.local.MovieDao;
import com.example.tmmovie.data.local.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public MovieDatabase getDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                MovieDatabase.class,
                "movies.db"
        ).build();
    }

    @Provides
    @Singleton
    public MovieDao getMovieDao(MovieDatabase db) {
        return db.getMovieDao();
    }

}

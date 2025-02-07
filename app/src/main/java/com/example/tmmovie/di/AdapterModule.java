package com.example.tmmovie.di;

import com.example.tmmovie.screens.main.TrendingViewAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class AdapterModule {
    @Provides
    public TrendingViewAdapter provideTrendingViewAdapter() {
        return new TrendingViewAdapter();
    }
}

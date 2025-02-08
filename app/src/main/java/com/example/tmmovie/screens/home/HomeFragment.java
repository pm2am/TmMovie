package com.example.tmmovie.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;
import com.example.tmmovie.databinding.FragmentHomeBinding;
import com.example.tmmovie.screens.MainViewModel;
import com.example.tmmovie.screens.common.MovieViewAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.loadData();
        MovieViewAdapter<TrendingMovie> trendingViewAdapter = new MovieViewAdapter<>();
        binding.trendingRecyclerView.setAdapter(trendingViewAdapter);
        MovieViewAdapter<NowPlayingMovie> nowPlayingViewAdapter = new MovieViewAdapter<>();
        binding.nowPlayingRecyclerView.setAdapter(nowPlayingViewAdapter);

        viewModel.trendingLiveData.observe(getViewLifecycleOwner(), trendingMovies -> {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            trendingViewAdapter.setMovieList(trendingMovies);
            binding.trendingRecyclerView.setVisibility(View.VISIBLE);
        });
        viewModel.nowPlayingMovieLiveData.observe(getViewLifecycleOwner(), nowPlayingMovies -> {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            nowPlayingViewAdapter.setMovieList(nowPlayingMovies);
            binding.nowPlayingRecyclerView.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        viewModel = null;
    }
}

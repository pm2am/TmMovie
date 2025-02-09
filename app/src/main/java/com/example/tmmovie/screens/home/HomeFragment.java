package com.example.tmmovie.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmmovie.R;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;
import com.example.tmmovie.databinding.FragmentHomeBinding;
import com.example.tmmovie.screens.MainViewModel;
import com.example.tmmovie.screens.MovieViewAdapter;
import com.example.tmmovie.screens.OnItemClickListener;
import com.example.tmmovie.screens.bookmark.BookmarkFragment;
import com.example.tmmovie.screens.detail.DetailFragment;

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
        trendingViewAdapter.setOnItemClickListener(itemClickListener);
        binding.trendingRecyclerView.setAdapter(trendingViewAdapter);
        MovieViewAdapter<NowPlayingMovie> nowPlayingViewAdapter = new MovieViewAdapter<>();
        nowPlayingViewAdapter.setOnItemClickListener(itemClickListener);
        binding.nowPlayingRecyclerView.setAdapter(nowPlayingViewAdapter);
        binding.bookMarkButton.setOnClickListener(view1 -> {
            navigateToFragment(new BookmarkFragment());
        });

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

    private final OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Movie movie) {
            viewModel.selectedMovie = movie;
            navigateToFragment(new DetailFragment());
        }
    };

    private void navigateToFragment(Fragment destinationFragment) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment currentFragment = manager.findFragmentById(R.id.fragmentContainer);
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.add(R.id.fragmentContainer, destinationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        viewModel = null;
    }
}

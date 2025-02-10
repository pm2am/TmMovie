package com.example.tmmovie.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.data.model.NowPlayingMovie;
import com.example.tmmovie.data.model.TrendingMovie;
import com.example.tmmovie.databinding.FragmentHomeBinding;
import com.example.tmmovie.screens.MainViewModel;
import com.example.tmmovie.screens.MovieViewAdapter;
import com.example.tmmovie.screens.OnItemClickListener;
import com.example.tmmovie.screens.bookmark.BookmarkFragment;
import com.example.tmmovie.screens.detail.DetailFragment;
import com.example.tmmovie.util.NavigationUtils;

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
        SearchViewAdapter searchViewAdapter = new SearchViewAdapter();
        searchViewAdapter.setOnItemClickListener(itemClickListener);
        binding.searchRecyclerView.setAdapter(searchViewAdapter);

        binding.searchView.setOnQueryTextFocusChangeListener((view1, b) -> {
            if (b) {
                binding.mainContainer.setVisibility(View.INVISIBLE);
                binding.searchRecyclerView.setVisibility(View.VISIBLE);
            } else {
                binding.mainContainer.setVisibility(View.VISIBLE);
                binding.searchRecyclerView.setVisibility(View.INVISIBLE);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                viewModel.loadSearch(s);
                return true;
            }
        });

        viewModel.searchedMovieLiveData.observe(getViewLifecycleOwner(), searchViewAdapter::setMovies);

        binding.bookMarkButton.setOnClickListener(view1 -> NavigationUtils.navigateToFragment(getParentFragmentManager(), new BookmarkFragment()));

        viewModel.trendingLiveData.observe(getViewLifecycleOwner(), trendingMovies -> {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            trendingViewAdapter.setMovieList(trendingMovies);
            binding.trendingContainer.setVisibility(View.VISIBLE);
        });
        viewModel.nowPlayingMovieLiveData.observe(getViewLifecycleOwner(), nowPlayingMovies -> {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            nowPlayingViewAdapter.setMovieList(nowPlayingMovies);
            binding.nowPlayingContainer.setVisibility(View.VISIBLE);
        });

    }

    private final OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Movie movie) {
            viewModel.onMovieItemClick(movie);
            NavigationUtils.navigateToFragment(getParentFragmentManager(), new DetailFragment());
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        viewModel = null;
    }
}

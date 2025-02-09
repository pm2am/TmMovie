package com.example.tmmovie.screens.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmmovie.data.model.BookmarkMovie;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.databinding.FragmentBookmarkBinding;
import com.example.tmmovie.screens.MainViewModel;
import com.example.tmmovie.screens.MovieViewAdapter;
import com.example.tmmovie.screens.OnItemClickListener;
import com.example.tmmovie.screens.detail.DetailFragment;
import com.example.tmmovie.util.NavigationUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookmarkFragment extends Fragment {
    FragmentBookmarkBinding binding;
    MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.loadBookmarkData();
        MovieViewAdapter<BookmarkMovie> adapter = new MovieViewAdapter<>();
        adapter.setOnItemClickListener(itemClickListener);
        binding.bookMarkRecyclerView.setAdapter(adapter);
        viewModel.bookmarkMovieLiveData.observe(getViewLifecycleOwner(), adapter::setMovieList);
    }

    private final OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Movie movie) {
            viewModel.selectedMovie = movie;
            NavigationUtils.navigateToFragment(getParentFragmentManager(), new DetailFragment());
        }
    };

}

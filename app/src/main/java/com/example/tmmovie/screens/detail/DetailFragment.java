package com.example.tmmovie.screens.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.databinding.FragmentDetailBinding;
import com.example.tmmovie.screens.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailFragment extends Fragment {

    FragmentDetailBinding binding;
    MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.selectedMovie.observe(getViewLifecycleOwner(), this::loadDetails);
        if (viewModel.selectedMovie.getValue()!=null) {
            loadDetails(viewModel.selectedMovie.getValue());
        }
        binding.addBookmarkButton.setOnClickListener(view1 -> {
            viewModel.onAddBookmarkButtonClicked();
        });
        binding.shareButton.setOnClickListener(view1 -> {
            viewModel.onShareButtonClicked();
            String url = "https://www.tmmovie.app/details?movieId=" + viewModel.selectedMovie.getValue().id;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, url);
            intent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(
                    intent, "Share URL"
            );
            startActivity(shareIntent);
        });
    }

    private void loadDetails(Movie movie) {
        binding.movieTile.setText(movie.title);
        Glide.with(binding.getRoot()
                        .getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(binding.moviePoster);
        binding.movieTile.setText(movie.title);
        binding.movieOverView.setText(movie.overview);
    }
}

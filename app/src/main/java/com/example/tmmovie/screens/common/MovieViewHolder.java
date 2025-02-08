package com.example.tmmovie.screens.common;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.databinding.MovieItemViewBinding;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private final MovieItemViewBinding binding;

    public MovieViewHolder(@NonNull MovieItemViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Movie movie, OnItemClickListener listener) {
        Glide.with(binding.getRoot()
                        .getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(binding.moviePoster);
        binding.movieTile.setText(movie.title);
        binding.movieOverView.setText(movie.overview);
        binding.itemContainer.setOnClickListener(view -> {
           listener.onItemClick(movie);
        });
    }
}

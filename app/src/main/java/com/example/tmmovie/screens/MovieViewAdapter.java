package com.example.tmmovie.screens;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.databinding.MovieItemViewBinding;

import java.util.ArrayList;
import java.util.List;

public class MovieViewAdapter<T extends Movie> extends RecyclerView.Adapter<MovieViewHolder> {
    private final List<T> movieList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemViewBinding binding = MovieItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movieList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMovieList(List<T> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

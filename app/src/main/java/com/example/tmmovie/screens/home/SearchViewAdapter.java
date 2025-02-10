package com.example.tmmovie.screens.home;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.databinding.SearchItemViewBinding;
import com.example.tmmovie.screens.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemViewBinding binding = SearchItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(movieList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovies(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    static class SearchViewHolder extends RecyclerView.ViewHolder {

        private final SearchItemViewBinding binding;
        public SearchViewHolder(@NonNull SearchItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie, OnItemClickListener listener) {
            binding.searchItemContainer.setOnClickListener(view -> {
                binding.searchItemContainer.postDelayed(() -> {
                    listener.onItemClick(movie);
                }, 300);
            });
            binding.searchedTitle.setText(movie.title);
        }
    }
}

package com.example.tmmovie.screens;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmmovie.R;
import com.example.tmmovie.data.model.Movie;
import com.example.tmmovie.databinding.ActivityMainBinding;
import com.example.tmmovie.screens.detail.DetailFragment;
import com.example.tmmovie.screens.home.HomeFragment;
import com.example.tmmovie.util.NavigationUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragmentContainer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (getIntent().getData() != null) {
            handleDeepLink();
        } else if (savedInstanceState == null) {
            NavigationUtils.setStartingFragment(getSupportFragmentManager(), new HomeFragment());
        }
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink();
    }

    private void handleDeepLink() {
        if (getIntent().getData() != null) {
            Uri uri = getIntent().getData();
            String id = uri.getQueryParameter("movieId");
            if (id != null) {
                int movieId = Integer.parseInt(id);
                viewModel.loadSharedDataWithId(movieId);
            }
            Movie movie = viewModel.selectedMovie.getValue();
            if ("/details".equals(uri.getPath())) {
                NavigationUtils.setStartingFragment(getSupportFragmentManager(), new DetailFragment());
            }
        }
    }
}
package com.example.tmmovie.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tmmovie.R;

public class NavigationUtils {
    public static void navigateToFragment(FragmentManager manager, Fragment destinationFragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment currentFragment = manager.findFragmentById(R.id.fragmentContainer);
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.add(R.id.fragmentContainer, destinationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

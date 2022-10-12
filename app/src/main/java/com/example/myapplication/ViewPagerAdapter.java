package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.Fragment.SearchFragment;
import com.example.myapplication.Fragment.TripFragment;
import com.example.myapplication.Fragment.UploadFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TripFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new UploadFragment();
            default:
                return new TripFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

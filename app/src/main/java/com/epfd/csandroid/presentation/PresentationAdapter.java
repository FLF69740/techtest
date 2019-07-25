package com.epfd.csandroid.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PresentationAdapter extends FragmentPagerAdapter {

    private List<ArrayList<String>> mFullListing;

    public PresentationAdapter(@NonNull FragmentManager fm, int behavior, List<ArrayList<String >> fullListing) {
        super(fm, behavior);
        mFullListing = fullListing;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PresentationPageOneFragment.newInstance(mFullListing.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFullListing.get(position).get(0).toUpperCase();
    }

    @Override
    public int getCount() {
        return mFullListing.size();
    }
}

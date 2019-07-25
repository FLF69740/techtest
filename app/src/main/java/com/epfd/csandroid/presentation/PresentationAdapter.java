package com.epfd.csandroid.presentation;

import androidx.annotation.NonNull;
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
        /*
        switch (position){
            case 0: return PresentationPageOneFragment.newInstance();
            case 1: return PresentationPageTwoFragment.newInstance();
            case 2: return PresentationPageThreeFragment.newInstance();
            case 3: return PresentationPageFourFragment.newInstance();
            case 4: return PresentationPageFiveFragment.newInstance();
            case 5: return PresentationPageSixFragment.newInstance();
            case 6: return PresentationPageSevenFragment.newInstance();
            case 7: return PresentationPageHeightFragment.newInstance();
            case 8: return PresentationPageNineFragment.newInstance();
            default: return PresentationPageTenFragment.newInstance();
        }
        */
    }

    @Override
    public int getCount() {
        return mFullListing.size();
    }
}

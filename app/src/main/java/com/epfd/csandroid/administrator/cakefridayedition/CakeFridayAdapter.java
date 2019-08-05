package com.epfd.csandroid.administrator.cakefridayedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class CakeFridayAdapter extends FragmentPagerAdapter {

    private List<CakeClassroom> mCakePlanning;

    public CakeFridayAdapter(@NonNull FragmentManager fm, int behavior, List<CakeClassroom> cakeClassrooms) {
        super(fm, behavior);
        mCakePlanning = cakeClassrooms;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return CakeClassroomFragment.newInstance(mCakePlanning.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCakePlanning.get(position).getClassroomCake();
    }

    @Override
    public int getCount() {
        return mCakePlanning.size();
    }
}

package com.epfd.csandroid.eventcreator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventPanelAdapter extends FragmentPagerAdapter {

    private List<String> mTablTitle = new ArrayList<>();
    private Event mEvent;

    public EventPanelAdapter(@NonNull FragmentManager fm, int behavior, Event event, boolean tabStage, Context context) {
        super(fm, behavior);
        mTablTitle.add(context.getString(R.string.event_creator_tab_one));
        if (tabStage) {
            mTablTitle.add(context.getString(R.string.event_creator_tab_two));
            mTablTitle.add(context.getString(R.string.event_creator_tab_three));
        }
        mEvent = event;
    }

    public void addStage(Context context){
        if (mTablTitle.size() == 1) {
            mTablTitle.add(context.getString(R.string.event_creator_tab_two));
            mTablTitle.add(context.getString(R.string.event_creator_tab_three));
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return EventCreatorMainPageFragment.newInstance(mEvent);
            case 1 :
                return EventCreatorStageFragment.newInstance(mEvent);

            default: return EventCreatorNeedsFragment.newInstance(mEvent);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTablTitle.get(position);
    }

    @Override
    public int getCount() {
        return mTablTitle.size();
    }
}

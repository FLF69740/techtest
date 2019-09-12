package com.epfd.csandroid.eventcreator;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.models.Event;
import com.google.android.material.tabs.TabLayout;

public class EventPanelActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_panel;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        Event tempEvent = new Event("temp", "01/01/1901", "temp event for test", "An awesome picture", "good label");
        ViewPager viewPager = findViewById(R.id.event_creator_panel_viewpager);
        viewPager.setAdapter(new EventPanelAdapter(getSupportFragmentManager(), 2, tempEvent, this));
        TabLayout tabLayout = findViewById(R.id.event_creator_panel_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


}

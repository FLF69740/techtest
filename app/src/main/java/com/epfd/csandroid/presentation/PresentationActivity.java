package com.epfd.csandroid.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PresentationActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_presentation;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        List<ArrayList<String>> myList = BusinessPresentation.createListing(getApplicationContext());
        ViewPager viewPager = findViewById(R.id.presentation_viewpager);
        viewPager.setAdapter(new PresentationAdapter(getSupportFragmentManager(),2, myList));
        TabLayout tabLayout = findViewById(R.id.presentation_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}

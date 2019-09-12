package com.epfd.csandroid.event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;

public class EventMainActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_main;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {

    }


}

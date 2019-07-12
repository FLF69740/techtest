package com.epfd.csandroid.firstpage;

import androidx.annotation.Nullable;

import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;

public class FirstPageActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_first_page;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Boolean isAChildActivity() {
        return false;
    }
}

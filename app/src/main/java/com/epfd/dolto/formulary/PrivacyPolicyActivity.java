package com.epfd.dolto.formulary;

import androidx.annotation.Nullable;

import android.os.Bundle;

import com.epfd.dolto.R;
import com.epfd.dolto.base.BaseActivity;

public class PrivacyPolicyActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {}

    @Override
    public Boolean isAChildActivity() {
        return false;
    }
}

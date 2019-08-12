package com.epfd.csandroid.formulary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;

public class PrivacyPolicyActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {}

    @Override
    public Boolean isAChildActivity() {
        return true;
    }
}

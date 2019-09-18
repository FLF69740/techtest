package com.epfd.csandroid.administrator.stageedition;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;

public class FileStageCreatorActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_file_stage_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {

    }
}

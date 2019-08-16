package com.epfd.csandroid.formulary;

import androidx.annotation.Nullable;
import android.os.Bundle;
import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;

public class NewsCreatorActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_news_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {

    }


}

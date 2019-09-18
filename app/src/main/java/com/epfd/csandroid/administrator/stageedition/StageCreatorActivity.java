package com.epfd.csandroid.administrator.stageedition;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.utils.Utils;

import butterknife.OnClick;

public class StageCreatorActivity extends BaseActivity {

    public static final String INTENT_STAGE_CREATOR_MENU = "INTENT_STAGE_CREATOR_MENU";

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_stage_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.stage_creator_add_stage) void addAStage(){
        Stage stage = new Stage("", Utils.EMPTY, Utils.EMPTY, 0);
        Intent intent = new Intent(this, FileStageCreatorActivity.class);
        intent.putExtra(INTENT_STAGE_CREATOR_MENU, stage);
        startActivity(intent);
    }




}

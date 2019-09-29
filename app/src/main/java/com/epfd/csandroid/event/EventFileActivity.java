package com.epfd.csandroid.event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.stageedition.recyclerview.StageCreatorAdapter;
import com.epfd.csandroid.api.StageCreatorHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.event.recyclerview.EventFileStageAdapter;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.utils.BitmapStorage;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventFileActivity extends BaseActivity implements EventFileStageAdapter.ListenerEventFileStage {

    @BindView(R.id.event_file_photo) ImageView mPhoto;
    @BindView(R.id.event_file_logo) ImageView mLogo;
    @BindView(R.id.event_file_title) TextView mTitle;
    @BindView(R.id.event_file_date) TextView mDate;
    @BindView(R.id.event_file_description) TextView mDescription;
    @BindView(R.id.event_file_stage_recycler) RecyclerView mRecyclerView;

    private EventFileStageAdapter mAdapter;
    private Event mEvent;
    private List<Stage> mStageList;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_file;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);

        mEvent = getIntent().getExtras().getParcelable(EventMainActivity.INTENT_EVENT_MENU);

        mPhoto.setImageBitmap(BitmapStorage.loadImage(this, mEvent.getPhoto()));
        mLogo.setImageBitmap(BitmapStorage.loadImage(this, mEvent.getLabel()));
        mTitle.setText(mEvent.getName());
        mDate.setText(mEvent.getDate());
        mDescription.setText(mEvent.getDescription());

        mStageList = new ArrayList<>();

        StageCreatorHelper.getStageCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    Stage stage = documentSnapshot.toObject(Stage.class);
                    if (mEvent.getStages().contains(stage.getUid())) {
                        setUserTimeTable(stage);
                        mStageList.add(stage);
                    }
                }
                mAdapter = new EventFileStageAdapter(mStageList, this);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    //extract currentUser timetable with StageRegistrationHelper
    private void setUserTimeTable(Stage stage){

    }


    @Override
    public void goParticipation(String stageUid) {
        Stage stage = new Stage();
        for (Stage request : mStageList){
            if (request.getUid().equals(stageUid)) stage = request;
        }
        ScheduleEventModalFragment.newInstance(stage, mEvent.getUid()).show(getSupportFragmentManager(), "MODAL");
    }
}

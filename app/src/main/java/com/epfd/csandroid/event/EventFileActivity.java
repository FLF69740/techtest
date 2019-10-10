package com.epfd.csandroid.event;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.epfd.csandroid.R;
import com.epfd.csandroid.api.StageCreatorHelper;
import com.epfd.csandroid.api.StageRegistrationHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.event.recyclerview.EventFileStageAdapter;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.models.ModalUserTimeTable;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.models.StageRegistration;
import com.epfd.csandroid.utils.BitmapStorage;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventFileActivity extends BaseActivity implements EventFileStageAdapter.ListenerEventFileStage, ScheduleEventModalFragment.modalFragmentListener,
NeedsEventModalFragment.needsFragmentListener {

    @BindView(R.id.event_file_photo) ImageView mPhoto;
    @BindView(R.id.event_file_logo) ImageView mLogo;
    @BindView(R.id.event_file_title) TextView mTitle;
    @BindView(R.id.event_file_date) TextView mDate;
    @BindView(R.id.event_file_description) TextView mDescription;
    @BindView(R.id.event_file_needs_btn) ImageView mNeedsListing;
    @BindView(R.id.event_file_stage_recycler) RecyclerView mRecyclerView;

    private EventFileStageAdapter mAdapter;
    private Event mEvent;
    private List<Stage> mStageList;
    private ModalUserTimeTable mTimeTable;

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
        mTimeTable = new ModalUserTimeTable();

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
        StageRegistrationHelper.getStageRegistration(mEvent.getUid()+stage.getUid()).addOnSuccessListener(documentSnapshot -> {
            StageRegistration stageRegistration = documentSnapshot.toObject(StageRegistration.class);

            if (getCurrentUser().getDisplayName() != null && stageRegistration.getParticipant().contains(getCurrentUser().getDisplayName())){
                EventBusiness.getTimeTableUpdated(mTimeTable, getCurrentUser().getDisplayName(),stageRegistration, stage);
            }
        });
    }

    @OnClick(R.id.event_file_needs_btn) void getNeedsLisiting(){
        NeedsEventModalFragment.newInstance(mEvent, getCurrentUser().getDisplayName(), getCurrentUser().getEmail()).show(getSupportFragmentManager(), "MODAL_NEED");
    }



    /**
     *  CALLBACK
     */

    @Override
    public void goParticipation(String stageUid) {
        Stage stage = new Stage();
        for (Stage request : mStageList){
            if (request.getUid().equals(stageUid)) stage = request;
        }
        ScheduleEventModalFragment.newInstance(stage, mEvent.getUid(), mTimeTable, getCurrentUser().getDisplayName(), getCurrentUser().getEmail()).show(getSupportFragmentManager(), "MODAL");
    }

    @Override
    public void callbackModal(ModalUserTimeTable modalUserTimeTable) {
        mTimeTable = new ModalUserTimeTable();
        for (Stage stage : mStageList) {
             this.setUserTimeTable(stage);
        }
    }

    /**
     *  AMONT CALLBACK
     */

    @Override
    public void callbackNeeds(Event event) {
        mEvent = event;
    }
}

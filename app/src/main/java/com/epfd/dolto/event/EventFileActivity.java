package com.epfd.dolto.event;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.epfd.dolto.R;
import com.epfd.dolto.api.StageCreatorHelper;
import com.epfd.dolto.api.StageRegistrationHelper;
import com.epfd.dolto.base.BaseActivity;
import com.epfd.dolto.event.recyclerview.EventFileStageAdapter;
import com.epfd.dolto.models.Event;
import com.epfd.dolto.models.ModalUserTimeTable;
import com.epfd.dolto.models.Stage;
import com.epfd.dolto.models.StageRegistration;
import com.epfd.dolto.utils.BitmapStorage;
import com.epfd.dolto.utils.StorageUtils;
import com.epfd.dolto.utils.Utils;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.io.File;
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
    @BindView(R.id.scrollView_event_file) ScrollView mScrollView;
    @BindView(R.id.event_file_logo_wait_process) ImageView mLogoWait;
    @BindView(R.id.event_file_progressBar) ProgressBar mProgressBar;

    private EventFileStageAdapter mAdapter;
    private Event mEvent;
    private List<StageRegistration> mRegistrations;
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
        mRegistrations = new ArrayList<>();

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
        }).addOnSuccessListener(queryDocumentSnapshots -> {
            mScrollView.setVisibility(View.VISIBLE);
            mLogoWait.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        });
    }

    //extract currentUser timetable with StageRegistrationHelper
    private void setUserTimeTable(Stage stage){
        StageRegistrationHelper.getStageRegistration(mEvent.getUid()+stage.getUid()).addOnSuccessListener(documentSnapshot -> {
            StageRegistration stageRegistration = documentSnapshot.toObject(StageRegistration.class);
            mRegistrations.add(stageRegistration);

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

    /**
     *  SEND PLANNINGS TO EMAIL
     */

    @OnClick(R.id.event_file_logo) void sendAllPlannings(){
        if (getCurrentUser().getEmail().equals(Utils.DEV)){

            StringBuilder builder = new StringBuilder();
            builder.append(EventBusiness.getFirstStepOfPlanningPaper())
                    .append(EventBusiness.getSecondStepOfPlanningPaper(mRegistrations, mStageList))
                    .append(EventBusiness.getThirdStepOfPlanningPaper(mEvent.getNeeds()));
            StorageUtils.setTextInStorage(getFilesDir(), this, "MYFILE.csv", "MYFOLDER", builder.toString());

            File imagePath = new File(getFilesDir(), "MYFOLDER");
            File newFile = new File(imagePath, "MYFILE.csv");

            String[] mail = {getCurrentUser().getEmail()};
            String subject = R.string.event_file_email_subject + mEvent.getName() + ", " + mEvent.getDate();
            String message = R.string.event_file_email_message + mEvent.getName() + ", " + mEvent.getDate();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, mail);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this,"com.epfd.dolto", newFile));

            intent.setType("message/rcf822");
            startActivity(Intent.createChooser(intent, "choose"));
        }
    }
}

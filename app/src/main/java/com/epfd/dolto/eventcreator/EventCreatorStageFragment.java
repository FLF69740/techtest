package com.epfd.dolto.eventcreator;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.epfd.dolto.R;
import com.epfd.dolto.api.EventHelper;
import com.epfd.dolto.api.StageCreatorHelper;
import com.epfd.dolto.api.StageRegistrationHelper;
import com.epfd.dolto.eventcreator.recyclerview.EventCreatorStageFragmentAdapter;
import com.epfd.dolto.models.Event;
import com.epfd.dolto.models.Stage;
import com.epfd.dolto.models.StageRegistration;
import com.epfd.dolto.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static android.app.Activity.RESULT_OK;


public class EventCreatorStageFragment extends Fragment implements EventCreatorStageFragmentAdapter.ListenerStageFragment{

    private static final String BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT";
    private static final String BUNDLE_EVENT_OUTSTATE = "BUNDLE_EVENT_OUTSTATE";

    @BindView(R.id.fragment_event_creator_stage_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.event_creator_event_publication_state) ImageView mPublicationBtn;

    private List<Stage> mStages;
    private Event mEvent;
    private EventCreatorStageFragmentAdapter mAdapter;

    static EventCreatorStageFragment newInstance(Event event){
        EventCreatorStageFragment eventCreatorStageFragment = new EventCreatorStageFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT, event);
        eventCreatorStageFragment.setArguments(bundle);
        return eventCreatorStageFragment;
    }

    public EventCreatorStageFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_creator_stage, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null){
            mEvent = savedInstanceState.getParcelable(BUNDLE_EVENT_OUTSTATE);
        }else {
            mEvent = getArguments().getParcelable(BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT);

        }

        if (!mEvent.getStages().equals("")){
            this.setStageListObject(mEvent.getStages());
        }else {
            mStages = new ArrayList<>();
        }

        if (mEvent.isAffichage()){
            mPublicationBtn.setImageResource(R.drawable.ic_visibility_24dp);
        } else {
            mPublicationBtn.setImageResource(R.drawable.ic_visibility_off_24dp);
        }
        mAdapter = new EventCreatorStageFragmentAdapter(mStages, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_EVENT_OUTSTATE, mEvent);
    }

    @OnClick(R.id.stage_creator_add_stage) void eventCreatorAddStage(){
        startActivityForResult(new Intent(getContext(), EventCreatorStageListingActivity.class), STAGE_REQUEST_CODE);
    }

    @OnClick(R.id.event_creator_event_publication_state) void changeEventVisibilityState(){
        if (mEvent.isAffichage()){
            mEvent.setAffichage(false);
            EventHelper.updateEventVisibility(mEvent.getUid(), false);
            mPublicationBtn.setImageResource(R.drawable.ic_visibility_off_24dp);
        }else {
            mEvent.setAffichage(true);
            EventHelper.updateEventVisibility(mEvent.getUid(), true);

            mPublicationBtn.setImageResource(R.drawable.ic_visibility_24dp);
        }
    }

    /**
     *  STAGES CREATION
     */

    private void setStageListObject(String listing){
        mStages = new ArrayList<>();
        String[] listString = listing.split(",");
        for (String stageName : listString){
            StageCreatorHelper.getStage(stageName).addOnSuccessListener(documentSnapshot -> {
                Stage stage = documentSnapshot.toObject(Stage.class);
                mStages.add(stage);
                mAdapter = new EventCreatorStageFragmentAdapter(mStages, this);
                mRecyclerView.setAdapter(mAdapter);
            });
        }

    }

    /**
     *  STAGES REGISTRATION
     */

    private void createStageRegistration(String stageName){
        StageCreatorHelper.getStage(stageName).addOnSuccessListener(documentSnapshot -> {
            Stage stage = documentSnapshot.toObject(Stage.class);
            if (stage != null && !stage.getSchedule().equals(Utils.EMPTY)) {
                int scheduleNumber = Utils.getSequenceNumberIntoAString(stage.getSchedule(), ',');
                StringBuilder registrationAnswer = new StringBuilder();
                for (int i = 0; i < scheduleNumber; i++) {
                    if (stage.getPeople() != 1) {
                        for (int j = 1; j < stage.getPeople(); j++) {
                            registrationAnswer.append(Utils.EMPTY).append(Utils.PARTICIPANT_SEPARATOR);
                        }
                        registrationAnswer.append(Utils.EMPTY + ",");
                    }else {
                        registrationAnswer.append(Utils.EMPTY + ",");
                    }
                }
                StageRegistration stageRegistration = new StageRegistration(registrationAnswer.toString(), mEvent.getUid()+stageName, stage.getPeople());
                StageRegistrationHelper.createStageRegistration(stageRegistration.getUid(), stageRegistration);
            }
        });
    }

    /**
     *  Callback
     */

    private static final int STAGE_REQUEST_CODE = 45;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (STAGE_REQUEST_CODE == requestCode && resultCode == RESULT_OK && !mEvent.getStages().contains(data.getStringExtra(EventCreatorStageListingActivity.FOR_RESULT_STAGE_EXTRA))){
            String stage = mEvent.getStages();
            if (!mEvent.getStages().equals("")){
                stage += ",";
            }
            stage += data.getStringExtra(EventCreatorStageListingActivity.FOR_RESULT_STAGE_EXTRA);
            createStageRegistration(data.getStringExtra(EventCreatorStageListingActivity.FOR_RESULT_STAGE_EXTRA));
            mEvent.setStages(stage);
            EventHelper.updateEventStages(mEvent.getUid(), stage).addOnSuccessListener(aVoid -> setStageListObject(mEvent.getStages()));
        }
    }


    @Override
    public void deleteStage(int position) {
        String stage = mEvent.getStages();
        stage = stage.replace(mStages.get(position).getUid(), "");
        if (stage.contains(",,")) {
            stage = stage.replace(",,", ",");
        }else if (stage.endsWith(",")){
            stage = stage.substring(0, stage.length()-1);
        }else if (stage.startsWith(",")){
            stage = stage.substring(1);
        }
        mEvent.setStages(stage);
        EventHelper.updateEventStages(mEvent.getUid(), stage);
        StageRegistrationHelper.deleteStageRegistration(mEvent.getUid()+mStages.get(position).getUid());
        mStages.remove(position);
        mAdapter.notifyDataSetChanged();
    }
}

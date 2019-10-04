package com.epfd.csandroid.event;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.csandroid.R;
import com.epfd.csandroid.api.StageRegistrationHelper;
import com.epfd.csandroid.event.recyclerview.BottomSheetSchedulesAdapter;
import com.epfd.csandroid.models.ModalUserTimeTable;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.models.StageRegistration;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleEventModalFragment extends BottomSheetDialogFragment implements BottomSheetSchedulesAdapter.ListenerBottomSheet{

    @BindView(R.id.modal_fragment_stage_title) TextView mTitle;
    @BindView(R.id.modal_fragment_recycler) RecyclerView mRecyclerView;

    private BottomSheetSchedulesAdapter mAdapter;
    private ArrayList<SingleScheduleBottomSheet> mPlanning;
    private String mUserName;
    private ModalUserTimeTable mTimeTable;
    private String mRegistration;

    private static final String KEY_SCHEDULE_ID = "KEY_SCHEDULE_ID";
    private static final String KEY_EVENT_ID = "KEY_EVENT_ID";
    private static final String KEY_TIMETABLE = "KEY_TIMETABLE";
    private static final String KEY_USERNAME = "KEY_USERNAME";
    private static final String SAVE_INSTANCE_STATE_PLANNING = "SAVE_INSTANCE_STATE_PLANNING";

    static ScheduleEventModalFragment newInstance(Stage stage, String eventUid, ModalUserTimeTable timeTable, String userName){
        ScheduleEventModalFragment scheduleEventModalFragment = new ScheduleEventModalFragment();
        Bundle bundle = new Bundle(4);
        bundle.putParcelable(KEY_SCHEDULE_ID, stage);
        bundle.putString(KEY_EVENT_ID, eventUid);
        bundle.putParcelable(KEY_TIMETABLE, timeTable);
        bundle.putString(KEY_USERNAME, userName);
        scheduleEventModalFragment.setArguments(bundle);
        return scheduleEventModalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_fragment_schedule_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Stage stage = getArguments().getParcelable(KEY_SCHEDULE_ID);
        mRegistration = getArguments().getString(KEY_EVENT_ID) + stage.getUid();
        mTimeTable = getArguments().getParcelable(KEY_TIMETABLE);
        mUserName = getArguments().getString(KEY_USERNAME);

        mTitle.setText(stage.getTitle());

        List<String> scheduleStageString = Arrays.asList(stage.getSchedule().split(","));

        if (savedInstanceState != null){
            mPlanning = savedInstanceState.getParcelableArrayList(SAVE_INSTANCE_STATE_PLANNING);
        }else {
            mPlanning = new ArrayList<>();

            for (String schedule : scheduleStageString) {
                SingleScheduleBottomSheet singleScheduleBottomSheet = new SingleScheduleBottomSheet(schedule, mRegistration);
                mPlanning.add(singleScheduleBottomSheet);
                updatePeopleIntoPlanning(mPlanning, mRegistration);
            }

            EventBusiness.compareTimeTableAndStagePlanning(mPlanning, mTimeTable);

        }

        mAdapter = new BottomSheetSchedulesAdapter(mPlanning, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_INSTANCE_STATE_PLANNING, mPlanning);
    }

    private void updatePeopleIntoPlanning(List<SingleScheduleBottomSheet> planning, String uid){
        StageRegistrationHelper.getStageRegistration(uid).addOnSuccessListener(documentSnapshot -> {
            StageRegistration registration = documentSnapshot.toObject(StageRegistration.class);
            List<String> peopleGroup = Arrays.asList(registration.getParticipant().split(","));
            for (int i = 0 ; i < planning.size() ; i++){
                planning.get(i).setParticipantList(peopleGroup.get(i));
            }
            mAdapter.notifyDataSetChanged();
        });
    }


    @Override
    public void activeParticipation(int position) {
        EventBusiness.addParticipantIntoPlanning(mPlanning, mUserName, position);
        StageRegistrationHelper.updateStageRegistrationParticipant(mRegistration, EventBusiness.listPlanningToString(mPlanning))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mAdapter.notifyDataSetChanged();
                        mCallback.callbackModal(mTimeTable);
                    }
                });

    }

    @Override
    public void deleteParticipation(int position) {
        EventBusiness.deleteParticipantIntoPlanning(mPlanning, mUserName, position);
        StageRegistrationHelper.updateStageRegistrationParticipant(mRegistration, EventBusiness.listPlanningToString(mPlanning))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mAdapter.notifyDataSetChanged();
                        mCallback.callbackModal(mTimeTable);
                    }
                });
    }


    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface modalFragmentListener{
        void callbackModal(ModalUserTimeTable modalUserTimeTable);
    }

    //callback for button clicked
    private modalFragmentListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (modalFragmentListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }

}

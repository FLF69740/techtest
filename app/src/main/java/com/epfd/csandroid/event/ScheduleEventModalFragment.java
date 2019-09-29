package com.epfd.csandroid.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.api.StageRegistrationHelper;
import com.epfd.csandroid.event.recyclerview.BottomSheetSchedulesAdapter;
import com.epfd.csandroid.event.recyclerview.EventFileStageAdapter;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.models.StageRegistration;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleEventModalFragment extends BottomSheetDialogFragment {

    @BindView(R.id.modal_fragment_stage_title) TextView mTitle;
    @BindView(R.id.modal_fragment_recycler) RecyclerView mRecyclerView;

    private BottomSheetSchedulesAdapter mAdapter;

    private static final String KEY_SCHEDULE_ID = "KEY_SCHEDULE_ID";
    private static final String KEY_EVENT_ID = "KEY_EVENT_ID";

    static ScheduleEventModalFragment newInstance(Stage stage, String eventUid){
        ScheduleEventModalFragment scheduleEventModalFragment = new ScheduleEventModalFragment();
        Bundle bundle = new Bundle(2);
        bundle.putParcelable(KEY_SCHEDULE_ID, stage);
        bundle.putString(KEY_EVENT_ID, eventUid);
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
        String registration = getArguments().getString(KEY_EVENT_ID) + stage.getUid();

        mTitle.setText(stage.getTitle());

        List<String> scheduleStageString = Arrays.asList(stage.getSchedule().split(","));
        List<SingleScheduleBottomSheet> planning = new ArrayList<>();

        for (String schedule : scheduleStageString){
            SingleScheduleBottomSheet singleScheduleBottomSheet = new SingleScheduleBottomSheet(schedule);
            planning.add(singleScheduleBottomSheet);
            updatePeopleIntoPlanning(planning, registration);
        }

        mAdapter = new BottomSheetSchedulesAdapter(planning);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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


}

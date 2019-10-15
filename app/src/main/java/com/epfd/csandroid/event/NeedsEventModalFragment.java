package com.epfd.csandroid.event;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.epfd.csandroid.R;
import com.epfd.csandroid.api.EventHelper;
import com.epfd.csandroid.event.recyclerview.BottomSheetSchedulesAdapter;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;
import com.epfd.csandroid.utils.AdminDialog;
import com.epfd.csandroid.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NeedsEventModalFragment extends BottomSheetDialogFragment implements BottomSheetSchedulesAdapter.ListenerBottomSheet, AdminDialog.ListenerAdminDialog{

    @BindView(R.id.modal_fragment_stage_title) TextView mTitle;
    @BindView(R.id.modal_fragment_recycler) RecyclerView mRecyclerView;

    private BottomSheetSchedulesAdapter mAdapter;
    private String mUserName;
    private Event mEvent;
    private ArrayList<SingleScheduleBottomSheet> mPlanning;
    private int mAdminPosition; // after dialog fragment callback define planning position
    private boolean mAdminAddAction; // after dialog fragment callback define if it'a add process or delete process

    private static final String KEY_EVENT_ID = "KEY_EVENT_ID";
    private static final String KEY_USERNAME = "KEY_USERNAME";
    private static final String KEY_MAIL = "KEY_MAIL";
    private static final String SAVE_INSTANCE_STATE_PLANNING = "SAVE_INSTANCE_STATE_PLANNING";
    private static final String SAVE_INSTANCE_STATE_ADMIN_POS = "SAVE_INSTANCE_STATE_ADMIN_POS";
    private static final String SAVE_INSTANCE_STATE_ADMIN_ACTION = "SAVE_INSTANCE_STATE_ADMIN_ACTION";

    public NeedsEventModalFragment() {}

    static NeedsEventModalFragment newInstance(Event event, String username, String mailDev){
        NeedsEventModalFragment needsEventModalFragment = new NeedsEventModalFragment();
        Bundle bundle = new Bundle(3);
        bundle.putParcelable(KEY_EVENT_ID, event);
        bundle.putString(KEY_USERNAME, username);
        bundle.putString(KEY_MAIL, mailDev);
        needsEventModalFragment.setArguments(bundle);
        return needsEventModalFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_fragment_schedule_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEvent = getArguments().getParcelable(KEY_EVENT_ID);
        mUserName = getArguments().getString(KEY_USERNAME);
        String mailDev = getArguments().getString(KEY_MAIL);

        mTitle.setText(getContext().getString(R.string.event_file_stage_needs));

        if (savedInstanceState != null){
            mPlanning = savedInstanceState.getParcelableArrayList(SAVE_INSTANCE_STATE_PLANNING);
        }else {
            mPlanning = new ArrayList<>();

            for (String need : Arrays.asList(mEvent.getNeeds().split(","))) {
                SingleScheduleBottomSheet singleScheduleBottomSheet = new SingleScheduleBottomSheet(need, mEvent.getUid());
                singleScheduleBottomSheet.setSchedule(need.substring(0, need.indexOf(":")));
                singleScheduleBottomSheet.setParticipantList(need.substring(need.indexOf(":")+1));
                if (need.contains(mUserName)){
                    singleScheduleBottomSheet.setNotRegistered(false);
                }
                mPlanning.add(singleScheduleBottomSheet);
            }
        }

        boolean adminAct = false;

        if (mailDev.equals(Utils.DEV)){
            adminAct = true;
        }

        mAdapter = new BottomSheetSchedulesAdapter(mPlanning, this, adminAct);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_INSTANCE_STATE_PLANNING, mPlanning);
        outState.putInt(SAVE_INSTANCE_STATE_ADMIN_POS, mAdminPosition);
        outState.putBoolean(SAVE_INSTANCE_STATE_ADMIN_ACTION, mAdminAddAction);
    }

    private void addProcess(int position, boolean adminAct, @Nullable String adminName){
        String targetName = mUserName;
        if (adminAct) {
            targetName = adminName;
        }
        if (mPlanning.get(position).getParticipantList().get(0).contains(Utils.EMPTY)) {
            mPlanning.get(position).setOtherParticipantList(targetName);
        } else {
            mPlanning.get(position).setOtherParticipantList(Utils.getStringListWithSeparator(mPlanning.get(position).getParticipantList(), Utils.PARTICIPANT_SEPARATOR)
                    + Utils.PARTICIPANT_SEPARATOR + mUserName);
        }

        mPlanning.get(position).setNotRegistered(true);
        for (String participant : mPlanning.get(position).getParticipantList()) {
            if (participant.equals(mUserName)) {
                mPlanning.get(position).setNotRegistered(false);
            }
        }

        mEvent.setNeeds(EventBusiness.getEventNeedsString(mPlanning));

        EventHelper.updateEventNeeds(mEvent.getUid(), mEvent.getNeeds()).addOnSuccessListener(aVoid -> {
            mAdapter.notifyDataSetChanged();
            mCallback.callbackNeeds(mEvent);
        });
    }

    private void deleteProcess(int position, boolean adminAct, @Nullable String adminName){
        boolean targetFind = false;
        String targetName = mUserName;
        if (adminAct) {
            targetName = adminName;
        }
        for (int i = 0; i < mPlanning.get(position).getParticipantList().size(); i++){
            if (mPlanning.get(position).getParticipantList().get(i).equals(targetName)){
                mPlanning.get(position).getParticipantList().remove(i);
                targetFind = true;
            }
            if (targetFind){
                i = mPlanning.get(position).getParticipantList().size();
            }
        }

        mPlanning.get(position).setNotRegistered(true);
        for (String participant : mPlanning.get(position).getParticipantList()) {
            if (participant.equals(mUserName)) {
                mPlanning.get(position).setNotRegistered(false);
            }
        }

        mEvent.setNeeds(EventBusiness.getEventNeedsString(mPlanning));

        EventHelper.updateEventNeeds(mEvent.getUid(), mEvent.getNeeds()).addOnSuccessListener(aVoid -> {
            mAdapter.notifyDataSetChanged();
            mCallback.callbackNeeds(mEvent);
        });
    }

    @Override
    public void activeParticipation(int position) {
        addProcess(position, false, null);
    }

    @Override
    public void deleteParticipation(int position) {
        deleteProcess(position, false, null);
    }

    @Override
    public void getAdminChoiceUsername(String name) {
        if (!name.equals(Utils.EMPTY) && !name.equals(mUserName)) addProcess(mAdminPosition, true, name);
    }

    @Override
    public void deleteAdminChoiceUsername(String name) {
        if (!name.equals(Utils.EMPTY) && !name.equals(mUserName)) deleteProcess(mAdminPosition, true, name);
    }

    @Override
    public void adminParticipation(int position) {
        mAdminPosition = position;
        mAdminAddAction = false;
        AdminDialog adminDialog = new AdminDialog();
        adminDialog.setTargetFragment(this, 0);
        adminDialog.show(getFragmentManager(), Utils.ADMIN_DIALOG_ASK);
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface needsFragmentListener{
        void callbackNeeds(Event event);
    }

    //callback for button clicked
    private needsFragmentListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (needsFragmentListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }


}

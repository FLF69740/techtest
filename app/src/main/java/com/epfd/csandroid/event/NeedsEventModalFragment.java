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
import com.epfd.csandroid.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NeedsEventModalFragment extends BottomSheetDialogFragment implements BottomSheetSchedulesAdapter.ListenerBottomSheet {



    @BindView(R.id.modal_fragment_stage_title) TextView mTitle;
    @BindView(R.id.modal_fragment_recycler) RecyclerView mRecyclerView;

    private BottomSheetSchedulesAdapter mAdapter;
    private String mUserName;
    private Event mEvent;
    private ArrayList<SingleScheduleBottomSheet> mPlanning;

    private static final String KEY_EVENT_ID = "KEY_EVENT_ID";
    private static final String KEY_USERNAME = "KEY_USERNAME";
    private static final String SAVE_INSTANCE_STATE_PLANNING = "SAVE_INSTANCE_STATE_PLANNING";

    public NeedsEventModalFragment() {}

    static NeedsEventModalFragment newInstance(Event event, String username){
        NeedsEventModalFragment needsEventModalFragment = new NeedsEventModalFragment();
        Bundle bundle = new Bundle(2);
        bundle.putParcelable(KEY_EVENT_ID, event);
        bundle.putString(KEY_USERNAME, username);
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

        mAdapter = new BottomSheetSchedulesAdapter(mPlanning, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    // GET the list of needs name
    private static List<String> getRootNeeds(List<String>  needsListString){
        List<String> rootNeeds = new ArrayList<>();
        for (String need : needsListString){
            rootNeeds.add(need.substring(0, need.indexOf(":")));
        }
        return rootNeeds;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_INSTANCE_STATE_PLANNING, mPlanning);
    }

    @Override
    public void activeParticipation(int position) {
        if (mPlanning.get(position).getParticipantList().get(0).contains(Utils.EMPTY)){
            mPlanning.get(position).setParticipantList(mUserName);
        }else {
            mPlanning.get(position).setParticipantList(Utils.getStringListWithSeparator(mPlanning.get(position).getParticipantList(), Utils.PARTICIPANT_SEPARATOR)
                    + Utils.PARTICIPANT_SEPARATOR + mUserName);
        }
        mPlanning.get(position).setNotRegistered(false);

        StringBuilder builder = new StringBuilder();
        String prefix = "";
        for (int i = 0 ; i < mPlanning.size() ; i++){
            String prefix_under = "";
            builder.append(prefix).append(mPlanning.get(i).getSchedule()).append(":");
            for (int j = 0 ; j < mPlanning.get(i).getParticipantList().size() ; j++){
                builder.append(prefix_under).append(mPlanning.get(i).getParticipantList().get(j));
                prefix_under = Utils.PARTICIPANT_SEPARATOR;
            }
            prefix = ",";
        }

        mEvent.setNeeds(builder.toString());

        EventHelper.updateEventNeeds(mEvent.getUid(), builder.toString()).addOnSuccessListener(aVoid -> {
            mAdapter.notifyDataSetChanged();
            mCallback.callbackNeeds(mEvent);
        });

    }

    @Override
    public void deleteParticipation(int position) {
        mPlanning.get(position).getParticipantList().remove(mPlanning.get(position).getParticipantList().size()-1);
        mPlanning.get(position).setNotRegistered(true);

        StringBuilder builder = new StringBuilder();
        String prefix = "";
        for (int i = 0 ; i < mPlanning.size() ; i++){
            String prefix_under = "";
            builder.append(prefix).append(mPlanning.get(i).getSchedule()).append(":");
            for (int j = 0 ; j < mPlanning.get(i).getParticipantList().size() ; j++){
                builder.append(prefix_under).append(mPlanning.get(i).getParticipantList().get(j));
                prefix_under = Utils.PARTICIPANT_SEPARATOR;
            }
            prefix = ",";
        }

        mEvent.setNeeds(builder.toString());

        EventHelper.updateEventNeeds(mEvent.getUid(), builder.toString()).addOnSuccessListener(aVoid -> {
            mAdapter.notifyDataSetChanged();
            mCallback.callbackNeeds(mEvent);
        });
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

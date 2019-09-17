package com.epfd.csandroid.eventcreator;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreatorStageFragment extends Fragment {

    private static final String BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT";
    private static final String BUNDLE_EVENT_OUTSTATE = "BUNDLE_EVENT_OUTSTATE";

    private Event mEvent;

    public static EventCreatorStageFragment newInstance(Event event){
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

        if (savedInstanceState != null){
            mEvent = savedInstanceState.getParcelable(BUNDLE_EVENT_OUTSTATE);
        }else {
            mEvent = getArguments().getParcelable(BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT);
        }



        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_EVENT_OUTSTATE, mEvent);
    }


}

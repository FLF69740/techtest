package com.epfd.csandroid.eventcreator;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreatorStageFragment extends Fragment {

    public static final String BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT";

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
        return inflater.inflate(R.layout.fragment_event_creator_stage, container, false);
    }

}

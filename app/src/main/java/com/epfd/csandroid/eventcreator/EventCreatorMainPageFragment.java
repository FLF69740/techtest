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
public class EventCreatorMainPageFragment extends Fragment {

    public static final String BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT";

    public static EventCreatorMainPageFragment newInstance(Event event){
        EventCreatorMainPageFragment eventCreatorMainPageFragment = new EventCreatorMainPageFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT, event);
        eventCreatorMainPageFragment.setArguments(bundle);
        return eventCreatorMainPageFragment;
    }

    public EventCreatorMainPageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_creator_main_page, container, false);




        return view;
    }

}

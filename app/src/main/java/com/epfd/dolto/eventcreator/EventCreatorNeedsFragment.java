package com.epfd.dolto.eventcreator;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.epfd.dolto.R;
import com.epfd.dolto.api.EventHelper;
import com.epfd.dolto.eventcreator.recyclerview.EventCreatorNeedsAdapter;
import com.epfd.dolto.models.Event;
import com.epfd.dolto.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreatorNeedsFragment extends Fragment implements EventCreatorNeedsAdapter.ListenerNeedsRecycler {

    private static final String BUNDLE_EVENT_CREATOR_NEEDS_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_NEEDS_EVENT_OBJECT";
    private static final String BUNDLE_NEEDS_OUTSTATE = "BUNDLE_NEEDS_OUTSTATE";

    private List<String> mMyNeed;
    private Event mEvent;
    private EventCreatorNeedsAdapter mAdapter;

    @BindView(R.id.needs_creator_recyclerview) RecyclerView mRecyclerView;

    public EventCreatorNeedsFragment() {}

    public static EventCreatorNeedsFragment newInstance(Event event){
        EventCreatorNeedsFragment eventCreatorNeedsFragment = new EventCreatorNeedsFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(BUNDLE_EVENT_CREATOR_NEEDS_EVENT_OBJECT, event);
        eventCreatorNeedsFragment.setArguments(bundle);
        return eventCreatorNeedsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_creator_needs, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null){
            mEvent = savedInstanceState.getParcelable(BUNDLE_NEEDS_OUTSTATE);
        }else {
            mEvent = getArguments().getParcelable(BUNDLE_EVENT_CREATOR_NEEDS_EVENT_OBJECT);
        }

        if (!mEvent.getNeeds().equals("")) {
            mMyNeed = new ArrayList<>(Arrays.asList(mEvent.getNeeds().split(",")));

        }else {
            mMyNeed = new ArrayList<>();
        }

        mMyNeed.add("");

        Context recyclerContext = mRecyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerContext, R.anim.layout_slide_from_bottom);
        mAdapter = new EventCreatorNeedsAdapter(mMyNeed, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.scheduleLayoutAnimation();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_NEEDS_OUTSTATE, mEvent);
    }

    @Override
    public void addNeed(String need) {
        mMyNeed.add(0, need + ":" + Utils.EMPTY);
        mEvent.setNeeds(Utils.getStringListWithSeparator(mMyNeed, ","));
        EventHelper.updateEventNeeds(mEvent.getUid(), mEvent.getNeeds()).addOnSuccessListener(aVoid -> mAdapter.notifyDataSetChanged());
    }

    @Override
    public void deleteNeed(int position) {
        mMyNeed.remove(position);
        mEvent.setNeeds(Utils.getStringListWithSeparator(mMyNeed, ","));
        EventHelper.updateEventNeeds(mEvent.getUid(), mEvent.getNeeds()).addOnSuccessListener(aVoid -> mAdapter.notifyDataSetChanged());
    }
}

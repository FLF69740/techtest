package com.epfd.csandroid.eventcreator;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Event;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EventCreatorMainPageFragment extends Fragment {

    public static final String BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT";
    @BindView(R.id.event_creator_panel) TextView mTextView;
    @BindView(R.id.event_creator_panel_photo) ImageView mPhoto;
    @BindView(R.id.event_creator_label) ImageView mLabel;
    @BindView(R.id.event_creator_guide_navigation_bottom) Guideline mNavGuideline;

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
        ButterKnife.bind(this, view);

        Event event = getArguments().getParcelable(BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT);

        mTextView.setText(event.getName());



        return view;
    }

    @OnClick(R.id.event_creator_panel_photo)
    public void eventCreatorLoadPhoto(){
        playAnimation();
    }

    @OnClick(R.id.event_creator_label)
    public void eventCreatorLoadLogo(){
        playAnimation();
    }

    /**
     *  BOTTOM NAVIGATION ANIMATION
     */

    private PropertyValuesHolder mAlphaValues = PropertyValuesHolder.ofInt(DIMENSION, 100, 90);
    private static final String DIMENSION = "DIMENSION";

    // launch animation at the start of MainActivity
    private void playAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        animators.add(showBottomNavigation());
        animatorSet.playSequentially(animators);
        animatorSet.start();
    }

    // reduce logo
    private Animator showBottomNavigation(){
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(mAlphaValues);
        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            int bottomValue = (int) animation.getAnimatedValue(DIMENSION);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mNavGuideline.getLayoutParams();
            params.guidePercent = (float)bottomValue /100;
            mNavGuideline.setLayoutParams(params);
        });
        return animator;
    }





























}

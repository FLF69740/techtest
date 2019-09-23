package com.epfd.csandroid.eventcreator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import com.epfd.csandroid.R;
import com.epfd.csandroid.api.StageCreatorHelper;
import com.epfd.csandroid.eventcreator.recyclerview.EventCreatorStageFragmentAdapter;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.models.Stage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreatorStageFragment extends Fragment implements EventCreatorStageFragmentAdapter.ListenerStageFragment{

    private static final String BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT";
    private static final String BUNDLE_EVENT_OUTSTATE = "BUNDLE_EVENT_OUTSTATE";

    @BindView(R.id.event_creator_stage_fragment_bottomNavigationView) BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fragment_event_creator_stage_recycler) RecyclerView mRecyclerView;

    private List<Stage> mStages;
    private Event mEvent;
    private View mView;
    private EventCreatorStageFragmentAdapter mAdapter;

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
        mView = inflater.inflate(R.layout.fragment_event_creator_stage, container, false);
        ButterKnife.bind(this, mView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return bottomNavigationViewAction(item.getItemId());
            }
        });

        if (savedInstanceState != null){
            mEvent = savedInstanceState.getParcelable(BUNDLE_EVENT_OUTSTATE);
        }else {
            mEvent = getArguments().getParcelable(BUNDLE_EVENT_CREATOR_STAGE_EVENT_OBJECT);

        }

        if (!mEvent.getStages().equals("")){
            this.setStageListObject(mEvent.getStages());
        }
        mAdapter = new EventCreatorStageFragmentAdapter(mStages, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);


        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_EVENT_OUTSTATE, mEvent);
    }

    @OnClick(R.id.stage_creator_add_stage) void eventCreatorAddStage(){
        startActivityForResult(new Intent(getContext(), EventCreatorStageListingActivity.class), STAGE_REQUEST_CODE);
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
                mAdapter.notifyDataSetChanged();
            });
        }

    }

    /**
     *  BOTTOM NAVIGATION ACTION
     */

    private Boolean bottomNavigationViewAction(Integer integer){
        switch (integer){
            case R.id.stage_fragment_save :
                Snackbar.make(mView, R.string.event_creator_stages_link, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.stage_fragment_publication :
                if (mEvent.isAffichage()){
                    Snackbar.make(mView, R.string.event_creator_event_not_published, Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(mView, R.string.event_creator_event_published, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    /**
     *  Callback
     */

    private static final int STAGE_REQUEST_CODE = 45;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (STAGE_REQUEST_CODE == requestCode && resultCode == RESULT_OK){
            String stage = data.getStringExtra(EventCreatorStageListingActivity.FOR_RESULT_STAGE_EXTRA);
            Toast.makeText(getContext(), "STAGE IMPORTE : " + stage, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void deleteStage(int position) {
        mStages.remove(position);
        mAdapter.notifyDataSetChanged();
    }
}

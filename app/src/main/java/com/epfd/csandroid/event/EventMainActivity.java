package com.epfd.csandroid.event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.epfd.csandroid.R;
import com.epfd.csandroid.api.EventHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.eventcreator.recyclerview.EventCreatorMenuAdapter;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventMainActivity extends BaseActivity {

    public static final String INTENT_EVENT_MENU = "INTENT_EVENT_MENU";

    private List<Event> mEventList;
    private EventCreatorMenuAdapter mAdapter;
    @BindView(R.id.event_recycler) RecyclerView mRecyclerView;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_main;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mEventList = new ArrayList<>();

        EventHelper.getEventsCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    Event event = documentSnapshot.toObject(Event.class);
                    if (event.isAffichage()) {
                        mEventList.add(event);
                    }
                }
                Context recyclerContext = mRecyclerView.getContext();
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerContext, R.anim.layout_slide_from_right);
                mAdapter = new EventCreatorMenuAdapter(mEventList);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                }else {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutAnimation(controller);
                mRecyclerView.scheduleLayoutAnimation();
                RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.creator_event_menu_recycler_item)
                        .setOnItemClickListener((recyclerView, position, v) -> {
                            Intent intent = new Intent(getApplicationContext(), EventFileActivity.class);
                            intent.putExtra(INTENT_EVENT_MENU, mEventList.get(position));
                            startActivity(intent);
                        });
            }
        });
    }


}

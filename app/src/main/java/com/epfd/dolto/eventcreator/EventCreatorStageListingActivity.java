package com.epfd.dolto.eventcreator;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.epfd.dolto.R;
import com.epfd.dolto.administrator.stageedition.recyclerview.StageCreatorAdapter;
import com.epfd.dolto.api.StageCreatorHelper;
import com.epfd.dolto.base.BaseActivity;
import com.epfd.dolto.models.Stage;
import com.epfd.dolto.utils.RecyclerViewClickSupport;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventCreatorStageListingActivity extends BaseActivity {

    private List<Stage> mStageList;
    private StageCreatorAdapter mAdapter;
    @BindView(R.id.stage_gallery_recyclerview) RecyclerView mRecyclerView;

    public static final String FOR_RESULT_STAGE_EXTRA = "FOR_RESULT_STAGE_EXTRA";

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_creator_stage_listing;
    }

    @Override
    public Boolean isAChildActivity() {
        return null;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);

        mStageList = new ArrayList<>();

        StageCreatorHelper.getStageCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    Stage stage = documentSnapshot.toObject(Stage.class);
                    mStageList.add(stage);
                }
                Context recyclerContext = mRecyclerView.getContext();
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerContext, R.anim.layout_slide_from_bottom);
                mAdapter = new StageCreatorAdapter(mStageList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutAnimation(controller);
                mRecyclerView.scheduleLayoutAnimation();
                RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.creator_stage_menu_recycler_item)
                        .setOnItemClickListener((recyclerView, position, v) -> {
                            Intent intent = new Intent();
                            intent.putExtra(FOR_RESULT_STAGE_EXTRA, mStageList.get(position).getUid());
                            setResult(RESULT_OK, intent);
                            finish();
                        });
            }
        });
    }


}

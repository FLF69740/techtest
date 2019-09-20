package com.epfd.csandroid.administrator.stageedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.stageedition.recyclerview.StageCreatorAdapter;
import com.epfd.csandroid.api.StageCreatorHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.eventcreator.recyclerview.EventCreatorMenuAdapter;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import com.epfd.csandroid.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StageCreatorActivity extends BaseActivity {

    public static final String INTENT_STAGE_CREATOR_MENU = "INTENT_STAGE_CREATOR_MENU";

    private List<Stage> mStageList;
    private StageCreatorAdapter mAdapter;
    @BindView(R.id.stage_creator_recycler) RecyclerView mRecyclerView;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_stage_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mStageList = new ArrayList<>();

        StageCreatorHelper.getStageCollection().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                                Intent intent = new Intent(getApplicationContext(), FileStageCreatorActivity.class);
                                intent.putExtra(INTENT_STAGE_CREATOR_MENU, mStageList.get(position));
                                startActivity(intent);
                            });
                }
            }
        });
    }

    @OnClick(R.id.stage_creator_add_stage) void addAStage(){
        Stage stage = new Stage(Utils.EMPTY,"", Utils.EMPTY, Utils.EMPTY, 0);
        Intent intent = new Intent(this, FileStageCreatorActivity.class);
        intent.putExtra(INTENT_STAGE_CREATOR_MENU, stage);
        startActivity(intent);
    }




}

package com.epfd.csandroid.administrator.newsedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.newsedition.recyclerview.NewsCreatorAdapter;
import com.epfd.csandroid.api.NewsHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.firstpage.recyclerview.FirstPageAdapter;
import com.epfd.csandroid.models.News;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsCreatorActivity extends BaseActivity {

    private List<News> mNewsList;
    private NewsCreatorAdapter mAdapter;
    @BindView(R.id.news_creator_recycler) RecyclerView mRecyclerView;

    public static final String INTENT_CREATOR_NEWS = "INTENT_CREATOR_NEWS";

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_news_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mNewsList = new ArrayList<>();

        NewsHelper.getNewsCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    News news = documentSnapshot.toObject(News.class);
                    mNewsList.add(news);
                }
                Context recyclerContext = mRecyclerView.getContext();
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerContext, R.anim.layout_slide_from_bottom);
                mAdapter = new NewsCreatorAdapter(mNewsList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutAnimation(controller);
                mRecyclerView.scheduleLayoutAnimation();
                RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.creator_news_recycler_item)
                        .setOnItemClickListener((recyclerView, position, v) -> {
                            Intent intent = new Intent(getApplicationContext(), FileNewsCreatorActivity.class);
                  //          intent.putExtra(INTENT_PHOTO_NEWS, mNewsList.get(position).getPhoto());
                            intent.putExtra(INTENT_CREATOR_NEWS, mNewsList.get(position));
                            startActivity(intent);
                        });
            }
        });
    }

    @OnClick(R.id.news_creator_add_news)
    public void newsCreatorAddNews(){
        startActivity(new Intent(this, FileNewsCreatorActivity.class));
    }

}

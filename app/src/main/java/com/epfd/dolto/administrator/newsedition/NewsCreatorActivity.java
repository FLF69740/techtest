package com.epfd.dolto.administrator.newsedition;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.epfd.dolto.R;
import com.epfd.dolto.administrator.newsedition.recyclerview.NewsCreatorAdapter;
import com.epfd.dolto.api.CakeHelper;
import com.epfd.dolto.api.NewsHelper;
import com.epfd.dolto.base.BaseActivity;
import com.epfd.dolto.models.News;
import com.epfd.dolto.utils.RecyclerViewClickSupport;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    private static final int RC_RECYCLER_UPDATE = 101;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_news_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return false;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mNewsList = new ArrayList<>();

        NewsHelper.getNewsCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    News news = documentSnapshot.toObject(News.class);
                    if (!news.getTitle().equals(CakeHelper.getEventName())) {
                        mNewsList.add(news);
                    }
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
                            intent.putExtra(INTENT_CREATOR_NEWS, mNewsList.get(position));
                            startActivityForResult(intent, RC_RECYCLER_UPDATE);
                        });
            }
        });
    }

    @OnClick(R.id.news_creator_add_news)
    public void newsCreatorAddNews(){
        startActivityForResult(new Intent(this, FileNewsCreatorActivity.class), RC_RECYCLER_UPDATE);
    }

    /**
     *  ACTIVITY RESULT
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_RECYCLER_UPDATE && resultCode == RESULT_OK) {
            mNewsList.clear();
            NewsHelper.getNewsCollection().get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        News news = documentSnapshot.toObject(News.class);
                        if (!news.getTitle().equals(CakeHelper.getEventName())) {
                            mNewsList.add(news);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}

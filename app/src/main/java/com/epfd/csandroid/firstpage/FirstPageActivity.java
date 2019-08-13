package com.epfd.csandroid.firstpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.api.NewsHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.firstpage.recyclerview.FirstPageAdapter;
import com.epfd.csandroid.models.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstPageActivity extends BaseActivity {

    private List<News> mNewsList;
    private FirstPageAdapter mPageAdapter;

    @BindView(R.id.first_page_recycler) RecyclerView mRecyclerView;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_first_page;
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
            if (task.isSuccessful()){
                if (task.getResult() != null) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        News news = documentSnapshot.toObject(News.class);
                        DateTime dateTime = new DateTime();
                        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
                        DateTime dateNews = fmt.parseDateTime(news.getPublication());
                        if (dateNews.getYear() < dateTime.getYear() || (dateNews.getYear() == dateTime.getYear() && dateNews.getDayOfYear() <= dateTime.getDayOfYear())) {
                            mNewsList.add(news);
                        }
                    }
                }
                mPageAdapter = new FirstPageAdapter(mNewsList);
                mRecyclerView.setAdapter(mPageAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });




    }


}

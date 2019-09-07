package com.epfd.csandroid.administrator.newsedition.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.News;

import java.util.List;

public class NewsCreatorAdapter extends RecyclerView.Adapter<NewsCreatorViewHolder> {

    private List<News> mNewsList;

    public NewsCreatorAdapter(List<News> newsList) {
        mNewsList = newsList;
    }

    @NonNull
    @Override
    public NewsCreatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.creator_news_recycler_item, parent, false);
        return new NewsCreatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCreatorViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.setViewHolder(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}

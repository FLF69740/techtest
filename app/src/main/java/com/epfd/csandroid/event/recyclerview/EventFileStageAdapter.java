package com.epfd.csandroid.event.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Stage;

import java.util.List;

public class EventFileStageAdapter extends RecyclerView.Adapter<EventFileStageHolder> {

    private List<Stage> mStages;

    public EventFileStageAdapter(List<Stage> stages) {
        mStages = stages;
    }

    @NonNull
    @Override
    public EventFileStageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_file_stage_item, parent, false);
        return new EventFileStageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventFileStageHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.setEventFileStageListItem(mStages.get(position));
    }

    @Override
    public int getItemCount() {
        return mStages.size();
    }
}

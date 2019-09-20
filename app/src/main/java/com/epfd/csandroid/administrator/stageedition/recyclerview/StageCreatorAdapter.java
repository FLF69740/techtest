package com.epfd.csandroid.administrator.stageedition.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Stage;

import java.util.List;

public class StageCreatorAdapter extends RecyclerView.Adapter<StageCreatorViewHolder> {

    List<Stage> mStages;

    public StageCreatorAdapter(List<Stage> stages) {
        mStages = stages;
    }

    @NonNull
    @Override
    public StageCreatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.creator_stage_menu_recycler_item,parent, false);
        return new StageCreatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StageCreatorViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.setStageMenuItem(mStages.get(position));
    }

    @Override
    public int getItemCount() {
        return mStages.size();
    }
}

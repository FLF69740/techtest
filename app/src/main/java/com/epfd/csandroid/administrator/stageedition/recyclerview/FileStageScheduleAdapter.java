package com.epfd.csandroid.administrator.stageedition.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.csandroid.R;
import java.util.ArrayList;

public class FileStageScheduleAdapter extends RecyclerView.Adapter<FileStageScheduleViewHolder> {

    private ArrayList<String> mSchedulesList;

    public FileStageScheduleAdapter(ArrayList<String> schedulesList) {
        mSchedulesList = schedulesList;
    }

    @NonNull
    @Override
    public FileStageScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.file_stage_creator_schedule_item,parent, false);
        return new FileStageScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileStageScheduleViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.setStageFileSchedule(mSchedulesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSchedulesList.size();
    }
}

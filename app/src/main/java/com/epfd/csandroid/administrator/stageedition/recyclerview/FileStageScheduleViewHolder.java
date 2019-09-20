package com.epfd.csandroid.administrator.stageedition.recyclerview;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.csandroid.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FileStageScheduleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.file_stage_schedule_recycler_item) TextView mSchedule;

    public FileStageScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setStageFileSchedule(String schedules){
        mSchedule.setText(schedules);
    }

}

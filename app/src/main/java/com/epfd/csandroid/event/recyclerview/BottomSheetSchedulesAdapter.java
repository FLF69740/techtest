package com.epfd.csandroid.event.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;

import java.util.List;

public class BottomSheetSchedulesAdapter extends RecyclerView.Adapter<BottomSheetSchedulesViewHolder> {

    private List<SingleScheduleBottomSheet> mSchedules;
    private final ListenerBottomSheet mCallback;
    private boolean mAdminAct;

    public interface ListenerBottomSheet{
        void activeParticipation(int position);
        void deleteParticipation(int position);
        void adminParticipation(int position);
    }

    public BottomSheetSchedulesAdapter(List<SingleScheduleBottomSheet> schedules, ListenerBottomSheet callback, boolean adminAct) {
        mSchedules = schedules;
        mCallback = callback;
        mAdminAct = adminAct;
    }

    @NonNull
    @Override
    public BottomSheetSchedulesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BottomSheetSchedulesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_schedule_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetSchedulesViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.setSchedulesTable(mSchedules.get(position), mCallback, position, mAdminAct);
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }
}

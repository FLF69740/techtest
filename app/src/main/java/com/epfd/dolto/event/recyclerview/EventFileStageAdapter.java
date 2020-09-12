package com.epfd.dolto.event.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.dolto.R;
import com.epfd.dolto.models.Stage;
import java.util.List;

public class EventFileStageAdapter extends RecyclerView.Adapter<EventFileStageHolder> {

    private List<Stage> mStages;
    private final ListenerEventFileStage mCallback;

    public interface ListenerEventFileStage{
        void goParticipation(String stageName);
    }

    public EventFileStageAdapter(List<Stage> stages, ListenerEventFileStage callback) {
        mStages = stages;
        mCallback = callback;
    }

    @NonNull
    @Override
    public EventFileStageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventFileStageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_file_stage_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventFileStageHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.setEventFileStageListItem(mStages.get(position), mCallback);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mStages.size();
    }
}

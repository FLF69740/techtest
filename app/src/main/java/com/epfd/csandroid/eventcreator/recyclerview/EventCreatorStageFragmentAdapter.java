package com.epfd.csandroid.eventcreator.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Stage;
import java.util.List;

public class EventCreatorStageFragmentAdapter extends RecyclerView.Adapter<EventCreatorStageFragmentViewHolder> {

    private List<Stage> mStageList;
    private final ListenerStageFragment mCallback;

    public interface ListenerStageFragment{
        void deleteStage(int position);
    }

    public EventCreatorStageFragmentAdapter(List<Stage> stageList, ListenerStageFragment listenerStageFragment) {
        mStageList = stageList;
        mCallback = listenerStageFragment;
    }

    @NonNull
    @Override
    public EventCreatorStageFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventCreatorStageFragmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_creator_stage_fragment_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventCreatorStageFragmentViewHolder holder, int position) {
      //  holder.setIsRecyclable(false);
        holder.SetStageFragmentHolder(mStageList.get(position), mCallback);
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
        return mStageList.size();
    }
}

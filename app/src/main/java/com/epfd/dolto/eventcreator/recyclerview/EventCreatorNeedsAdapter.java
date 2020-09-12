package com.epfd.dolto.eventcreator.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.dolto.R;

import java.util.List;

public class EventCreatorNeedsAdapter extends RecyclerView.Adapter<EventCreatorNeedsViewHolder> {

    private List<String> mNeedsString;
    private final ListenerNeedsRecycler mCallback;

    public interface ListenerNeedsRecycler{
        void addNeed(String need);
        void deleteNeed(int position);
    }

    public EventCreatorNeedsAdapter(List<String> needsString, ListenerNeedsRecycler callback) {
        mNeedsString = needsString;
        mCallback = callback;
    }

    @NonNull
    @Override
    public EventCreatorNeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_creator_need_item,parent, false);
        return new EventCreatorNeedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCreatorNeedsViewHolder holder, int position) {
        holder.setNewNeed(mNeedsString.get(position), position, mCallback);
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
        return mNeedsString.size();
    }
}

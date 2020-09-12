package com.epfd.dolto.eventcreator.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.dolto.R;
import com.epfd.dolto.models.Event;

import java.util.List;

public class EventCreatorMenuAdapter extends RecyclerView.Adapter<EventCreatorMenuViewHolder> {

    private List<Event> mEventList;

    public EventCreatorMenuAdapter(List<Event> eventList) {
        mEventList = eventList;
    }

    @NonNull
    @Override
    public EventCreatorMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.creator_event_menu_recycler_item, parent, false);
        return new EventCreatorMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCreatorMenuViewHolder holder, int position) {
        holder.setEventCreatorMenu(mEventList.get(position));
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
        return mEventList.size();
    }
}

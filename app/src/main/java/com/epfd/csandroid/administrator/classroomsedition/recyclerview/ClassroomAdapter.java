package com.epfd.csandroid.administrator.classroomsedition.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomHolder> {

    private List<String> mClassroomsList;

    public ClassroomAdapter(List<String> classroomsList) {
        mClassroomsList = classroomsList;
    }

    @NonNull
    @Override
    public ClassroomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.classroom_recycler_item, parent, false);
        return new ClassroomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomHolder holder, int position) {
        holder.updateWithClassroomAdapter(mClassroomsList.get(position));
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
        return this.mClassroomsList.size();
    }
}

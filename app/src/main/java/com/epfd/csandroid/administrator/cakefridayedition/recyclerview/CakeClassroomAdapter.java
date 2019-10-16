package com.epfd.csandroid.administrator.cakefridayedition.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;

import java.util.List;

public class CakeClassroomAdapter extends RecyclerView.Adapter<CakeClassroomHolder> {

    private List<String> mDateCake;

    public CakeClassroomAdapter(List<String> dateCake) {
        mDateCake = dateCake;
    }

    @NonNull
    @Override
    public CakeClassroomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cake_recycler_item, parent, false);
        return new CakeClassroomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeClassroomHolder holder, int position) {
        holder.mCakeDate.setText(mDateCake.get(position));
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
        return this.mDateCake.size();
    }
}

package com.epfd.dolto.administrator.classroomsedition.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.dolto.R;

import butterknife.ButterKnife;

public class ClassroomHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    public ClassroomHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void updateWithClassroomAdapter(String name){
        TextView textName = mItemView.findViewById(R.id.classrooms_recycler_item_name);
        textName.setText(name);
    }
}

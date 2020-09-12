package com.epfd.dolto.administrator.cakefridayedition.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.dolto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CakeClassroomHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cake_recycler_date) TextView mCakeDate;

    public CakeClassroomHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

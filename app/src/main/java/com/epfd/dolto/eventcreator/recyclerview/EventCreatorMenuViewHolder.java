package com.epfd.dolto.eventcreator.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.dolto.R;
import com.epfd.dolto.models.Event;
import com.epfd.dolto.utils.BitmapStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

class EventCreatorMenuViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    @BindView(R.id.event_menu_recycler_title) TextView mTitle;
    @BindView(R.id.event_menu_recycler_date) TextView mDate;
    @BindView(R.id.event_menu_recycler_panel) ImageView mPanel;
    @BindView(R.id.event_menu_recycler_label) ImageView mLabel;

    EventCreatorMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void setEventCreatorMenu(Event event){
        mTitle.setText(event.getName());
        mDate.setText(event.getDate());
        mPanel.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), event.getPhoto()));
        mLabel.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), event.getLabel()));
    }
}

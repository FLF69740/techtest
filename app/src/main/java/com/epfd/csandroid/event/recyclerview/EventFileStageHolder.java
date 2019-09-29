package com.epfd.csandroid.event.recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.utils.BitmapStorage;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

class EventFileStageHolder extends RecyclerView.ViewHolder {

    private View mItemView;
    private WeakReference<EventFileStageAdapter.ListenerEventFileStage> mWeakReference;

    @BindView(R.id.event_file_stage_item_title) TextView mTitle;
    @BindView(R.id.event_file_stage_item_place) TextView mPlace;
    @BindView(R.id.event_file_stage_item_panel) ImageView mPhoto;
    @BindView(R.id.event_file_stage_item_btn) Button mButton;

    EventFileStageHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    void setEventFileStageListItem(Stage stage, EventFileStageAdapter.ListenerEventFileStage callback){
        mTitle.setText(stage.getTitle());
        mPhoto.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), stage.getPhoto()));

        this.mWeakReference = new WeakReference<>(callback);

        mButton.setOnClickListener(v -> {
            if (mWeakReference.get() != null) mWeakReference.get().goParticipation(stage.getUid());
        });
    }
}

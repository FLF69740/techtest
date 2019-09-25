package com.epfd.csandroid.eventcreator.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.utils.BitmapStorage;
import com.epfd.csandroid.utils.Utils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

class EventCreatorStageFragmentViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;
    private WeakReference<EventCreatorStageFragmentAdapter.ListenerStageFragment> mWeakReference;

    @BindView(R.id.stage_menu_recycler_panel) ImageView mPhoto;
    @BindView(R.id.stage_menu_recycler_title) TextView mTitle;
    @BindView(R.id.stage_menu_recycler_place) TextView mPlaces;
    @BindView(R.id.stage_menu_delete_stage) ImageView mDeleteBtn;

    public EventCreatorStageFragmentViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void SetStageFragmentHolder(Stage stage, EventCreatorStageFragmentAdapter.ListenerStageFragment callback){
        mPhoto.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), stage.getPhoto()));
        mTitle.setText(stage.getTitle());
        int places = Utils.getSequenceNumberIntoAString(stage.getSchedule(), ',') * stage.getPeople();
        mPlaces.setText(String.valueOf(places));

        this.mWeakReference = new WeakReference<>(callback);

        mDeleteBtn.setOnClickListener(v -> {
            if (mWeakReference.get() != null) mWeakReference.get().deleteStage(getAdapterPosition());
        });
    }


}

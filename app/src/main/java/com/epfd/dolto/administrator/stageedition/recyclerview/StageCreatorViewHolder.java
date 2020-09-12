package com.epfd.dolto.administrator.stageedition.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.dolto.R;
import com.epfd.dolto.models.Stage;
import com.epfd.dolto.utils.BitmapStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StageCreatorViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    @BindView(R.id.stage_menu_recycler_panel) ImageView mPhoto;
    @BindView(R.id.stage_menu_recycler_title) TextView mName;
    @BindView(R.id.stage_menu_recycler_place) TextView mPlaces;

    public StageCreatorViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void setStageMenuItem(Stage stage){
        mPhoto.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), stage.getPhoto()));
        mName.setText(stage.getTitle());

        char someChar = ',';
        int count = 0;

        for (int i = 0; i < stage.getSchedule().length(); i++) {
            if (stage.getSchedule().charAt(i) == someChar) {
                count++;
            }
        }

        int places = count * stage.getPeople();
        mPlaces.setText(String.valueOf(places));
    }
}

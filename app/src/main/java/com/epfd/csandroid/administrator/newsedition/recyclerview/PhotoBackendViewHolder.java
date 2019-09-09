package com.epfd.csandroid.administrator.newsedition.recyclerview;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epfd.csandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoBackendViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.photo_backend_image) ImageView mPhoto;
    private View mItemView;
    public PhotoBackendViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void setPhotoBackEnd(Bitmap uri){
        Glide.with(mItemView)
                .load(uri)
                .apply(RequestOptions.centerCropTransform())
                .into(mPhoto);
    }
}

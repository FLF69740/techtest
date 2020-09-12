package com.epfd.dolto.administrator.newsedition.recyclerview;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.dolto.R;
import com.epfd.dolto.utils.BitmapStorage;

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

    public void setPhotoBackEnd(String uri){
       if (BitmapStorage.isFileExist(mItemView.getContext(), uri)){
           mPhoto.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), uri));
       }
    }
}

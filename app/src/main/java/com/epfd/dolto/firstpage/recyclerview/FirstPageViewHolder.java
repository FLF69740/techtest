package com.epfd.dolto.firstpage.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.dolto.R;
import com.epfd.dolto.models.News;
import com.epfd.dolto.utils.BitmapStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstPageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.news_recycler_title) TextView mTitle;
    @BindView(R.id.new_recycler_date) TextView mDate;
    @BindView(R.id.news_recycler_body) TextView mBody;
    @BindView(R.id.news_recycler_imageview) ImageView mImage;

    private View mItemView;

    public FirstPageViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void setViewHolder(News news){
        mTitle.setText(news.getTitle());
        mDate.setText(news.getPublication());
        mBody.setText(news.getBody());

        switch (news.getPhoto()) {

            case "ic_cake_friday_photo" :
                mImage.setBackgroundResource(R.drawable.ic_cake_friday_photo);
                break;
            default:
                mImage.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), news.getPhoto()));
                break;
        }
    }
}

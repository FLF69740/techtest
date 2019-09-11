package com.epfd.csandroid.administrator.newsedition.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epfd.csandroid.R;
import com.epfd.csandroid.models.News;
import com.epfd.csandroid.utils.BitmapStorage;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsCreatorViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    @BindView(R.id.news_creator_edition_event_date) TextView mDate;
    @BindView(R.id.news_creator_edition_publication_date) TextView mPublication;
    @BindView(R.id.news_creator_edition_title) TextView mTitle;
    @BindView(R.id.news_creator_edition_body) TextView mBody;
    @BindView(R.id.news_creator_edition_photo) ImageView mPhoto;

    public NewsCreatorViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void setViewHolder(News news){
        mDate.setText(news.getDate());
        mPublication.setText(news.getPublication());
        mTitle.setText(news.getTitle());
        mBody.setText(news.getBody());

        if (BitmapStorage.isFileExist(mItemView.getContext(), news.getPhoto())){
            mPhoto.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), news.getPhoto()));
        }else {
            Glide.with(mItemView.getContext())
                    .load(R.drawable.ic_logo_pos2)
                    .apply(RequestOptions.fitCenterTransform())
                    .into(mPhoto);
        }
    }

}

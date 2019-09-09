package com.epfd.csandroid.administrator.newsedition.recyclerview;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epfd.csandroid.R;
import com.epfd.csandroid.models.News;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(news.getPhoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mItemView.getContext())
                        .load(uri)
                        .apply(RequestOptions.fitCenterTransform())
                        .into(mPhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Glide.with(mItemView.getContext())
                        .load(R.drawable.ic_logo_pos2)
                        .apply(RequestOptions.fitCenterTransform())
                        .into(mPhoto);
            }
        });

    }

}

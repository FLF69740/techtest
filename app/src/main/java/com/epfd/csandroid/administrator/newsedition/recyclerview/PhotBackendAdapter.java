package com.epfd.csandroid.administrator.newsedition.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;

import java.util.List;

public class PhotBackendAdapter extends RecyclerView.Adapter<PhotoBackendViewHolder> {

    private List<String> mUriListString;

    public PhotBackendAdapter(List<String> uriListString) {
        mUriListString = uriListString;
    }

    @NonNull
    @Override
    public PhotoBackendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photo_backend_item, parent, false);
        return new PhotoBackendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoBackendViewHolder holder, int position) {
        holder.setPhotoBackEnd(mUriListString.get(position));
    }

    @Override
    public int getItemCount() {
        return mUriListString.size();
    }
}

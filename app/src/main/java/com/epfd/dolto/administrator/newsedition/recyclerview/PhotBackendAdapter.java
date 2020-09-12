package com.epfd.dolto.administrator.newsedition.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.dolto.R;
import java.util.List;

public class PhotBackendAdapter extends RecyclerView.Adapter<PhotoBackendViewHolder> {

    private List<String> mBitmaps;

    public PhotBackendAdapter(List<String> bitmapList) {
        mBitmaps = bitmapList;
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
        holder.setPhotoBackEnd(mBitmaps.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mBitmaps.size();
    }
}

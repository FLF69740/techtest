package com.epfd.csandroid.formulary.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Kid;

import java.util.List;

public class FormularyAdapter extends RecyclerView.Adapter<FormularyViewHolder> {

    private List<Kid> mKidList;

    public interface Listener{
        void onClickDeleteButton(int position);
        void updateName(String name, int position);
        void updateForname(String forname, int position);
        void updateClassroom(String classroom, int position);
        void updateGender(String gender, int position);
    }

    private final Listener mCallback;

    public FormularyAdapter(List<Kid> kidList, Listener callback) {
        mKidList = kidList;
        mCallback = callback;
    }

    @NonNull
    @Override
    public FormularyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.formulary_recycler_item, parent, false);
        return new FormularyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormularyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.updateWithAdapterInformation(this.mKidList.get(position), position, mCallback);
    }

    @Override
    public int getItemCount() {
        return this.mKidList.size();
    }
}

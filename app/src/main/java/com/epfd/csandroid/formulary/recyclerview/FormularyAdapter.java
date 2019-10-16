package com.epfd.csandroid.formulary.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.Kid;
import com.epfd.csandroid.utils.Utils;

import java.util.List;

public class FormularyAdapter extends RecyclerView.Adapter<FormularyViewHolder> {

    private List<Kid> mKidList;
    private List<String> mClassroomsList;
    private ArrayAdapter<String> mArrayAdapter;

    public interface Listener{
        void onClickDeleteButton(int position);
        void updateName(String name, int position);
        void updateForname(String forname, int position);
        void updateClassroom(String classroom, int position);
        void updateGender(String gender, int position);
    }

    private final Listener mCallback;

    public FormularyAdapter(Context context, List<Kid> kidList, List<String> classroomsList, Listener callback) {
        mKidList = kidList;
        mCallback = callback;
        mClassroomsList = classroomsList;

        mArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, mClassroomsList);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        holder.mName.setText(this.mKidList.get(position).getNom());
        holder.mForname.setText(this.mKidList.get(position).getPrenom());
        holder.mClasseSpinner.setAdapter(mArrayAdapter);
        for (int i = 0 ; i < mClassroomsList.size() ; i++){
            if (mClassroomsList.get(i).equals(mKidList.get(position).getClasse())){
                holder.mClasseSpinner.setSelection(i);
            }
        }
        switch (this.mKidList.get(position).getGenre()){
            case Utils.BOY : holder.mBoyLogo.setAlpha(1.0f); break;
            case Utils.GIRL : holder.mGirlLogo.setAlpha(1.0f); break;
        }
        if (position == 0){
            holder.mImageViewDelete.setVisibility(View.INVISIBLE);
        }
        holder.updateWithAdapterInformation(this.mKidList.get(position), position, mCallback);
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
        return this.mKidList.size();
    }
}

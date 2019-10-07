package com.epfd.csandroid.eventcreator.recyclerview;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventCreatorNeedsViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;
    private WeakReference<EventCreatorNeedsAdapter.ListenerNeedsRecycler> mWeakReference;

    @BindView(R.id.event_creator_need_textview_item) TextView mTextView;
    @BindView(R.id.event_creator_need_recycler_item) EditText mEditText;
    @BindView(R.id.event_creator_need_validation_item) ImageView mValidation;
    @BindView(R.id.event_creator_need_delete_item) ImageView mDelete;

    public EventCreatorNeedsViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void setNewNeed(String need, int position, EventCreatorNeedsAdapter.ListenerNeedsRecycler callback){
        this.mWeakReference = new WeakReference<>(callback);
        if (need.equals("")){
            mDelete.setVisibility(View.INVISIBLE);
            mValidation.setVisibility(View.VISIBLE);
            mEditText.setText(need);
            mTextView.setVisibility(View.INVISIBLE);

        }else {
            mTextView.setText(need);
            mTextView.setVisibility(View.VISIBLE);
            mEditText.setVisibility(View.INVISIBLE);
            mValidation.setVisibility(View.INVISIBLE);
            mDelete.setVisibility(View.VISIBLE);
        }

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeakReference.get() != null) mWeakReference.get().deleteNeed(position);

            }
        });

        mValidation.setOnClickListener(v -> {
            if (!mEditText.getText().toString().equals("")){
                if (mWeakReference.get() != null) mWeakReference.get().addNeed(mEditText.getText().toString());
                mTextView.setText(mEditText.getText().toString());
                mTextView.setVisibility(View.VISIBLE);
                mDelete.setVisibility(View.VISIBLE);
                mEditText.setVisibility(View.INVISIBLE);
                mValidation.setVisibility(View.INVISIBLE);
            }
        });

    }


}

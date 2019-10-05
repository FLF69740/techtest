package com.epfd.csandroid.event.recyclerview;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;
import com.epfd.csandroid.utils.Utils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class BottomSheetSchedulesViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;
    private int mPosition;
    private WeakReference<BottomSheetSchedulesAdapter.ListenerBottomSheet> mWeakReference;

    @BindView(R.id.modal_schedule_recycler_item_schedule) TextView mSchedule;
    @BindView(R.id.modal_schedule_recycler_item_people) TextView mPeople;
    @BindView(R.id.modal_schedule_recycler_item_add_participation) ImageView mModalAddBtn;
    @BindView(R.id.modal_schedule_recycler_item_delete_participation) ImageView mModalDeleteBtn;

    BottomSheetSchedulesViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    void setSchedulesTable(SingleScheduleBottomSheet scheduleDatas, BottomSheetSchedulesAdapter.ListenerBottomSheet callback, int position){
        this.mPosition = position;
        this.mWeakReference = new WeakReference<>(callback);
        mSchedule.setText(scheduleDatas.getSchedule());
        StringBuilder peopleListString = new StringBuilder();
        for (String personae : scheduleDatas.getParticipantList()){
            if (personae.equals(Utils.EMPTY)){
                peopleListString.append("<font color='#FFFFFF'>" + Utils.EMPTY +"</font>").append("<br>");
            }else {
                peopleListString.append(personae).append("<br>");
            }
        }
        mPeople.setText(Html.fromHtml(peopleListString.toString()));

        if (!scheduleDatas.isActifReservation() || !peopleListString.toString().contains(Utils.EMPTY)){
            mModalAddBtn.setVisibility(View.GONE);
            mModalDeleteBtn.setVisibility(View.GONE);
        }else if (!scheduleDatas.isNotRegistered()){
            mModalAddBtn.setVisibility(View.GONE);
            mModalDeleteBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.modal_schedule_recycler_item_add_participation) void addAModalParticipant(){
        mModalAddBtn.setVisibility(View.GONE);
        mModalDeleteBtn.setVisibility(View.VISIBLE);
        if (mWeakReference.get() != null) mWeakReference.get().activeParticipation(mPosition);
    }

    @OnClick(R.id.modal_schedule_recycler_item_delete_participation) void deleteAModalParticipant(){
        mModalAddBtn.setVisibility(View.VISIBLE);
        mModalDeleteBtn.setVisibility(View.GONE);
        if (mWeakReference.get() != null) mWeakReference.get().deleteParticipation(mPosition);
    }



}

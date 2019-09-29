package com.epfd.csandroid.event.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;

import butterknife.BindView;
import butterknife.ButterKnife;

class BottomSheetSchedulesViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    @BindView(R.id.modal_schedule_recycler_item_schedule) TextView mTitle;
    @BindView(R.id.modal_schedule_recycler_item_people) TextView mPeople;

    BottomSheetSchedulesViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    void setSchedulesTable(SingleScheduleBottomSheet scheduleDatas){
        mTitle.setText(scheduleDatas.getSchedule());
        StringBuilder peopleListString = new StringBuilder();
        for (String personae : scheduleDatas.getParticipantList()){
            peopleListString.append(personae).append("\n");
        }
        mPeople.setText(peopleListString.toString());
    }



}

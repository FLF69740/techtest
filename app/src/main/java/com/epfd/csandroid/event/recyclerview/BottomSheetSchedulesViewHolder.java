package com.epfd.csandroid.event.recyclerview;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    @BindView(R.id.modal_schedule_recycler_item_other_participation) ImageView mModalOtherBtn;

    BottomSheetSchedulesViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    void setSchedulesTable(SingleScheduleBottomSheet scheduleDatas, BottomSheetSchedulesAdapter.ListenerBottomSheet callback, int position, boolean adminAct){
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

        if (!adminAct){
            mPeople.setClickable(false);
        }

        if (!scheduleDatas.isActifReservation() && scheduleDatas.isNotRegistered()) {
            mModalOtherBtn.setVisibility(View.VISIBLE);
            mModalAddBtn.setVisibility(View.GONE);
            mModalDeleteBtn.setVisibility(View.GONE);
        }else if (!peopleListString.toString().contains(Utils.EMPTY) && scheduleDatas.isNotRegistered()){
            mModalAddBtn.setVisibility(View.GONE);
            mModalDeleteBtn.setVisibility(View.GONE);
            mModalOtherBtn.setVisibility(View.GONE);
        }else if (!scheduleDatas.isNotRegistered()) {
            mModalAddBtn.setVisibility(View.GONE);
            mModalDeleteBtn.setVisibility(View.VISIBLE);
            mModalOtherBtn.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.modal_schedule_recycler_item_add_participation) void addAModalParticipant(){
        mModalAddBtn.setVisibility(View.GONE);
        mModalOtherBtn.setVisibility(View.GONE);
        mModalDeleteBtn.setVisibility(View.VISIBLE);
        if (mWeakReference.get() != null) mWeakReference.get().activeParticipation(mPosition);
    }

    @OnClick(R.id.modal_schedule_recycler_item_delete_participation) void deleteAModalParticipant(){
        mModalAddBtn.setVisibility(View.VISIBLE);
        mModalDeleteBtn.setVisibility(View.GONE);
        mModalOtherBtn.setVisibility(View.GONE);
        if (mWeakReference.get() != null) mWeakReference.get().deleteParticipation(mPosition);
    }

    @OnClick(R.id.modal_schedule_recycler_item_people) void sendAdminAct(){
        if (mWeakReference.get() != null) mWeakReference.get().adminParticipation(mPosition);
    }

    @OnClick(R.id.modal_schedule_recycler_item_other_participation) void otherPlanningReservedInfo(){
        Toast.makeText(mItemView.getContext(), R.string.event_file_other_participation, Toast.LENGTH_LONG).show();
    }

}

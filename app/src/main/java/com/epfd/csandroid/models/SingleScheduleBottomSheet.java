package com.epfd.csandroid.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.epfd.csandroid.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleScheduleBottomSheet implements Parcelable {

    private List<String> mParticipantList;
    private String mSchedule;
    private String mRegistrationId;
    private boolean mActifReservation; // boolean : false if participant is registered into an other stage
    private boolean mNotRegistered; // boolean : false if participant is registered into the stage showed

    public SingleScheduleBottomSheet(String schedule, String registrationId) {
        mParticipantList = new ArrayList<>();
        mSchedule = schedule;
        mActifReservation = true;
        mNotRegistered = true;
        mRegistrationId = registrationId;
    }

    public List<String> getParticipantList() {
        return mParticipantList;
    }

    public void setParticipantList(String participantDataLine) {
        if (!participantDataLine.contains(Utils.PARTICIPANT_SEPARATOR)) {
            mParticipantList.add(participantDataLine);
        } else {
            mParticipantList = new ArrayList<>(Arrays.asList(participantDataLine.split(Utils.PARTICIPANT_SEPARATOR)));
        }

    }

    public String getRegistrationId() {
        return mRegistrationId;
    }

    public void setRegistrationId(String registrationId) {
        mRegistrationId = registrationId;
    }

    public String getSchedule() {
        return mSchedule;
    }

    public void setSchedule(String schedule) {
        mSchedule = schedule;
    }

    public boolean isActifReservation() {
        return mActifReservation;
    }

    public void setActifReservation(boolean actifReservation) {
        mActifReservation = actifReservation;
    }

    public boolean isNotRegistered() {
        return mNotRegistered;
    }

    public void setNotRegistered(boolean notRegistered) {
        mNotRegistered = notRegistered;
    }

    protected SingleScheduleBottomSheet(Parcel in) {
        if (in.readByte() == 0x01) {
            mParticipantList = new ArrayList<String>();
            in.readList(mParticipantList, String.class.getClassLoader());
        } else {
            mParticipantList = null;
        }
        mSchedule = in.readString();
        mRegistrationId = in.readString();
        mActifReservation = in.readByte() != 0x00;
        mNotRegistered = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mParticipantList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mParticipantList);
        }
        dest.writeString(mSchedule);
        dest.writeString(mRegistrationId);
        dest.writeByte((byte) (mActifReservation ? 0x01 : 0x00));
        dest.writeByte((byte) (mNotRegistered ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SingleScheduleBottomSheet> CREATOR = new Parcelable.Creator<SingleScheduleBottomSheet>() {
        @Override
        public SingleScheduleBottomSheet createFromParcel(Parcel in) {
            return new SingleScheduleBottomSheet(in);
        }

        @Override
        public SingleScheduleBottomSheet[] newArray(int size) {
            return new SingleScheduleBottomSheet[size];
        }
    };
}

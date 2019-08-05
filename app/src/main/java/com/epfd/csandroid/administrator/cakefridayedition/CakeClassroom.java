package com.epfd.csandroid.administrator.cakefridayedition;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CakeClassroom implements Parcelable {

    private String mClassroomCake;
    private List<String> mDatesPlannification;

    public CakeClassroom() {
    }

    public String getClassroomCake() {
        return mClassroomCake;
    }

    public void setClassroomCake(String classroomCake) {
        mClassroomCake = classroomCake;
    }

    public List<String> getDatesPlannification() {
        return mDatesPlannification;
    }

    public void setDatesPlannification(List<String> datesPlannification) {
        mDatesPlannification = datesPlannification;
    }


    protected CakeClassroom(Parcel in) {
        mClassroomCake = in.readString();
        if (in.readByte() == 0x01) {
            mDatesPlannification = new ArrayList<String>();
            in.readList(mDatesPlannification, String.class.getClassLoader());
        } else {
            mDatesPlannification = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mClassroomCake);
        if (mDatesPlannification == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mDatesPlannification);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CakeClassroom> CREATOR = new Parcelable.Creator<CakeClassroom>() {
        @Override
        public CakeClassroom createFromParcel(Parcel in) {
            return new CakeClassroom(in);
        }

        @Override
        public CakeClassroom[] newArray(int size) {
            return new CakeClassroom[size];
        }
    };
}

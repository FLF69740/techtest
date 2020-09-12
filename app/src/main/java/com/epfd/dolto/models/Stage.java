package com.epfd.dolto.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Stage implements Parcelable {

    private String mUid;
    private String mTitle;
    private String mPhoto;
    private String mSchedule;
    private int mPeople;

    public Stage() {}

    public Stage(String uid, String title, String photo, String schedule, int people) {
        mUid = uid;
        mTitle = title;
        mPhoto = photo;
        mSchedule = schedule;
        mPeople = people;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getSchedule() {
        return mSchedule;
    }

    public void setSchedule(String schedule) {
        mSchedule = schedule;
    }

    public int getPeople() {
        return mPeople;
    }

    public void setPeople(int people) {
        mPeople = people;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    protected Stage(Parcel in) {
        mUid = in.readString();
        mTitle = in.readString();
        mPhoto = in.readString();
        mSchedule = in.readString();
        mPeople = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUid);
        dest.writeString(mTitle);
        dest.writeString(mPhoto);
        dest.writeString(mSchedule);
        dest.writeInt(mPeople);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Stage> CREATOR = new Parcelable.Creator<Stage>() {
        @Override
        public Stage createFromParcel(Parcel in) {
            return new Stage(in);
        }

        @Override
        public Stage[] newArray(int size) {
            return new Stage[size];
        }
    };
}

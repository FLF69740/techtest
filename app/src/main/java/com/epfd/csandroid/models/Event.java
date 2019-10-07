package com.epfd.csandroid.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private String mUid;
    private String mName;
    private String mDate;
    private String mDescription;
    private String mPhoto;
    private String mLabel;
    private String mStages;
    private String mNeeds;
    private boolean mAffichage;

    public Event() {}

    public Event(String uid, String name, String date, String description, String photo, String label) {
        mUid = uid;
        mName = name;
        mDate = date;
        mDescription = description;
        mPhoto = photo;
        mLabel = label;
        mStages = "";
        mNeeds = "";
        mAffichage = false;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getStages() {
        return mStages;
    }

    public void setStages(String stages) {
        mStages = stages;
    }

    public boolean isAffichage() {
        return mAffichage;
    }

    public void setAffichage(boolean affichage) {
        mAffichage = affichage;
    }

    public String getNeeds() {
        return mNeeds;
    }

    public void setNeeds(String needs) {
        mNeeds = needs;
    }

    protected Event(Parcel in) {
        mUid = in.readString();
        mName = in.readString();
        mDate = in.readString();
        mDescription = in.readString();
        mPhoto = in.readString();
        mLabel = in.readString();
        mStages = in.readString();
        mNeeds = in.readString();
        mAffichage = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUid);
        dest.writeString(mName);
        dest.writeString(mDate);
        dest.writeString(mDescription);
        dest.writeString(mPhoto);
        dest.writeString(mLabel);
        dest.writeString(mStages);
        dest.writeString(mNeeds);
        dest.writeByte((byte) (mAffichage ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
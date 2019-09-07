package com.epfd.csandroid.models;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {

    private String mTitle;
    private String mDate;
    private Boolean mNotification;
    private String mPublication;
    private String mPhoto;
    private String mBody;
    private int mTag;
    private String mTarget;

    public News() {}

    public News(String title, String date, Boolean notification, String publication, String photo, String body, String target, int tag) {
        mTitle = title;
        mDate = date;
        mNotification = notification;
        mPublication = publication;
        mPhoto = photo;
        mBody = body;
        mTarget = target;
        mTag = tag;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Boolean getNotification() {
        return mNotification;
    }

    public void setNotification(Boolean notification) {
        mNotification = notification;
    }

    public String getPublication() {
        return mPublication;
    }

    public void setPublication(String publication) {
        mPublication = publication;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getTarget() {
        return mTarget;
    }

    public void setTarget(String target) {
        mTarget = target;
    }

    public int getTag() {
        return mTag;
    }

    public void setTag(int tag) {
        mTag = tag;
    }


    protected News(Parcel in) {
        mTitle = in.readString();
        mDate = in.readString();
        byte mNotificationVal = in.readByte();
        mNotification = mNotificationVal == 0x02 ? null : mNotificationVal != 0x00;
        mPublication = in.readString();
        mPhoto = in.readString();
        mBody = in.readString();
        mTag = in.readInt();
        mTarget = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDate);
        if (mNotification == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (mNotification ? 0x01 : 0x00));
        }
        dest.writeString(mPublication);
        dest.writeString(mPhoto);
        dest.writeString(mBody);
        dest.writeInt(mTag);
        dest.writeString(mTarget);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
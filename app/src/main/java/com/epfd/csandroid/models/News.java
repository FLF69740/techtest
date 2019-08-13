package com.epfd.csandroid.models;

public class News {

    private String mTitle;
    private String mDate;
    private Boolean mNotification;
    private String mPublication;
    private String mPhoto;
    private String mBody;

    public News() {}

    public News(String title, String date, Boolean notification, String publication, String photo, String body) {
        mTitle = title;
        mDate = date;
        mNotification = notification;
        mPublication = publication;
        mPhoto = photo;
        mBody = body;
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
}

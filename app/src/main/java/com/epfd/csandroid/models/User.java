package com.epfd.csandroid.models;

import javax.annotation.Nullable;

public class User {

    private String mUid;
    private String mUsername;
    @Nullable private String mUrlPicture;
    private String mStringKidNameList;
    private String mStringClasseRoomList;
    private String mStringGenderList;

    public User() {
    }

    public User(String uid, String username, @Nullable String urlPicture, String stringKidNameList, String stringClassRoomList, String stringGenderList) {
        mUid = uid;
        mUsername = username;
        mUrlPicture = urlPicture;
        mStringKidNameList = stringKidNameList;
        mStringClasseRoomList = stringClassRoomList;
        mStringGenderList = stringGenderList;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    @Nullable
    public String getUrlPicture() {
        return mUrlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        mUrlPicture = urlPicture;
    }

    public String getStringKidNameList() {
        return mStringKidNameList;
    }

    public void setStringKidNameList(String stringKidNameList) {
        mStringKidNameList = stringKidNameList;
    }

    public String getStringClasseNameList() {
        return mStringClasseRoomList;
    }

    public void setStringClasseNameList(String stringClasseNameList) {
        mStringClasseRoomList = stringClasseNameList;
    }

    public String getStringGenderList() {
        return mStringGenderList;
    }

    public void setStringGenderList(String stringGenderList) {
        mStringGenderList = stringGenderList;
    }
}

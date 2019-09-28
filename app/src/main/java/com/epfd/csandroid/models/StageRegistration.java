package com.epfd.csandroid.models;

public class StageRegistration {

    private String mParticipant;
    private String mUid;
    private String mUrlParticipant;

    public StageRegistration() {}

    public StageRegistration(String participant, String uid, String urlParticipant) {
        mParticipant = participant;
        mUid = uid;
        mUrlParticipant = urlParticipant;
    }

    public String getParticipant() {
        return mParticipant;
    }

    public void setParticipant(String participant) {
        mParticipant = participant;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getUrlParticipant() {
        return mUrlParticipant;
    }

    public void setUrlParticipant(String urlParticipant) {
        mUrlParticipant = urlParticipant;
    }
}

package com.epfd.dolto.models;

public class StageRegistration {

    private String mParticipant;
    private String mUid;
    private int mPeople;

    public StageRegistration() {}

    public StageRegistration(String participant, String uid, int people) {
        mParticipant = participant;
        mUid = uid;
        mPeople = people;
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

    public int getPeople() {
        return mPeople;
    }

    public void setPeople(int people) {
        mPeople = people;
    }
}

package com.epfd.dolto.models;

public class CakeFriday {

    private String mDateListString;
    private String mClassroomsListString;

    public CakeFriday(String dateListString, String classroomsListString) {
        mDateListString = dateListString;
        mClassroomsListString = classroomsListString;
    }

    public String getDateListString() {
        return mDateListString;
    }

    public void setDateListString(String dateListString) {
        mDateListString = dateListString;
    }

    public String getClassroomsListString() {
        return mClassroomsListString;
    }

    public void setClassroomsListString(String classroomsListString) {
        mClassroomsListString = classroomsListString;
    }
}

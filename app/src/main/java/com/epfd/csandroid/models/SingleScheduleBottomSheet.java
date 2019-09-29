package com.epfd.csandroid.models;

import com.epfd.csandroid.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleScheduleBottomSheet {

    private List<String> mParticipantList;
    private String mSchedule;

    public SingleScheduleBottomSheet(String schedule) {
        mParticipantList = new ArrayList<>();
        mSchedule = schedule;
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

    public String getSchedule() {
        return mSchedule;
    }

    public void setSchedule(String schedule) {
        mSchedule = schedule;
    }
}

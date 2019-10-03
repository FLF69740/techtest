package com.epfd.csandroid.event;

import com.epfd.csandroid.models.ModalUserTimeTable;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.models.StageRegistration;
import com.epfd.csandroid.utils.Utils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventBusiness {

    //SCHEDULE START EXTRACTION
    private static DateTime getStartSchedule(String scheduleString){
        List<String> scheduleCut = Arrays.asList(scheduleString.split(" / "));
        List<String> dateCut = Arrays.asList(scheduleCut.get(0).split(":"));
        DateTime dateTime = new DateTime();
        dateTime = dateTime.hourOfDay().setCopy(Integer.valueOf(dateCut.get(0)));
        dateTime = dateTime.minuteOfHour().setCopy(Integer.valueOf(dateCut.get(1)));
        return dateTime;
    }

    public static List<DateTime> loadSchedulesStart(List<Integer> participantReservationId, Stage stage){
        List<DateTime> resultList = new ArrayList<>();
        List<String> stageScheduleString = Arrays.asList(stage.getSchedule().split(","));
        for (int placeSchedule : participantReservationId){
            resultList.add(getStartSchedule(stageScheduleString.get(placeSchedule)));
        }
        return resultList;
    }

    //SCHEDULE END EXTRACTION
    private static DateTime getEndSchedule(String scheduleString){
        List<String> scheduleCut = Arrays.asList(scheduleString.split(" / "));
        List<String> dateCut = Arrays.asList(scheduleCut.get(1).split(":"));
        DateTime dateTime = new DateTime();
        dateTime = dateTime.hourOfDay().setCopy(Integer.valueOf(dateCut.get(0)));
        dateTime = dateTime.minuteOfHour().setCopy(Integer.valueOf(dateCut.get(1)));
        return dateTime;
    }

    public static List<DateTime> loadSchedulesEnd(List<Integer> participantReservationId, Stage stage){
        List<DateTime> resultList = new ArrayList<>();
        List<String> stageScheduleString = Arrays.asList(stage.getSchedule().split(","));
        for (int placeSchedule : participantReservationId){
            resultList.add(getEndSchedule(stageScheduleString.get(placeSchedule)));
        }
        return resultList;
    }

    //UPDATE TIMETABLE
    public static void getTimeTableUpdated(ModalUserTimeTable timeTable, String username, StageRegistration stageRegistration, Stage stage){
        List<Integer> scheduleNumberDetection = new ArrayList<>();
        List<String> stageRegistrationParticipantList = Arrays.asList(stageRegistration.getParticipant().split(","));
        for (int i = 0; i < stageRegistrationParticipantList.size(); i++) {
            if (stageRegistrationParticipantList.get(i).contains(username)) scheduleNumberDetection.add(i);
        }

        List<DateTime> lastScheduleRegistration = timeTable.getListReservationStart();
        lastScheduleRegistration.addAll(EventBusiness.loadSchedulesStart(scheduleNumberDetection, stage));
        timeTable.setListReservationStart(lastScheduleRegistration);

        lastScheduleRegistration= timeTable.getListReservationEnd();
        lastScheduleRegistration.addAll(EventBusiness.loadSchedulesEnd(scheduleNumberDetection, stage));
        timeTable.setListReservationEnd(lastScheduleRegistration);
    }

    //COMPARE SCHEDULES BETWEEN TIMETABLE AND STAGE PLANNING AND UPDATE PLANNING
    public static void compareTimeTableAndStagePlanning(List<SingleScheduleBottomSheet> planning, ModalUserTimeTable timeTable){

        DateTime modalStart = new DateTime();
        DateTime modalEnd = new DateTime();

        for (int i = 0 ; i < timeTable.getListReservationStart().size() ; i++){

            for (int j = 0 ; j < planning.size() ; j++) {
                modalStart = getStartSchedule(planning.get(j).getSchedule());
                modalEnd = getEndSchedule(planning.get(j).getSchedule());

                if (modalStart.getMinuteOfDay() >= timeTable.getListReservationStart().get(i).getMinuteOfDay() &&
                        modalStart.getMinuteOfDay() < timeTable.getListReservationEnd().get(i).getMinuteOfDay()) {
                    planning.get(j).setActifReservation(false);
                }else if (modalEnd.getMinuteOfDay() > timeTable.getListReservationStart().get(i).getMinuteOfDay() &&
                        modalEnd.getMinuteOfDay() <= timeTable.getListReservationEnd().get(i).getMinuteOfDay()){
                    planning.get(j).setActifReservation(false);
                }
            }
        }
    }

    //ADD A PARTICIPANT INTO A PLANNING
    public static void addParticipantIntoPlanning(List<SingleScheduleBottomSheet> planning, String name, int position){
        List<String> participantList = planning.get(position).getParticipantList();
        boolean inscription = false;
        for (int i = 0 ; i < participantList.size(); i++){
            if (participantList.get(i).equals(Utils.EMPTY)&& !inscription){
                participantList.set(i, name);
                inscription = true;
            }
        }
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (String participantName : participantList){
            result.append(prefix);
            prefix = Utils.PARTICIPANT_SEPARATOR;
            result.append(participantName);
        }

        planning.get(position).setParticipantList(result.toString());
        planning.get(position).setNotRegistered(false);
    }

    //ADD A PARTICIPANT INTO A PLANNING
    public static void deleteParticipantIntoPlanning(List<SingleScheduleBottomSheet> planning, String name, int position){
        List<String> participantList = planning.get(position).getParticipantList();
        boolean inscription = true;
        for (int i = 0 ; i < participantList.size(); i++){
            if (participantList.get(i).equals(name)&& inscription){
                participantList.set(i, Utils.EMPTY);
                inscription = false;
            }
        }
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (String participantName : participantList){
            result.append(prefix);
            prefix = Utils.PARTICIPANT_SEPARATOR;
            result.append(participantName);
        }

        planning.get(position).setParticipantList(result.toString());
        planning.get(position).setNotRegistered(true);
    }

    //PARTICIPANT LIST TO STRING
    public static String listPlanningToString(List<SingleScheduleBottomSheet> planning){
        StringBuilder builder = new StringBuilder();
        for (SingleScheduleBottomSheet sheet : planning){
            String prefix = "";
            for (String participantName : sheet.getParticipantList()){
                builder.append(prefix);
                prefix = Utils.PARTICIPANT_SEPARATOR;
                builder.append(participantName);
            }
            builder.append(",");
        }
        return builder.toString();
    }

}

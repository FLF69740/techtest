package com.epfd.csandroid;

import com.epfd.csandroid.event.EventBusiness;
import com.epfd.csandroid.models.ModalUserTimeTable;
import com.epfd.csandroid.models.SingleScheduleBottomSheet;
import com.epfd.csandroid.models.Stage;
import com.epfd.csandroid.models.StageRegistration;
import com.epfd.csandroid.utils.Utils;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EventUnitTest {

    private static final String STAGE_SCHEDULE_1 = "12:30 / 13:30,13:30 / 14:30,14:30 / 15:30,";
    private static final String STAGE_SCHEDULE_1Bis = "12:45 / 13:45,13:45 / 14:45,14:45 / 15:45,";
    private static final String STAGE_SCHEDULE_2 = "12:30 / 13:30,13:30 / 14:30,14:30 / 15:30,";
    private static final String MY_NAME = "MY_NAME";

    private Stage getStage1(){
        return new Stage("STAGE12345", "STAGE A", Utils.EMPTY, STAGE_SCHEDULE_1, 2);
    }

    private Stage getStage1Bis(){
        return new Stage("STAGE12345", "STAGE A", Utils.EMPTY, STAGE_SCHEDULE_1Bis, 2);
    }

    private Stage getStage2(){
        return new Stage("STAGE12346", "STAGE B", Utils.EMPTY, STAGE_SCHEDULE_2, 2);
    }

    private StageRegistration getStageRegistration1(){
        return new StageRegistration("MY_NAME!EMPTY,EMPTY!EMPTY,MY_NAME!EMPTY,", "EVENT12345STAGE12345",2);
    }

    private StageRegistration getStageRegistration2(){
        return new StageRegistration("EMPTY!EMPTY,MY_NAME!EMPTY,EMPTY!EMPTY,", "EVENT12345STAGE12345",2);
    }

    private List<SingleScheduleBottomSheet> getPlanning(Stage stage){
        List<String> scheduleStageString = Arrays.asList(stage.getSchedule().split(","));
        List<SingleScheduleBottomSheet> planning = new ArrayList<>();

        for (String schedule : scheduleStageString){
            SingleScheduleBottomSheet singleScheduleBottomSheet = new SingleScheduleBottomSheet(schedule);
            planning.add(singleScheduleBottomSheet);
        }
        return planning;
    }

    /**
     *  TEST
     */

    @Test
    public void simpletest(){
        assertEquals(4, 2+2);
    }

    @Test
    public void testIfEventAreTwelveToThirteen(){
        Stage stage = getStage1();
        StageRegistration stageRegistration = getStageRegistration1();

        List<Integer> scheduleNumberDetection = new ArrayList<>();
        List<String> stageRegistrationParticipantList = Arrays.asList(stageRegistration.getParticipant().split(","));
        for (int i = 0; i < stageRegistrationParticipantList.size(); i++) {
            if (stageRegistrationParticipantList.get(i).contains(MY_NAME)) scheduleNumberDetection.add(i);
        }

        ModalUserTimeTable timeTable = new ModalUserTimeTable();
        List<DateTime> lastScheduleRegistration = timeTable.getListReservationStart();
        lastScheduleRegistration.addAll(EventBusiness.loadSchedulesStart(scheduleNumberDetection, stage));
        timeTable.setListReservationStart(lastScheduleRegistration);

        lastScheduleRegistration = timeTable.getListReservationEnd();
        lastScheduleRegistration.addAll(EventBusiness.loadSchedulesEnd(scheduleNumberDetection, stage));
        timeTable.setListReservationEnd(lastScheduleRegistration);

        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getHourOfDay()), "12");
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getHourOfDay()), "13");
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getMinuteOfHour()), "30");
    }

    @Test
    public void testIfEventStartAndEnd(){
        Stage stage = getStage1();
        StageRegistration stageRegistration = getStageRegistration2();

        List<Integer> scheduleNumberDetection = new ArrayList<>();
        List<String> stageRegistrationParticipantList = Arrays.asList(stageRegistration.getParticipant().split(","));
        for (int i = 0; i < stageRegistrationParticipantList.size(); i++) {
            if (stageRegistrationParticipantList.get(i).contains(MY_NAME)) scheduleNumberDetection.add(i);
        }

        ModalUserTimeTable timeTable = new ModalUserTimeTable();
        List<DateTime> lastScheduleRegistration = timeTable.getListReservationStart();
        lastScheduleRegistration.addAll(EventBusiness.loadSchedulesStart(scheduleNumberDetection, stage));
        timeTable.setListReservationStart(lastScheduleRegistration);

        lastScheduleRegistration = timeTable.getListReservationEnd();
        lastScheduleRegistration.addAll(EventBusiness.loadSchedulesEnd(scheduleNumberDetection, stage));
        timeTable.setListReservationEnd(lastScheduleRegistration);

        assertEquals(timeTable.getListReservationStart().size(), 2);

        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getHourOfDay()), "12");
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationStart().get(1).getHourOfDay()), "14");
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(1).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getHourOfDay()), "13");
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(1).getHourOfDay()), "15");
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(1).getMinuteOfHour()), "30");
    }

    @Test
    public void testIfEventStartAndEndWithTwoSchedules(){
        List<Stage> stageList = new ArrayList<>();
        stageList.add(getStage1());
        stageList.add(getStage2());
        List<StageRegistration> stageRegistrationList = new ArrayList<>();
        stageRegistrationList.add(getStageRegistration1());
        stageRegistrationList.add(getStageRegistration2());
        ModalUserTimeTable timeTable = new ModalUserTimeTable();

        for (int j= 0 ; j < stageList.size() ; j++) {
            List<Integer> scheduleNumberDetection = new ArrayList<>();
            List<String> stageRegistrationParticipantList = Arrays.asList(stageRegistrationList.get(j).getParticipant().split(","));
            for (int i = 0; i < stageRegistrationParticipantList.size(); i++) {
                if (stageRegistrationParticipantList.get(i).contains(MY_NAME))
                    scheduleNumberDetection.add(i);
            }


            List<DateTime> lastScheduleRegistration = timeTable.getListReservationStart();
            lastScheduleRegistration.addAll(EventBusiness.loadSchedulesStart(scheduleNumberDetection, stageList.get(j)));
            timeTable.setListReservationStart(lastScheduleRegistration);

            lastScheduleRegistration = timeTable.getListReservationEnd();
            lastScheduleRegistration.addAll(EventBusiness.loadSchedulesEnd(scheduleNumberDetection, stageList.get(j)));
            timeTable.setListReservationEnd(lastScheduleRegistration);
        }

        assertEquals(timeTable.getListReservationStart().size(), 3);

        // start list
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getHourOfDay()), "12");
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationStart().get(1).getHourOfDay()), "14");
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(1).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationStart().get(2).getHourOfDay()), "13");
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(2).getMinuteOfHour()), "30");

        // end list
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getHourOfDay()), "13");
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(1).getHourOfDay()), "15");
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(1).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(2).getHourOfDay()), "14");
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(2).getMinuteOfHour()), "30");
    }

    @Test
    public void testIfGetTimeTableMetodsIsOk(){
        ModalUserTimeTable timeTable = new ModalUserTimeTable();
        EventBusiness.getTimeTableUpdated(timeTable, MY_NAME, getStageRegistration1(), getStage1());

        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getHourOfDay()), "12");
        assertEquals(String.valueOf(timeTable.getListReservationStart().get(0).getMinuteOfHour()), "30");

        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getHourOfDay()), "13");
        assertEquals(String.valueOf(timeTable.getListReservationEnd().get(0).getMinuteOfHour()), "30");
    }

    @Test
    public void testComparaisonBetweenPlanningAndTimeTableActOne(){
        List<SingleScheduleBottomSheet> planning = getPlanning(getStage1());

        ModalUserTimeTable timeTable = new ModalUserTimeTable();
        EventBusiness.getTimeTableUpdated(timeTable, MY_NAME, getStageRegistration2(), getStage2());


        EventBusiness.compareTimeTableAndStagePlanning(planning, timeTable);

        int falseNb = 0;
        for (SingleScheduleBottomSheet singleScheduleBottomSheet : planning){
            if (!singleScheduleBottomSheet.isActifReservation()){
                falseNb++;
            }
        }

        assertEquals(1, falseNb);


        planning = getPlanning(getStage1Bis());

        EventBusiness.compareTimeTableAndStagePlanning(planning, timeTable);

        falseNb = 0;
        for (SingleScheduleBottomSheet singleScheduleBottomSheet : planning){
            if (!singleScheduleBottomSheet.isActifReservation()){
                falseNb++;
            }
        }

        assertEquals(2, falseNb);
    }

    @Test
    public void testIfParticipantIsInsertedIntoPlanning(){
        List<SingleScheduleBottomSheet> planning = getPlanning(getStage1());
        StageRegistration registration = getStageRegistration1();
        List<String> peopleGroup = Arrays.asList(registration.getParticipant().split(","));
        for (int i = 0 ; i < planning.size() ; i++){
            planning.get(i).setParticipantList(peopleGroup.get(i));
        }

        EventBusiness.addParticipantIntoPlanning(planning, MY_NAME, 1);

        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (String participantName : planning.get(1).getParticipantList()){
            result.append(prefix);
            prefix = Utils.PARTICIPANT_SEPARATOR;
            result.append(participantName);
        }

        assertEquals("MY_NAME!EMPTY", result.toString());
    }

    @Test
    public void testIfParticipantIsDeletedIntoPlanning(){
        List<SingleScheduleBottomSheet> planning = getPlanning(getStage1());
        StageRegistration registration = getStageRegistration1();
        List<String> peopleGroup = Arrays.asList(registration.getParticipant().split(","));
        for (int i = 0 ; i < planning.size() ; i++){
            planning.get(i).setParticipantList(peopleGroup.get(i));
        }

        EventBusiness.deleteParticipantIntoPlanning(planning, MY_NAME, 0);

        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (String participantName : planning.get(1).getParticipantList()){
            result.append(prefix);
            prefix = Utils.PARTICIPANT_SEPARATOR;
            result.append(participantName);
        }

        assertEquals("EMPTY!EMPTY", result.toString());
    }




}

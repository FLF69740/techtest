package com.epfd.dolto.administrator.cakefridayedition;

import com.google.common.base.Joiner;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessCakeFriday {

    public static List<CakeClassroom> getListCakeDateFromFirebase(List<CakeClassroom> cakeClassrooms, String targetClassroom, String dateString){
        for (int i = 0 ; i < cakeClassrooms.size() ; i++){
            if (cakeClassrooms.get(i).getClassroomCake().equals(targetClassroom)){
                if (cakeClassrooms.get(i).getDatesPlannification() == null){
                    cakeClassrooms.get(i).setDatesPlannification(new ArrayList<>());
                }

               List<String> tempDate = cakeClassrooms.get(i).getDatesPlannification();

               tempDate.add(dateString);
               cakeClassrooms.get(i).setDatesPlannification(tempDate);
            }
        }
        return cakeClassrooms;
    }

    public static CakeClassroom updateCakeDateListString(CakeClassroom cakeClassroom, String propositionDate){
        boolean exist = false;
        for (String registeredDate : cakeClassroom.getDatesPlannification()){
            if (propositionDate.equals(registeredDate)){
                exist = true;
            }
        }
        if (!exist){
            List<String> newList = cakeClassroom.getDatesPlannification();
            newList.add(0, propositionDate);
            cakeClassroom.setDatesPlannification(newList);
        }

        return cakeClassroom;
    }

    public static int getCakePositionToDelete(String cakeListString, String dateListString, String cake, String date){

        List<String> cakeList = new ArrayList<>(Arrays.asList(cakeListString.split(",")));
        List<String> dateList = new ArrayList<>(Arrays.asList(dateListString.split(",")));

        int result = 0;
        for (int i = 0 ; i < cakeList.size() ; i++){
            if (cakeList.get(i).equals(cake) && dateList.get(i).equals(date)) result = i;
        }
        return result;
    }

    public static String deleteCakeNameForFirebase(String cakeListString, int position){
        List<String> cakeList = new ArrayList<>(Arrays.asList(cakeListString.split(",")));
        cakeList.remove(position);
        cakeListString = Joiner.on(",").join(cakeList);
        return cakeListString;
    }

    public static String deleteCakeDateForFirebase(String dateListString, int position){
        List<String> dateList = new ArrayList<>(Arrays.asList(dateListString.split(",")));
        dateList.remove(position);
        dateListString = Joiner.on(",").join(dateList);
        return dateListString;
    }

    public static int getCakeTag(DateTime dateTime){
        StringBuilder builder = new StringBuilder();
        builder.append(dateTime.getDayOfYear()).append(dateTime.getSecondOfDay());
        return Integer.valueOf(builder.toString());
    }

}

package com.epfd.csandroid.administrator.cakefridayedition;

import java.util.ArrayList;
import java.util.List;

public class BusinessCakeFriday {

    public static List<CakeClassroom> getListWithInsertedCakeDate(List<CakeClassroom> cakeClassrooms, String targetClassroom, String dateString){
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
}

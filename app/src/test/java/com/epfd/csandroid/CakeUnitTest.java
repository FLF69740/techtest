package com.epfd.csandroid;

import com.epfd.csandroid.administrator.cakefridayedition.BusinessCakeFriday;
import com.epfd.csandroid.administrator.cakefridayedition.CakeClassroom;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CakeUnitTest {

    private static final String FIRST_CLASSROOM_NAME = "FIRST_CLASSROOM_NAME";
    private static final String SECOND_CLASSROOM_NAME = "SECOND_CLASSROOM_NAME";

    private CakeClassroom getFirstCake(){
        CakeClassroom cakeClassroom = new CakeClassroom();
        cakeClassroom.setClassroomCake(FIRST_CLASSROOM_NAME);
        cakeClassroom.setDatesPlannification(new ArrayList<>());
        return cakeClassroom;
    }

    private CakeClassroom getSecondCake(){
        CakeClassroom cakeClassroom = new CakeClassroom();
        cakeClassroom.setClassroomCake(SECOND_CLASSROOM_NAME);
        cakeClassroom.setDatesPlannification(new ArrayList<>());
        return cakeClassroom;
    }

    private List<CakeClassroom> getCakeList(){
        List<CakeClassroom> cakeClassrooms = new ArrayList<>();
        cakeClassrooms.add(getFirstCake());
        cakeClassrooms.add(getSecondCake());
        return cakeClassrooms;
    }

    @Test
    public void simpletest(){
        assertEquals(4, 2+2);
    }

    @Test
    public void testIfDateCakeIsInserted(){
        List<CakeClassroom> cakeClassrooms = getCakeList();
        BusinessCakeFriday.getListWithInsertedCakeDate(cakeClassrooms, SECOND_CLASSROOM_NAME, "01/01/1901");
        BusinessCakeFriday.getListWithInsertedCakeDate(cakeClassrooms, SECOND_CLASSROOM_NAME, "01/01/1902");
        BusinessCakeFriday.getListWithInsertedCakeDate(cakeClassrooms, FIRST_CLASSROOM_NAME, "01/01/2002");

        assertEquals("01/01/2002", cakeClassrooms.get(0).getDatesPlannification().get(0));
        assertEquals("01/01/1901", cakeClassrooms.get(1).getDatesPlannification().get(0));
        assertEquals("01/01/1902", cakeClassrooms.get(1).getDatesPlannification().get(1));
    }


}

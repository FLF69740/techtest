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
    private static final String THIRD_CLASSROOM_NAME = "THIRD_CLASSROOM_NAME";
    private static final String FOUR_CLASSROOM_NAME = "FOUR_CLASSROOM_NAME";
    private static final String DATE_1 = "01/01/1901";
    private static final String DATE_2 = "01/01/1902";
    private static final String DATE_3 = "01/01/2002";
    private static final String DATE_4 = "01/08/2002";

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
    public void testIfDateCakeIsInsertedFromFirebase(){
        List<CakeClassroom> cakeClassrooms = getCakeList();
        BusinessCakeFriday.getListCakeDateFromFirebase(cakeClassrooms, SECOND_CLASSROOM_NAME, DATE_1);
        BusinessCakeFriday.getListCakeDateFromFirebase(cakeClassrooms, SECOND_CLASSROOM_NAME, DATE_2);
        BusinessCakeFriday.getListCakeDateFromFirebase(cakeClassrooms, FIRST_CLASSROOM_NAME, DATE_3);

        assertEquals(DATE_3, cakeClassrooms.get(0).getDatesPlannification().get(0));
        assertEquals(DATE_1, cakeClassrooms.get(1).getDatesPlannification().get(0));
        assertEquals(DATE_2, cakeClassrooms.get(1).getDatesPlannification().get(1));
    }

    @Test
    public void testIfDateCakePropositionIsInsertedIntoCakeClassroom(){
        CakeClassroom cakeClassroom = getFirstCake();

        BusinessCakeFriday.updateCakeDateListString(cakeClassroom, DATE_1);
        assertEquals(DATE_1, cakeClassroom.getDatesPlannification().get(0));

        BusinessCakeFriday.updateCakeDateListString(cakeClassroom, DATE_2);
        assertEquals(DATE_2, cakeClassroom.getDatesPlannification().get(0));
    }

    @Test
    public void testIfDateCakePropositionIsRejectedIntoCakeClassroom(){
        CakeClassroom cakeClassroom = getFirstCake();

        BusinessCakeFriday.updateCakeDateListString(cakeClassroom, DATE_1);
        BusinessCakeFriday.updateCakeDateListString(cakeClassroom, DATE_2);

        assertEquals(DATE_2, cakeClassroom.getDatesPlannification().get(0));

        BusinessCakeFriday.updateCakeDateListString(cakeClassroom, DATE_1);
        assertEquals(DATE_2, cakeClassroom.getDatesPlannification().get(0));
    }

    @Test
    public void testIfGoodCakeNameIsDeleteFromFirebase(){
        String cakeListString = FIRST_CLASSROOM_NAME + "," + SECOND_CLASSROOM_NAME + "," + THIRD_CLASSROOM_NAME + "," + FOUR_CLASSROOM_NAME;
        String dateListString = DATE_1 + "," + DATE_2 + "," + DATE_3 + "," + DATE_4;

        String result = BusinessCakeFriday.deleteCakeNameForFirebase(cakeListString, BusinessCakeFriday.getCakePositionToDelete(cakeListString, dateListString, THIRD_CLASSROOM_NAME, DATE_3));

        assertEquals(result, FIRST_CLASSROOM_NAME + "," + SECOND_CLASSROOM_NAME + "," + FOUR_CLASSROOM_NAME);
    }

    @Test
    public void testIfGoodCakeDateIsDeleteFromFirebase(){
        String cakeListString = FIRST_CLASSROOM_NAME + "," + SECOND_CLASSROOM_NAME + "," + THIRD_CLASSROOM_NAME + "," + FOUR_CLASSROOM_NAME;
        String dateListString = DATE_1 + "," + DATE_2 + "," + DATE_3 + "," + DATE_4;

        String result = BusinessCakeFriday.deleteCakeDateForFirebase(dateListString, BusinessCakeFriday.getCakePositionToDelete(cakeListString, dateListString, THIRD_CLASSROOM_NAME, DATE_3));

        assertEquals(result, DATE_1 + "," + DATE_2 + "," + DATE_4);
    }

}

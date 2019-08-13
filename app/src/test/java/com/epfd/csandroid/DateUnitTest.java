package com.epfd.csandroid;

import com.epfd.csandroid.utils.Utils;

import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

public class DateUnitTest {

    @Test
    public void simpletest(){
        assertEquals(4, 2+2);
    }

    @Test
    public void testIfClassroomsHelperReturnYearCode(){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.year().setCopy(2019);
        if (dateTime.getMonthOfYear() < 7) {
            assertEquals("20182019", Utils.getSchoolYear(dateTime));
        }else {
            assertEquals("20192020", Utils.getSchoolYear(dateTime));
        }
    }

    @Test
    public void testIfPublicationDateIsCorrectAtDayLessThreeWithStringFormat(){
        assertEquals("19/03/1975", Utils.getDayLessThree(null, "22/03/1975"));
    }

    @Test
    public void testIfPublicationDateIsCorrectAtDayLessThreeWithDateTimeFormat(){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.dayOfMonth().setCopy(22);
        dateTime = dateTime.monthOfYear().setCopy(3);
        dateTime = dateTime.year().setCopy(1975);
        assertEquals("19/03/1975", Utils.getDayLessThree(dateTime, null));
    }

    @Test
    public void testIfPublicationDateIsCorrectAtDayLessXWithStringFormat(){
        assertEquals("12/03/1975", Utils.getDayLessXDays(null, "22/03/1975", 10));
    }

    @Test
    public void testIfPublicationDateIsCorrectAtDayLessXWithDateTimeFormat(){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.dayOfMonth().setCopy(22);
        dateTime = dateTime.monthOfYear().setCopy(3);
        dateTime = dateTime.year().setCopy(1975);
        assertEquals("12/03/1975", Utils.getDayLessXDays(dateTime, null, 10));
    }

}

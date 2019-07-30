package com.epfd.csandroid;

import com.epfd.csandroid.api.ClassroomsHelper;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

public class DateUnitTest {

    @Test
    public void testIfClassroomsHelperReturnYearCode(){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.year().setCopy(2019);
        if (dateTime.getMonthOfYear() < 7) {
            assertEquals("20182019", ClassroomsHelper.getSchoolYear(dateTime));
        }else {
            assertEquals("20192020", ClassroomsHelper.getSchoolYear(dateTime));
        }
    }

}

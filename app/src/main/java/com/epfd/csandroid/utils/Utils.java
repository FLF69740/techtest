package com.epfd.csandroid.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class Utils {

    public static final String APEL = "apel.dolto.sqf@gmail.com";
    public static final String SCHOOL = "ecole.dolto.sqf@gmail.com";
    public static final String DEV = "slayer171.flf@gmail.com";

    public static final String INFORMATION_LOG = "INFORMATION_LOG";
    public static final String EMPTY_PREFERENCES_LOG_CODE = "EMPTY_PREFERENCES_LOG_CODE";
    public static final String EMPTY = "EMPTY";
    public static final String NAME_DATA_CODE = "code";

    public static final String CONSOLE_NOTIF_TITLE = "TITRE";
    public static final String CONSOLE_NOTIF_BODY = "MESSAGE";

    public static final String NAME_DATA_CAKE_DATE = "dateListString";
    public static final String NAME_DATA_CAKE_CLASSROOMS = "classroomsListString";

    public static final String NAME_DATA_CLASSROOMS = "name";
    public static final String ALL = "ALL";

    public static final String NAME_DATA_CLASSROOM_USER = "stringClasseNameList";

    public static final String BUNDLE_USERNAME = "BUNDLE_USERNAME";
    public static final String BUNDLE_USERMAIL = "BUNDLE_USERMAIL";
    public static final String BUNDLE_PASSWORD = "BUNDLE_PASSWORD";

    public static final String SHARED_INTERNAL_CODE = "SHARED_INTERNAL_CODE";
    public static final String BUNDLE_KEY_ACTIVE_USER = "BUNDLE_KEY_ACTIVE_USER";

    public static final String SHARED_FORMULARY_SOLD = "SHARED_FORMULARY_SOLD";
    public static final String BUNDLE_KEY_FORMULARY = "BUNDLE_KEY_FORMULARY";

    public static final String BOY = "BOY";
    public static final String GIRL = "GIRL";

    public static final String ANALYTICS_CLASSROOM_PROPERTY = "classe";

    // GET ACTUAL SCHOOL YEAR
    public static String getSchoolYear(DateTime dateTime){
        String result;
        DateTime secondDate = dateTime;

        if (dateTime.getMonthOfYear() < 7){
            secondDate = secondDate.year().setCopy(secondDate.getYear()-1);
            result = String.valueOf(secondDate.getYear()) + dateTime.getYear();
        } else {
            secondDate = secondDate.year().setCopy(secondDate.getYear()+1);
            result = String.valueOf(dateTime.getYear()) + secondDate.getYear();
        }

        return result;
    }

    // GET DAY - 3
    public static String getDayLessThree(DateTime dateTime, String dateString){
        if (dateTime == null && dateString != null){
            DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
            dateTime = fmt.parseDateTime(dateString);
        }
        dateTime = dateTime.minusDays(3);
        return dateTime.toString("dd/MM/yyyy");
    }

    // GET DAY - X
    public static String getDayLessXDays(DateTime dateTime, String dateString, int nbDays){
        if (dateTime == null && dateString != null){
            DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
            dateTime = fmt.parseDateTime(dateString);
        }
        dateTime = dateTime.minusDays(nbDays);
        return dateTime.toString("dd/MM/yyyy");
    }

    //charsequence into a String
    public static int getSequenceNumberIntoAString(String target, char someChar){
        int count = 0;

        for (int i = 0; i < target.length(); i++) {
            if (target.charAt(i) == someChar) {
                count++;
            }
        }

        return count;
    }


}

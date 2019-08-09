package com.epfd.csandroid.utils;

import org.joda.time.DateTime;

public class Utils {

    public static final String APEL = "apel.dolto.sqf@gmail.com";
    public static final String SCHOOL = "ecole.f.dolto@free.fr";
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

    public static final String BUNDLE_USERNAME = "BUNDLE_USERNAME";
    public static final String BUNDLE_USERMAIL = "BUNDLE_USERMAIL";
    public static final String BUNDLE_PASSWORD = "BUNDLE_PASSWORD";

    public static final String SHARED_INTERNAL_CODE = "SHARED_INTERNAL_CODE";
    public static final String BUNDLE_KEY_ACTIVE_USER = "BUNDLE_KEY_ACTIVE_USER";

    public static final String SHARED_FORMULARY_SOLD = "SHARED_FORMULARY_SOLD";
    public static final String BUNDLE_KEY_FORMULARY = "BUNDLE_KEY_FORMULARY";

    public static final String BOY = "BOY";
    public static final String GIRL = "GIRL";

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


}

package com.epfd.csandroid.api;

import com.epfd.csandroid.models.Classroom;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.joda.time.DateTime;

import static com.epfd.csandroid.utils.Utils.NAME_DATA_CLASSROOMS;

public class ClassroomsHelper {

    private static final String COLLECTION_NAME = "classroomlist";

    private static CollectionReference getClassroomsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createClassroom(String name) {
        Classroom classroomToCreate = new Classroom(name);
        return ClassroomsHelper.getClassroomsCollection().document(getSchoolYear(DateTime.now())).set(classroomToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getClassrooms(){
        return ClassroomsHelper.getClassroomsCollection().document(getSchoolYear(DateTime.now())).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateClassrooms(String name) {
        return ClassroomsHelper.getClassroomsCollection().document(getSchoolYear(DateTime.now())).update(NAME_DATA_CLASSROOMS, name);
    }


    // GET ACTUAL DOCUMENT NAME
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

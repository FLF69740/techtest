package com.epfd.csandroid.api;

import com.epfd.csandroid.models.Classroom;
import com.epfd.csandroid.utils.Utils;
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
        return ClassroomsHelper.getClassroomsCollection().document(Utils.getSchoolYear(DateTime.now())).set(classroomToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getClassrooms(){
        return ClassroomsHelper.getClassroomsCollection().document(Utils.getSchoolYear(DateTime.now())).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateClassrooms(String name) {
        return ClassroomsHelper.getClassroomsCollection().document(Utils.getSchoolYear(DateTime.now())).update(NAME_DATA_CLASSROOMS, name);
    }



}

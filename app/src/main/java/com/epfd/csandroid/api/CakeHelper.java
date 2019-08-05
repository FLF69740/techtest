package com.epfd.csandroid.api;

import com.epfd.csandroid.models.CakeFriday;
import com.epfd.csandroid.utils.Utils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.joda.time.DateTime;

public class CakeHelper {

    private static final String COLLECTION_NAME = "cakefriday";

    private static CollectionReference getCakeEventCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createCakeEvent(String classroomsList, String dateList) {
        CakeFriday cakeEventToCreate = new CakeFriday(dateList, classroomsList);
        return CakeHelper.getCakeEventCollection().document(Utils.getSchoolYear(DateTime.now())).set(cakeEventToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getCakeEvent(){
        return CakeHelper.getCakeEventCollection().document(Utils.getSchoolYear(DateTime.now())).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateCakeEventDate(String dateList) {
        return CakeHelper.getCakeEventCollection().document(Utils.getSchoolYear(DateTime.now())).update(Utils.NAME_DATA_CAKE_DATE, dateList);
    }

    public static Task<Void> updateCakeEventClassrooms(String classroomList) {
        return CakeHelper.getCakeEventCollection().document(Utils.getSchoolYear(DateTime.now())).update(Utils.NAME_DATA_CAKE_CLASSROOMS, classroomList);
    }
}

package com.epfd.csandroid.api;

import com.epfd.csandroid.models.StageRegistration;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StageRegistrationHelper {

    private static final String COLLECTION_NAME = "stageRegistration";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getStageRegistrationCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createStageRegistration(String uid, StageRegistration registration){
        return StageRegistrationHelper.getStageRegistrationCollection().document(uid).set(registration);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getStageRegistration(String uid){
        return StageRegistrationHelper.getStageRegistrationCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateStageRegistrationParticipant(String uid, String participant){
        return StageRegistrationHelper.getStageRegistrationCollection().document(uid).update("participant", participant);
    }

    // --- DELETE ---

    public static Task<Void> deleteStageRegistration(String uid){
        return StageRegistrationHelper.getStageRegistrationCollection().document(uid).delete();
    }

}

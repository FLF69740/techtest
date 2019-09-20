package com.epfd.csandroid.api;

import com.epfd.csandroid.models.Stage;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StageCreatorHelper {

    private static final String COLLECTION_NAME = "stage";
    public static final String ROOT_UID = "STAGE";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getStageCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createStage(String uid, Stage stage) {
        return StageCreatorHelper.getStageCollection().document(uid).set(stage);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getStage(String uid){
        return StageCreatorHelper.getStageCollection().document(uid).get();
    }

    // --- DELETE ---

    public static Task<Void> deleteStage(String uid){
        return StageCreatorHelper.getStageCollection().document(uid).delete();
    }

}

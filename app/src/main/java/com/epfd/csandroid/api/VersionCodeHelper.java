package com.epfd.csandroid.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VersionCodeHelper {

    private static final String COLLECTION_NAME = "versionCode";
    private static final String DOCUMENT_NAME = "serial";


    private static CollectionReference getVersionCodeCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getVersionCode(){
        return VersionCodeHelper.getVersionCodeCollection().document(DOCUMENT_NAME).get();
    }
}

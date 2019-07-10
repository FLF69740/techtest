package com.epfd.csandroid.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PasswordHelper {

    private static final String COLLECTION_NAME = "apelcode";
    private static final String DOCUMENT_NAME = "root1";

    public static CollectionReference getCodeCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getCode(){
        return PasswordHelper.getCodeCollection().document(DOCUMENT_NAME).get();
    }

}

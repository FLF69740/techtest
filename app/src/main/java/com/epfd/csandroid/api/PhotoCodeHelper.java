package com.epfd.csandroid.api;

import com.epfd.csandroid.models.PhotoCode;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PhotoCodeHelper {

    private static final String COLLECTION_NAME = "photocode";
    private static final String DOCUMENT_NAME = "serial";


    private static CollectionReference getPhotoCodeCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createPhotoCode(String serial){
        PhotoCode photoCode = new PhotoCode(serial);
        return PhotoCodeHelper.getPhotoCodeCollection().document(DOCUMENT_NAME).set(photoCode);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getPhotoCode(){
        return PhotoCodeHelper.getPhotoCodeCollection().document(DOCUMENT_NAME).get();
    }

}

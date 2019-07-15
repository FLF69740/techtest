package com.epfd.csandroid.api;

import com.epfd.csandroid.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHelper {

    private static final String COLLECTION_NAME = "users";
    private static final String KID_LIST = "kid";
    private static final String CLASSE_LIST = "classe";
    private static final String GENDER_LIST = "gender";

    // --- COLLECTION REFERENCE ---

    private static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String urlPicture, String stringKidNameList, String stringClasseroomsList, String stringGenderList) {
        User userToCreate = new User(uid, username, urlPicture, stringKidNameList, stringClasseroomsList, stringGenderList);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> updateKidList(String uid, String stringKidNameList) {
        return UserHelper.getUsersCollection().document(uid).update(KID_LIST, stringKidNameList);
    }

    public static Task<Void> updateClasseList(String uid, String stringClasseNameList) {
        return UserHelper.getUsersCollection().document(uid).update(CLASSE_LIST, stringClasseNameList);
    }

    public static Task<Void> updateGenderList(String uid, String stringGenderList) {
        return UserHelper.getUsersCollection().document(uid).update(GENDER_LIST, stringGenderList);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}

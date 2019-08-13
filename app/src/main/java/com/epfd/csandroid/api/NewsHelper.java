package com.epfd.csandroid.api;

import com.epfd.csandroid.models.News;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewsHelper {

    private static final String COLLECTION_NAME = "news";
    private static final String TITLE = "title";
    private static final String DATE = "date";
    private static final String NOTIFICATION = "notification";
    private static final String PUBLICATION = "publication";
    private static final String PHOTO = "photo";
    private static final String BODY = "body";

    // --- COLLECTION REFERENCE ---

    private static CollectionReference getNewsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createNews(String title, String date, boolean notification, String publication, String photo, String body, String sectionName, String dateBloc) {
        News newsToCreate = new News(title, date, notification, publication, photo, body);
        String newsId = sectionName + dateBloc;
        return NewsHelper.getNewsCollection().document(newsId).set(newsToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getNews(String uid){
        return NewsHelper.getNewsCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateNewsTitle(String title, String uid) {
        return NewsHelper.getNewsCollection().document(uid).update(TITLE, title);
    }

    public static Task<Void> updateNewsDate(String uid, String date) {
        return NewsHelper.getNewsCollection().document(uid).update(DATE, date);
    }

    public static Task<Void> updateNewsNotification(String uid, boolean notification) {
        return NewsHelper.getNewsCollection().document(uid).update(NOTIFICATION, notification);
    }

    public static Task<Void> updateNewsPublication(String uid, String publication) {
        return NewsHelper.getNewsCollection().document(uid).update(PUBLICATION, publication);
    }

    public static Task<Void> updateNewsPhoto(String uid, String photo) {
        return NewsHelper.getNewsCollection().document(uid).update(PHOTO, photo);
    }

    public static Task<Void> updateNewsBody(String uid, String body) {
        return NewsHelper.getNewsCollection().document(uid).update(BODY, body);
    }

    // --- DELETE ---

    public static Task<Void> deleteNews(String uid) {
        return NewsHelper.getNewsCollection().document(uid).delete();
    }


}

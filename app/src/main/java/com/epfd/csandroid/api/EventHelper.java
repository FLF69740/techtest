package com.epfd.csandroid.api;

import com.epfd.csandroid.models.Event;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventHelper {

    private static final String COLLECTION_NAME = "events";

    // --- COLLECTION REFERENCE ---

    private static CollectionReference getEventsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createEvent(String uid, String name, String date, String description, String photo, String label) {
        Event eventToCreate = new Event(name, date, description, photo, label);
        return EventHelper.getEventsCollection().document(uid).set(eventToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getEvent(String uid){
        return EventHelper.getEventsCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateEventName(String uid, String name){
        return EventHelper.getEventsCollection().document(uid).update("name", name);
    }

    public static Task<Void> updateEventDate(String uid, String date){
        return EventHelper.getEventsCollection().document(uid).update("date", date);
    }

    public static Task<Void> updateEventDescription(String uid, String description){
        return EventHelper.getEventsCollection().document(uid).update("description", description);
    }

    public static Task<Void> updateEventPhoto(String uid, String photo){
        return EventHelper.getEventsCollection().document(uid).update("photo", photo);
    }

    public static Task<Void> updateEventLogo(String uid, String label){
        return EventHelper.getEventsCollection().document(uid).update("logo", label);
    }

    public static Task<Void> updateEventStages(String uid, String stages){
        return EventHelper.getEventsCollection().document(uid).update("stages", stages);
    }

    // --- DELETE ---

    public static Task<Void> deleteEvent(String uid) {
        return EventHelper.getEventsCollection().document(uid).delete();
    }

}

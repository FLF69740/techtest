package com.epfd.dolto.utils;

import android.content.Context;
import com.epfd.dolto.api.PhotoCodeHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;

public class FireBaseStorageUtils {

    public FireBaseStorageUtils() {}

    public void createStorageSerial(){
        StringBuilder builder = new StringBuilder();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference prefix : listResult.getItems()){
                prefix.getDownloadUrl().addOnSuccessListener(uri -> {
                    builder.append(prefix.getName()).append(BitmapStorage.PHOTO_SEPARATOR);
                    PhotoCodeHelper.createPhotoCode(builder.toString());
                });
            }
        });
    }

    public void initialisePhotoGallery(Context context){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference prefix : listResult.getItems()){
                File localFile = new File(context.getFilesDir(), prefix.getName());
                prefix.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    }
                });

            }
        });
    }


}

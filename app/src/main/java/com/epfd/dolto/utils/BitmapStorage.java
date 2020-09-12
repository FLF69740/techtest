package com.epfd.dolto.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class BitmapStorage {

    public static final String PHOTO_SEPARATOR  = "!-!";

    // save Image into internal memory
    public static void saveImageInternalStorage(Context context, String imageName, Uri uri){
        Bitmap bitmap = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // load Image from internal memory
    public static Bitmap loadImage(Context context, String imageName){
        Bitmap bitmap = null;
        try {
            String path = new File(context.getFilesDir(), imageName).getAbsolutePath();
            bitmap = BitmapFactory.decodeFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // check if image exist into internal memory
    public static boolean isFileExist(Context context, String imageName){
        File file = context.getFileStreamPath(imageName);
        return file.exists();
    }

    // show if bitmpap exist and his path into the logcat
    public static void showImageInformations(Context context, String imageName){
        File file = context.getFileStreamPath(imageName);
        Log.i(Utils.INFORMATION_LOG, "Exist : "+ String.valueOf(file.exists()) + " - chemin : " + file.getAbsolutePath());
    }

    // Organise real photo used for RISK
    public static void purgePhotosInternalMemory(Context context){
        Log.i(Utils.INFORMATION_LOG, "--> " + context.getFilesDir().getAbsolutePath());
        File directory = context.getFilesDir();

        File[] list = directory.listFiles();
        for (File ff : list) {
            if (isInteger(ff.getName()))
            deleteImage(context, ff.getName());
        }
    }

    // Get photo internal memory key code
    public static String getPhotoMemoryCode(Context context){
        StringBuilder builder = new StringBuilder();
        File directory = context.getFilesDir();

        File[] list = directory.listFiles();
        for (File ff : list) {
            if (isInteger(ff.getName()))
            builder.append(ff.getName()).append(PHOTO_SEPARATOR);
        }
        return builder.toString();
    }

    // delete a bitmap
    public static void deleteImage(Context context, String imageName){
        if (BitmapStorage.isFileExist(context, imageName)){
            File file = context.getFileStreamPath(imageName);
            file.delete();
            Log.i(Utils.INFORMATION_LOG, imageName + " : bitmap deleted");
        }else {
            Log.i(Utils.INFORMATION_LOG, "bitmap didn't exist");
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}

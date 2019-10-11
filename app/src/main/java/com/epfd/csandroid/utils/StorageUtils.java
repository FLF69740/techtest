package com.epfd.csandroid.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class StorageUtils {

    private static File createOrGetFile(File destination, String fileName, String folderName){
        File folder = new File(destination, folderName);
        return new File(folder, fileName);
    }

    private static void writeOnFile(Context context, String text, File file){

        try {
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            Writer w = new BufferedWriter(new OutputStreamWriter(fos));

            try {
                w.write(text);
                w.flush();
                fos.getFD().sync();
            } finally {
                w.close();
            }

        } catch (IOException e) {
            Log.i("INTERNAL STORAGE", "WRITTE INTERNAL STORAGE ERROR :" + e.toString());
        }
    }

    public static void setTextInStorage(File rootDestination, Context context, String fileName, String folderName, String text){
        File file = createOrGetFile(rootDestination, fileName, folderName);
        writeOnFile(context, text, file);
    }
}

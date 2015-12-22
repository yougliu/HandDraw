package com.example.helios.handdraw.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by helios on 12/22/15.
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    //sdcard
    public static boolean hasSDCard(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&!Environment.isExternalStorageRemovable()){
            return true;
        }
        return false;
    }

    /**
     * get storage path
     * @param context
     * @return
     */
    public static String getStoragePath(Context context){
        String path = null;
        if(hasSDCard(context)){
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
            path = context.getFilesDir().getAbsolutePath();
        }
        return path;
    }

    /**
     * create file
     * @param context
     * @param parentPath
     * @param fileName
     * @return
     */
    public static File createAbsoluteFile(Context context,String parentPath, String fileName){
        if(parentPath == null){
            parentPath = getStoragePath(context);
        }
        File parentFile = new File(parentPath);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        File file = new File(parentFile,fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File getDiskCacheDir(Context context, String fileName){
        String diskCache = null;
        if(hasSDCard(context)){
            diskCache = context.getExternalCacheDir().getPath();
        }else{
            diskCache = context.getCacheDir().getPath();
        }
        return new File(diskCache+File.separator+fileName);
    }

}

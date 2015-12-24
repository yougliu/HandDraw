package com.example.helios.handdraw.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

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

    /**
     * create cache dir
     * @param context
     * @param fileName
     * @return
     */
    public static File getDiskCacheDir(Context context, String fileName){
        String diskCache = null;
        if(hasSDCard(context)){
            diskCache = context.getExternalCacheDir().getPath();
        }else{
            diskCache = context.getCacheDir().getPath();
        }
        return new File(diskCache+File.separator+fileName);
    }

    public static String getAppStoragePath(Context context){
        String dir = getStoragePath(context);
        return dir + File.separator + context.getPackageName();
    }

    public static String DecodeURL(String uriString) throws Exception {
        String str = URLDecoder.decode(uriString, "iso-8859-1");
        String rule = "^(?:[\\x00-\\x7f]|[\\xe0-\\xef][\\x80-\\xbf]{2})+$";
        if (str.matches(rule)) {
            return URLDecoder.decode(uriString, "UTF-8");
        } else {
            return URLDecoder.decode(uriString, "GB2312");
        }
    }

    public static String getPath(Context context,String folder) {
        String mydir = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            mydir = Environment.getExternalStorageDirectory().getAbsolutePath();
            mydir = mydir + File.separator
                    + context.getPackageName()
                    + File.separator;
            if (!isFolderExists(mydir)) {
                mydir = context.getCacheDir()
                        .getAbsolutePath()
                        + File.separator;
            }
        } else {
            mydir = context.getCacheDir()
                    .getAbsolutePath()
                    + File.separator;
        }
        mydir = mydir + folder + File.separator;
        isFolderExists(mydir);
        return mydir;
    }


    private static boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdir())
                return true;
            else
                return false;
        }
        return true;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * Whether the Uri authority is ExternalStorageProvider.
     * @param uri
     * @return
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * Whether the Uri authority is DownloadsProvider.
     * @param uri
     * @return
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * Whether the Uri authority is MediaProvider.
     * @param uri
     * @return
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

}

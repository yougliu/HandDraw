package com.example.helios.handdraw.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.example.helios.handdraw.contants.Contants;

import java.util.ArrayList;
import java.util.List;

/**
 * for common util
 * Created by helios on 12/22/15.
 */
public class ConfigUtils {

    private static final String TAG = ConfigUtils.class.getSimpleName();

    /**
     * 版本号
     * @param context
     * @return
     */
    public static int getApplicationVersion(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 设备型号
     * @param context
     * @return
     */
    public static String getDeviceModel(Context context){
        Build bd = new Build();
        return bd.MODEL;
    }

    /**
     * sdk版本
     * @return
     */
    public static String getSDKVersion(){
        return Build.VERSION.SDK;
    }

    /**
     * 系统版本
     * @return
     */
    public static String getReleaseVersion(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备IMEI号
     * @param context
     * @return
     */
    public static String getIMEI(Context context){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static List<int[]> getWatermarkList(){
        List<int[]> list = new ArrayList<>();
        list.add(Contants.water_affection);
        list.add(Contants.water_food);
        list.add(Contants.water_popular);
        list.add(Contants.water_travel);
        list.add(Contants.water_affection_bg);
        list.add(Contants.water_food_bg);
        list.add(Contants.water_popular_bg);
        list.add(Contants.water_travel_bg);
        return list;
    }


}

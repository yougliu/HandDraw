package com.example.helios.handdraw;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.helios.handdraw.contants.Contants;
import com.example.helios.handdraw.utils.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helios on 12/22/15.
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();
    private List<Activity> mActivityLists = new ArrayList<>();
    private Context mContext = getApplicationContext();

    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity){
        mActivityLists.add(activity);
    }

    /**
     * 清楚activity
     */
    public void exitApplication(){
        try{
            for (Activity activity : mActivityLists){
                if(activity != null){
                    activity.finish();
                }
            }
        }catch(Exception e){
            throw new RuntimeException("exit APP failed!");
        }finally{
            System.exit(0);
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences(Contants.DEVICEINFO_SHARE_PRE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Contants.DEVICE_MODEL, ConfigUtils.getDeviceModel(mContext));
        editor.putString(Contants.DEVICE_IMEI,ConfigUtils.getIMEI(mContext));
        editor.putString(Contants.DEVICE_RELEASE,ConfigUtils.getReleaseVersion());
        editor.putString(Contants.DEVICE_SDK,ConfigUtils.getSDKVersion());
        editor.putString(Contants.APP_VERSION,String.valueOf(ConfigUtils.getApplicationVersion(mContext)));
        editor.commit();

    }
}

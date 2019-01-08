package com.example.administrator.mydemo.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * 屏幕适配方案
 */
public class CustomDensity {
    private static  float sNocompatDensity;
    private static  float sNocompatScaledDensity;
    public static final void setCustomDensity(Activity activity, Application application){
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if(sNocompatDensity == 0){
            sNocompatDensity = displayMetrics.density;
            sNocompatScaledDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig!= null && newConfig.fontScale>0){
                        sNocompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
            float targetDensity = displayMetrics.widthPixels / 375f;
            float targetScaledDensity = targetDensity * (sNocompatScaledDensity / sNocompatDensity);
            int targetDensityDpi = (int) (160*targetDensity);

            displayMetrics.density = targetDensity;
            displayMetrics.scaledDensity = targetScaledDensity;
            displayMetrics.densityDpi = targetDensityDpi;

            DisplayMetrics activitydisplayMetrics = activity.getResources().getDisplayMetrics();
            activitydisplayMetrics.density = targetDensity;
            activitydisplayMetrics.scaledDensity = targetScaledDensity;
            activitydisplayMetrics.densityDpi = targetDensityDpi;

        }

    }
}

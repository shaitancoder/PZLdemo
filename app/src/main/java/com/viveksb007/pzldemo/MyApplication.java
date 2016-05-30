package com.viveksb007.pzldemo;

import android.app.Application;

import com.flurry.android.FlurryAgent;

/**
 * Created by viveksb007 on 5/30/16.
 */
public class MyApplication extends Application {

    private static String FLURRY_API_KEY = "6VW9TRBYK43BKHGS6J48";

    @Override
    public void onCreate() {
        super.onCreate();

        new FlurryAgent.Builder()
                .withLogEnabled(false)
                .build(this, FLURRY_API_KEY);
    }
}

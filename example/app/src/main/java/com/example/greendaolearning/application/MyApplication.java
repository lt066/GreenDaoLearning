package com.example.greendaolearning.application;

import android.app.Application;

import com.example.greendaolearning.util.DbHelper;

/**
 * Created by Administrator on 2017/1/3.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper.getInstance(this);
    }
}

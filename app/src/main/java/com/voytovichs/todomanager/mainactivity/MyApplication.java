package com.voytovichs.todomanager.mainactivity;

import android.app.Application;

import com.voytovichs.todomanager.dao.TaskHelperFactory;

/**
 * Created by itegulov on 10/07/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TaskHelperFactory.setHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        TaskHelperFactory.releaseHelper();
        super.onTerminate();
    }
}

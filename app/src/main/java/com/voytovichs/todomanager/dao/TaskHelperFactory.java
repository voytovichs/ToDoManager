package com.voytovichs.todomanager.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by itegulov on 10/07/15.
 */
public class TaskHelperFactory {
    public static final String TAG = TaskHelperFactory.class.getSimpleName();
    private static TaskDatabaseHelper databaseHelper;

    public static TaskDatabaseHelper getHelper(){
        return databaseHelper;
    }
    public static void setHelper(Context context){
        databaseHelper = OpenHelperManager.getHelper(context, TaskDatabaseHelper.class);
        Log.v(TAG, "Setting helper for " + TAG);
    }
    public static void releaseHelper(){
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
        Log.v(TAG, "Releasing helper for " + TAG);
    }
}

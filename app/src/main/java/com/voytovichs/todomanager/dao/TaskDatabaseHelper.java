package com.voytovichs.todomanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.voytovichs.todomanager.mainactivity.TaskItem;

import java.sql.SQLException;

/**
 * Created by itegulov on 10/07/15.
 */
public class TaskDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = TaskDatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "innovativetodomanager.db";

    private static final int DATABASE_VERSION = 8;

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.v(TAG, "Creating tables for " + TAG);
        try {
            TableUtils.createTable(connectionSource, TaskItem.class);
        } catch (SQLException e) {
            Log.e(TAG, "Error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.v(TAG, "Upgrading tables for " + TAG);
        try {
            TableUtils.dropTable(connectionSource, TaskItem.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Error upgrading db " + DATABASE_NAME + " from ver " + oldVersion);
            throw new RuntimeException(e);
        }
    }

    private TaskDAO taskDAO;

    public synchronized TaskDAO getTaskDAO() throws SQLException {
        if (taskDAO == null) {
            taskDAO = new TaskDAO(getConnectionSource(), TaskItem.class);
        }
        return taskDAO;
    }
}

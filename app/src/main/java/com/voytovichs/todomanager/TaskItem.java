package com.voytovichs.todomanager;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by voytovichs on 03.07.15.
 */
public class TaskItem {

    public enum Status {COMPLETED, INCOMPLETED}

    public static final String ITEM_SEP = System.getProperty("line.separator");
    public final static String TITLE = "title";
    public final static String STATUS = "status";
    public final static String DATE = "date";
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy h:mm a");

    private String mTitle = "";
    private Status mStatus = Status.INCOMPLETED;
    private Date mDate = new Date();

    public TaskItem(String tittle, Status status, Date date) {
        this.mTitle = tittle;
        this.mStatus = status;
        this.mDate = date;
    }

    public TaskItem(Intent intent) {

        mTitle = intent.getStringExtra(TaskItem.TITLE);
        mStatus = Status.valueOf(intent.getStringExtra(TaskItem.STATUS));

        try {
            mDate = TaskItem.FORMAT.parse(intent.getStringExtra(TaskItem.DATE));
        } catch (ParseException e) {
            mDate = new Date();
        }
    }

    public static void packageIntent(Intent intent, String title, Status status, String date) {
        intent.putExtra(TaskItem.TITLE, title);
        intent.putExtra(TaskItem.STATUS, status.toString());
        intent.putExtra(TaskItem.DATE, date);
    }

    public String getTittle() {
        return mTitle;
    }

    public Status getmStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String toString() {
        return mTitle + "\n" + mStatus + "\n" + FORMAT.format(mDate);
    }

}

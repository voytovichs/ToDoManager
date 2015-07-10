package com.voytovichs.todomanager.addtaskactivity;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by voytovichs on 03.07.15.
 */
public class TaskItem {

    public enum Status {COMPLETED, INCOMPLETED}

    private final static String ITEM_SEP = System.getProperty("line.separator");
    private final static String TITLE = "title";
    private final static String STATUS = "status";
    private final static String DATE = "date";
    private final static String COMMENT = "comment";

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy h:mm a");

    private String mTitle = "";
    private String mComment = "";
    private Status mStatus = Status.INCOMPLETED;
    private Date mDate = new Date();

    public TaskItem(String tittle, String comment, Status status, Date date) {
        this.mTitle = tittle;
        this.mStatus = status;
        this.mDate = date;
        this.mComment = comment;
    }

    public TaskItem(Intent intent) {

        mTitle = intent.getStringExtra(TaskItem.TITLE);
        mStatus = Status.valueOf(intent.getStringExtra(TaskItem.STATUS));
        mComment = intent.getStringExtra(TaskItem.COMMENT);

        try {
            mDate = TaskItem.FORMAT.parse(intent.getStringExtra(TaskItem.DATE));
        } catch (ParseException e) {
            mDate = new Date();
        }
    }

    public static void packageIntent(Intent intent, String title, String comment, Status status, String date) {
        intent.putExtra(TaskItem.TITLE, title);
        intent.putExtra(TaskItem.STATUS, status.toString());
        intent.putExtra(TaskItem.DATE, date);
        intent.putExtra(TaskItem.COMMENT, comment);
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

    public String getComment() {
        return mComment;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String toString() {
        return mTitle + "\n" + mStatus + "\n" + FORMAT.format(mDate);
    }

}

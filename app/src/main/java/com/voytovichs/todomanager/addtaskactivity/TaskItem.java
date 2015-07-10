package com.voytovichs.todomanager.addtaskactivity;

import android.content.Intent;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by voytovichs on 03.07.15.
 */
@DatabaseTable(tableName = "tasks")
public class TaskItem {

    public enum Status {COMPLETED, INCOMPLETED}

    private final static String ITEM_SEP = System.getProperty("line.separator");
    private final static String TITLE = "title";
    private final static String STATUS = "status";
    private final static String DATE = "date";
    private final static String COMMENT = "comment";

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.ENGLISH);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "title")
    private String mTitle;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "comment")
    private String mComment;

    //@DatabaseField(canBeNull = false, dataType = DataType.ENUM, columnName = "status")
    private Status mStatus = Status.INCOMPLETED;

    @DatabaseField(canBeNull = false, dataType = DataType.DATE, columnName = "date")
    private Date mDate = new Date();

    public TaskItem() {

    }

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

    public String getTitle() {
        return mTitle;
    }

    public Status getStatus() {
        return mStatus;
    }

    public Date getDate() {
        return mDate;
    }

    public String getComment() {
        return mComment;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String toString() {
        return mTitle + "\n" + mStatus + "\n" + FORMAT.format(mDate);
    }

}

package com.voytovichs.todomanager.mainactivity;

import android.content.Intent;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by voytovichs on 03.07.15.
 */
@DatabaseTable(tableName = "tasks")
public class TaskItem {

    public enum Status {COMPLETED, INCOMPLETED}

    private final static String TITLE = "title";
    private final static String STATUS = "status";
    private final static String DATE = "date";
    private final static String TIME = "time";
    private final static String COMMENT = "comment";

    // public final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.ENGLISH);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = TITLE)
    private String mTitle;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = COMMENT)
    private String mComment;

    //@DatabaseField(canBeNull = false, dataType = DataType.ENUM, columnName = STATUS)
    private Status mStatus = Status.INCOMPLETED;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = DATE)
    private String mDate;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = TIME)
    private String mTime;

    public TaskItem() {
    }

    public TaskItem(String tittle, String comment, String status, String date, String time) {
        this.mTitle = tittle;

        switch (status) {
            case "COMPLETED":
                this.mStatus = Status.COMPLETED;
                break;
            case "INCOMPLETED":
                this.mStatus = Status.INCOMPLETED;
                break;
            default:
                throw new IllegalArgumentException("Status string value is not a valid status");
        }

        this.mDate = date;
        this.mComment = comment;
        this.mTime = time;
    }

    public TaskItem(Intent intent) {

        mTitle = intent.getStringExtra(TaskItem.TITLE);
        mStatus = Status.valueOf(intent.getStringExtra(TaskItem.STATUS));
        mComment = intent.getStringExtra(TaskItem.COMMENT);
        mDate = intent.getStringExtra(TaskItem.DATE);
        mTime = intent.getStringExtra(TaskItem.TIME);
        this.mStatus = Status.INCOMPLETED;
    }

    public static Intent packageIntent(String title, String comment, Status status, String date, String time) {
        Intent data = new Intent();
        data.putExtra(TaskItem.TITLE, title);
        data.putExtra(TaskItem.STATUS, status.toString());
        data.putExtra(TaskItem.DATE, date);
        data.putExtra(TaskItem.COMMENT, comment);
        data.putExtra(TaskItem.TIME, time);
        return data;
    }

    public String getTitle() {
        return mTitle;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public String getDate() {
        return mDate;
    }

    public String getComment() {
        return mComment;
    }

    public String getTime() {
        return mTime;
    }


    public String toString() {
        return mTitle + "\n" + mComment + "\n" + mStatus + "\n" + mDate + "\n" + mTime;
    }

}

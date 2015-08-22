package com.voytovichs.todomanager.activity.mainactivity;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voytovichs on 26.07.15.
 */
public class DefaultTutorialIntents {

    public static List<Intent> getTutorialIntents() {

        List<Intent> list = new ArrayList<>();

        Intent deleteIntent = new Intent();
        deleteIntent.putExtra(TaskItem.TITLE, "Swipe to delete");
        deleteIntent.putExtra(TaskItem.COMMENT, "That's all you need to know");
        setDefaultParameters(deleteIntent);
        list.add(deleteIntent);

        Intent editIntent = new Intent();
        editIntent.putExtra(TaskItem.TITLE, "Touch a bit longer to edit task");
        editIntent.putExtra(TaskItem.COMMENT, "You do it wrong\n" +
                "Touch longer!");
        setDefaultParameters(editIntent);
        list.add(editIntent);

        Intent commentIntent = new Intent();
        commentIntent.putExtra(TaskItem.TITLE, "Touch to see a description");
        commentIntent.putExtra(TaskItem.COMMENT, "This is a description!");
        setDefaultParameters(commentIntent);
        list.add(commentIntent);

        return list;
    }

    private static void setDefaultParameters(Intent intent) {
        intent.putExtra(TaskItem.STATUS, "INCOMPLETED");
        intent.putExtra(TaskItem.DATE, "26.07.2015");
        intent.putExtra(TaskItem.TIME, "23:59");
    }
}

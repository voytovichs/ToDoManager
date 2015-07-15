package com.voytovichs.todomanager.mainactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.addtaskactivity.AddTaskItemActivity;
import com.voytovichs.todomanager.dao.TaskDAO;
import com.voytovichs.todomanager.dao.TaskHelperFactory;
import com.voytovichs.todomanager.mainactivity.adapters.ListViewAdapter;
import com.voytovichs.todomanager.mainactivity.adapters.ListViewAdapter.editableElements;
import com.voytovichs.todomanager.mainactivity.layouts.FloatingActionButton;

import java.sql.SQLException;


public class MainActivity extends AppCompatActivity implements editableElements {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int BUTTON_ICON_SIZE = 70;
    private static final int ADD_TODO_ITEM_REQUEST = 1;
    private TaskDAO taskDAO;
    private FloatingActionButton mButton;


    private ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        TaskHelperFactory.setHelper(getApplicationContext());


        ListView list = (ListView) findViewById(R.id.listView);
        mAdapter = new ListViewAdapter(this);
        list.setAdapter(mAdapter);
        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        list.setLayoutParams(mParam);
        setFloatingButton();

        try {
            taskDAO = TaskHelperFactory.getHelper().getTaskDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setFloatingButton() {

        Drawable dr = getResources().getDrawable(R.drawable.add_button);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable buttonIcon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, true));
        mButton = new FloatingActionButton.Builder(this)
                .withDrawable(buttonIcon)
                .withButtonColor(getResources().getColor(R.color.primaryColorDark))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewElement(ADD_TODO_ITEM_REQUEST);
            }
        });
    }

    private void addNewElement(int finalPosition) {
        Intent toDoIntent = new Intent(MainActivity.this, AddTaskItemActivity.class);
        startActivityForResult(toDoIntent, finalPosition);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_TODO_ITEM_REQUEST && resultCode == RESULT_OK) {
            TaskItem item = new TaskItem(data);
            mAdapter.add(item);
        } else if (resultCode == RESULT_OK) {
            TaskItem item = new TaskItem(data);
            mAdapter.add(requestCode, item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter.getCount() == 0) {
            loadItems();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveItems();
    }

    private void saveItems() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            try {
                Log.d(TAG, "Adding task " + mAdapter.getItem(i));
                taskDAO.createOrUpdate(mAdapter.getItem(i));
            } catch (SQLException e) {
                Log.e(TAG, "Couldn't save task to database: " + e);
            }
        }
    }

    private void loadItems() {
        mAdapter.clear();
        try {
            for (TaskItem taskItem : taskDAO.queryForAll()) {
                Log.d(TAG, "Loading task " + taskItem);
                mAdapter.add(taskItem);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Couldn't load tasks: " + e);
        }
    }

    @Override
    public void editElement(int position) {
        addNewElement(position);
    }

    @Override
    public void deleteFromDB(int position) {
        try {
            taskDAO.delete(mAdapter.getItem(position));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

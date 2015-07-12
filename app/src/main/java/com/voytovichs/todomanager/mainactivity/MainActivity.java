package com.voytovichs.todomanager.mainactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.addtaskactivity.AddTaskItemActivity;
import com.voytovichs.todomanager.dao.TaskHelperFactory;
import com.voytovichs.todomanager.mainactivity.adapters.ListViewAdapter;
import com.voytovichs.todomanager.mainactivity.layouts.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private static final int BUTTON_ICON_SIZE = 70;
    private static final int ADD_TODO_ITEM_REQUEST = 1;

    private ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        TaskHelperFactory.setHelper(getApplicationContext());


        ListView list = (ListView) findViewById(R.id.listView);
        mAdapter = new ListViewAdapter(this);
        list.setAdapter(mAdapter);

        setFloatingButton();
    }

    private void setFloatingButton() {

        Drawable dr = getResources().getDrawable(R.drawable.add_button);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable buttonIcon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, true));
        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(buttonIcon)
                .withButtonColor(getResources().getColor(R.color.primaryColor))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDoIntent = new Intent(MainActivity.this, AddTaskItemActivity.class);
                startActivityForResult(toDoIntent, ADD_TODO_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_TODO_ITEM_REQUEST && resultCode == RESULT_OK) {
            TaskItem item = new TaskItem(data);
            mAdapter.add(item);
        }

    }


}

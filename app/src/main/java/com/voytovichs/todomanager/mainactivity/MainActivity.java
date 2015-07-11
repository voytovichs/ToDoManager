package com.voytovichs.todomanager.mainactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.dao.TaskHelperFactory;
import com.voytovichs.todomanager.mainactivity.layouts.FloatingActionButton;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        TaskHelperFactory.setHelper(getApplicationContext());

        setFloatingButton();
    }

    private void setFloatingButton() {
        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.add_button))
                        //.withButtonColor(R.color.primaryColor)
                .withButtonColor(getResources().getColor(R.color.primaryColor))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
    }


}

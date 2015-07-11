package com.voytovichs.todomanager.mainactivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.dao.TaskHelperFactory;
import com.voytovichs.todomanager.mainactivity.layouts.FloatingActionButton;


public class MainActivity extends Activity {

    private static int BUTTON_ICON_SIZE = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        TaskHelperFactory.setHelper(getApplicationContext());

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
    }


}

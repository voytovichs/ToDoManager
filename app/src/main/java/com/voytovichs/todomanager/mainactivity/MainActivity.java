package com.voytovichs.todomanager.mainactivity;

import android.app.Activity;
import android.os.Bundle;

import com.voytovichs.todomanager.dao.TaskHelperFactory;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskHelperFactory.setHelper(getApplicationContext());
    }

    
}

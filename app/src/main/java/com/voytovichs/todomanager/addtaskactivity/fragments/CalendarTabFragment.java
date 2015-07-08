package com.voytovichs.todomanager.addtaskactivity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voytovichs.todomanager.R;

/**
 * Created by voytovichs on 08.07.15.
 */
public class CalendarTabFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar_tab, container, false);
    }
}

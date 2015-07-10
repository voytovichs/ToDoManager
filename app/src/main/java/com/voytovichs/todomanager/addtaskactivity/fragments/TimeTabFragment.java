package com.voytovichs.todomanager.addtaskactivity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.voytovichs.todomanager.R;

/**
 * Created by voytovichs on 10.07.15.
 */
public class TimeTabFragment extends android.support.v4.app.Fragment {

    TimeListener mCallback;

    // Container Activity must implement this interface
    public interface TimeListener {
        void sendTime(String dateString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_tab, container, false);
        setTimePicker(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TimeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TimeListener");
        }
    }

    private String getStringTime(TimePicker clock) {
        int hours = clock.getCurrentHour();
        int minutes = clock.getCurrentMinute();
        return timeToString(hours, minutes);
    }

    private String timeToString(int hour, int minutes) {
        return hour + ":" + minutes;
    }

    private void setTimePicker(View mainView) {
        final TimePicker clock = (TimePicker) mainView.findViewById(R.id.timePicker);
        clock.setIs24HourView(true);
        mCallback.sendTime(getStringTime(clock));
        clock.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mCallback.sendTime(timeToString(hourOfDay, minute));
            }
        });
    }
}

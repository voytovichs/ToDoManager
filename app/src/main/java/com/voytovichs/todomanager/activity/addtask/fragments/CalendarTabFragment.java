package com.voytovichs.todomanager.activity.addtask.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.voytovichs.todomanager.R;

/**
 * Created by voytovichs on 08.07.15.
 */
public class CalendarTabFragment extends android.support.v4.app.Fragment {

    private static final String TAG = CalendarTabFragment.class.getSimpleName();
    CalendarListener mCallback;

    // Container Activity must implement this interface
    public interface CalendarListener {
        void sendDate(String dateString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, TAG + "creating");
        View view = inflater.inflate(R.layout.calendar_tab, container, false);
        setDatePicker(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            Log.e(TAG, "Set callback to " + activity);
            mCallback = (CalendarListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, activity + " doesn't implement CalendarListener interface");
            throw new ClassCastException(activity.toString()
                    + " must implement Calendar");
        }
    }

    private String getStringDate(DatePicker dp) {
        int year = dp.getYear();
        int month = dp.getMonth();
        int day = dp.getDayOfMonth();
        return toStringDate(year, month, day);
    }

    private String toStringDate(int year, int month, int day) {
        if (month + 1 < 10) {
            return day + "." + "0" + (month + 1) + "." + year;
        }
        return day + "." + (month + 1) + "." + year;
    }

    private void setDatePicker(View mainView) {
        final DatePicker mCalendar = (DatePicker) mainView.findViewById(R.id.datePicker);
        mCallback.sendDate(getStringDate(mCalendar));
        mCalendar.init(mCalendar.getYear(), mCalendar.getMonth(), mCalendar.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e(TAG, "Sending date to " + getActivity());
                mCallback.sendDate(toStringDate(year, monthOfYear, dayOfMonth));
            }
        });
    }
}

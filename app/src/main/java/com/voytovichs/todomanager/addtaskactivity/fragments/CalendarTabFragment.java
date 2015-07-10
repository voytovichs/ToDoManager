package com.voytovichs.todomanager.addtaskactivity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.voytovichs.todomanager.R;

/**
 * Created by voytovichs on 08.07.15.
 */
public class CalendarTabFragment extends android.support.v4.app.Fragment {

    CalendarListener mCallback;

    // Container Activity must implement this interface
    public interface CalendarListener {
        void sendDate(String dateString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            mCallback = (CalendarListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainPageItemsListener");
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
        final DatePicker calendar = (DatePicker) mainView.findViewById(R.id.datePicker);
        mCallback.sendDate(getStringDate(calendar));
        calendar.init(calendar.getYear(), calendar.getMonth(), calendar.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCallback.sendDate(toStringDate(year, monthOfYear, dayOfMonth));
            }
        });
    }


}

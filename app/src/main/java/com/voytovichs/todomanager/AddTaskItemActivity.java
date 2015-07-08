package com.voytovichs.todomanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.voytovichs.todomanager.adapters.ViewPagerAdapter;
import com.voytovichs.todomanager.layouts.SlidingTabLayout;

/**
 * Created by voytovichs on 05.07.15.
 */
public class AddTaskItemActivity extends ActionBarActivity {

    private final static CharSequence[] tabTittles = {"Description", "Date"};

    private EditText mTittle = null;
    private Toolbar mToolbar = null;
    private ViewPager mViewPager = null;
    private ViewPagerAdapter mViewPagerAdapter = null;
    private SlidingTabLayout mTabs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_task_layout);

        setToolbar();
        setTabs();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);
    }

    private void setTabs() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabTittles);
        mViewPager = (ViewPager) findViewById(R.id.tabs_view_pager);
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mViewPager);
    }


    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboardFromTitle() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTittle.getWindowToken(), 0);
    }

}

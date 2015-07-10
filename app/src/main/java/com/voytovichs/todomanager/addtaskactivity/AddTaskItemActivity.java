package com.voytovichs.todomanager.addtaskactivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.addtaskactivity.adapters.ViewPagerAdapter;
import com.voytovichs.todomanager.addtaskactivity.fragments.CalendarTabFragment;
import com.voytovichs.todomanager.addtaskactivity.fragments.NameTabFragment;
import com.voytovichs.todomanager.addtaskactivity.fragments.TimeTabFragment;
import com.voytovichs.todomanager.addtaskactivity.layouts.SlidingTabLayout;
import com.voytovichs.todomanager.dao.TaskDAO;
import com.voytovichs.todomanager.dao.TaskHelperFactory;

import java.sql.SQLException;

/**
 * Created by voytovichs on 05.07.15.
 */
public class AddTaskItemActivity extends AppCompatActivity implements NameTabFragment.MainPageItemsListener,
        CalendarTabFragment.CalendarListener, TimeTabFragment.TimeListener {
    private static final String TAG = AddTaskItemActivity.class.getSimpleName();

    private final static CharSequence[] tabTittles = {"Title", "Date", "Time"};

    private Toolbar mToolbar = null;
    private ViewPager mViewPager = null;
    private ViewPagerAdapter mViewPagerAdapter = null;
    private SlidingTabLayout mTabs = null;
    private String titleText = null;
    private String description = null;
    private String dateString = null;
    private String timeString = null;
    private TaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            taskDAO = TaskHelperFactory.getHelper().getTaskDAO();
            // Now you can call taskDao.create(taskItem) to add taskItem to database (but I didn't
            // test it properly, so I don't guarantee that it works).
        } catch (SQLException e) {
            Log.e(TAG, "Couldn't load DAO for tasks");
        }

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

    @Override
    public void sendTitleText(String title) {
        this.titleText = title;
    }

    @Override
    public void sendCommentText(String comment) {
        this.description = comment;
    }

    @Override
    public void sendDate(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public void sendTime(String timeString) {
        this.timeString = timeString;
    }
}

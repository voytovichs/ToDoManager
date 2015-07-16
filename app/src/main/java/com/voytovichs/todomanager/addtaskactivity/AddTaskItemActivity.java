package com.voytovichs.todomanager.addtaskactivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.addtaskactivity.adapters.ViewPagerAdapter;
import com.voytovichs.todomanager.addtaskactivity.fragments.CalendarTabFragment;
import com.voytovichs.todomanager.addtaskactivity.fragments.NameTabFragment;
import com.voytovichs.todomanager.addtaskactivity.fragments.TimeTabFragment;
import com.voytovichs.todomanager.addtaskactivity.layouts.SlidingTabLayout;
import com.voytovichs.todomanager.mainactivity.TaskItem;
import com.voytovichs.todomanager.mainactivity.TaskItem.Status;

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
    private String descriptionText = null;
    private String dateString = null;
    private String timeString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_task_activity_layout);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mViewPagerAdapter.closeInputs();
        switch (item.getItemId()) {
            case R.id.add_task_menu_done: {
                if (titleText == null || titleText.equals("")) {
                    titleText = getResources().getString(R.string.no_title_string);
                }
                if (timeString == null) {
                    timeString = getResources().getString(R.string.no_time_string);
                }
                if (descriptionText == null) {
                    descriptionText = getResources().getString(R.string.no_description_string);
                }
                if (dateString == null) {
                    dateString = getResources().getString(R.string.no_date_string);
                }
                Status status = Status.INCOMPLETED;
                if (getIntent().hasExtra(TaskItem.STATUS)) {
                    status = Status.valueOf(getIntent().getStringExtra(TaskItem.STATUS));
                }
                setResult(RESULT_OK, TaskItem.packageIntent(titleText, descriptionText, status, dateString, timeString));
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendTitleText(String title) {
        this.titleText = title;
    }

    @Override
    public void sendCommentText(String comment) {
        this.descriptionText = comment;
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

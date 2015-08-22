package com.voytovichs.todomanager.activity.mainactivity.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.activity.mainactivity.TaskItem;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by voytovichs on 12.07.15.
 */
public class ListViewAdapter extends BaseAdapter {

    private static final String TAG = ListViewAdapter.class.getSimpleName();

    public interface editableElements {
        void editElement(int position);

        void deleteElement(int position);
    }

    private final Context mContext;
    private final List<TaskItem> mData;
    private final editableElements activity;

    private boolean hasHandReleased = true;
    private boolean hasBottomViewCovered = true;

    public ListViewAdapter(Context context) {
        mContext = context;
        mData = new LinkedList<>();
        try {
            this.activity = (editableElements) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement editableElements");
        }
    }

    public void clear() {
        Log.e(TAG, "Clear task container");
        mData.clear();
    }

    public void add(TaskItem item) {
        Log.e(TAG, "Add task on the top of the list");
        mData.add(0, item);
        notifyDataSetChanged();
    }

    public void add(int position, TaskItem item) {
        Log.e(TAG, "Add task to " + position + " position");
        mData.add(position, item);
        notifyDataSetChanged();
    }

    public void delete(int position) {
        Log.e(TAG, "Delete task from " + position + " position");
        //Don't touch the order!
        activity.deleteElement(position);
        mData.remove(position);

        notifyDataSetChanged();
    }

    public Collection<TaskItem> getCollection() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public TaskItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final TaskItem taskItem = mData.get(position);

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final SwipeLayout listItem = (SwipeLayout) mInflater.inflate(R.layout.list_item, null);
        listItem.setShowMode(SwipeLayout.ShowMode.LayDown);
        listItem.addDrag(SwipeLayout.DragEdge.Top, listItem.findViewById(R.id.bottom_wrapper));
        listItem.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                Log.e(TAG, "Swipe: start");
                hasHandReleased = false;
                hasBottomViewCovered = false;
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                Log.e(TAG, "Swipe: bottom view totally show");
                delete(position);
            }

            @Override
            public void onClose(SwipeLayout layout) {
                Log.e(TAG, "Swipe: surface view totally cover the bottom view");
                hasBottomViewCovered = true;
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                Log.e(TAG, "Swipe: start closing");
                //do nothing
            }


            @Override
            public void onHandRelease(SwipeLayout swipeLayout, float v, float v1) {
                Log.e(TAG, "Swipe: hand release");
                hasHandReleased = true;
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                hasHandReleased = false;
                hasBottomViewCovered = false;
            }

        });

        final TextView titleView = (TextView) listItem.findViewById(R.id.listTitle);
        titleView.setText(taskItem.getTitle());

        final CheckBox statusView = (CheckBox) listItem.findViewById(R.id.statusCheckBox);
        statusView.setChecked(taskItem.getStatus().equals(TaskItem.Status.COMPLETED));
        statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                Log.e(TAG, "Change comboBoxValue");
                if (taskItem.getStatus().equals(TaskItem.Status.COMPLETED)) {
                    taskItem.setStatus(TaskItem.Status.INCOMPLETED);
                } else {
                    taskItem.setStatus(TaskItem.Status.COMPLETED);
                }
            }
        });
        final TextView dateView = (TextView) listItem.findViewById(R.id.dateTextView);
        dateView.setText(taskItem.getDate());

        final TextView timeView = (TextView) listItem.findViewById(R.id.timeTextView);
        timeView.setText(taskItem.getTime());

        final TextView commentView = (TextView) listItem.findViewById(R.id.listDescription);
        commentView.setText(taskItem.getComment());
        commentView.setVisibility(View.GONE);

        listItem.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.e(TAG, "List item clicked");
                                            //description should not opens when MotionAction is swipe
                                            if (!(hasBottomViewCovered && hasHandReleased)) {
                                                return;
                                            }
                                            if (commentView.getText().equals("")) {
                                                return;
                                            }
                                            if (commentView.isShown()) {
                                                commentView.setVisibility(View.GONE);
                                            } else {
                                                commentView.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
        );


        listItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (hasBottomViewCovered && hasBottomViewCovered) {
                    activity.editElement(position);
                    return true;
                }
                return false;
            }
        });
        return listItem;
    }
}

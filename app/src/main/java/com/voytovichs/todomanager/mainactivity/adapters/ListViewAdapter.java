package com.voytovichs.todomanager.mainactivity.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.mainactivity.TaskItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by voytovichs on 12.07.15.
 */
public class ListViewAdapter extends BaseAdapter {

    public interface editableElements {
        void editElement(int position);

        void deleteFromDB(int position);
    }

    private final Context mContext;
    private final List<TaskItem> mData;
    private final editableElements activity;

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
        mData.clear();
    }

    public void add(TaskItem item) {
        mData.add(0, item);
        notifyDataSetChanged();
    }

    public void add(int position, TaskItem item) {
        mData.add(position, item);
        notifyDataSetChanged();
    }

    public void delete(int position) {
        activity.deleteFromDB(position);
        mData.remove(position);

        notifyDataSetChanged();
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
        final LinearLayout itemLayout = (LinearLayout) mInflater.inflate(R.layout.list_item, null);

        final TextView titleView = (TextView) itemLayout.findViewById(R.id.listTitle);
        titleView.setText(taskItem.getTitle());

        final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
        statusView.setChecked(taskItem.getStatus().equals(TaskItem.Status.COMPLETED));
        statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (taskItem.getStatus().equals(TaskItem.Status.COMPLETED)) {
                    taskItem.setStatus(TaskItem.Status.INCOMPLETED);
                } else {
                    taskItem.setStatus(TaskItem.Status.COMPLETED);
                }
            }
        });
        final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateTextView);
        dateView.setText(taskItem.getDate());

        final TextView timeView = (TextView) itemLayout.findViewById(R.id.timeTextView);
        timeView.setText(taskItem.getTime());

        final TextView commentView = (TextView) itemLayout.findViewById(R.id.listDescription);
        commentView.setText(taskItem.getComment());
        commentView.setVisibility(View.GONE);

        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentView.getText().equals("")) {
                    return;
                }
                if (commentView.isShown()) {
                    commentView.setVisibility(View.GONE);
                } else {
                    commentView.setVisibility(View.VISIBLE);
                }
            }
        });

        itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delete(position);
                activity.editElement(position);
                return true;
            }
        });
        return itemLayout;
    }
}

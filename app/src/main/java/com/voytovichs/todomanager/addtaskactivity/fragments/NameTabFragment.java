package com.voytovichs.todomanager.addtaskactivity.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.voytovichs.todomanager.R;
import com.voytovichs.todomanager.mainactivity.TaskItem;

import java.util.Objects;

/**
 * Created by voytovichs on 08.07.15.
 */
public class NameTabFragment extends android.support.v4.app.Fragment {

    private MainPageItemsListener mCallback;
    private EditText mTitle = null;
    private EditText mComment = null;

    private String titleFromActivity = "";
    private String commentFromActivity = "";

    // Container Activity must implement this interface
    public interface MainPageItemsListener {
        void sendTitleText(String title);

        void sendCommentText(String comment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_tab, container, false);
        setTitleEditText(view);
        setDescriptionEditText(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (MainPageItemsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainPageItemsListener");
        }

        Intent dataIntent = activity.getIntent();
        if (dataIntent.hasExtra(TaskItem.TITLE)) {
            titleFromActivity = dataIntent.getStringExtra(TaskItem.TITLE);
        }
        if (dataIntent.hasExtra(TaskItem.COMMENT)) {
            commentFromActivity = dataIntent.getStringExtra(TaskItem.COMMENT);
        }
    }

    private void setTitleEditText(final View mainView) {
        mTitle = (EditText) mainView.findViewById(R.id.taskTitleTextEdit);
        if (!Objects.equals(titleFromActivity, "")) {
            mTitle.setText(titleFromActivity);
            mCallback.sendTitleText(titleFromActivity);
        }
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard(mTitle);
            }
        });
        mTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(mTitle);
                    mCallback.sendTitleText(mTitle.getText().toString());
                }
            }
        });
        mTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mCallback.sendTitleText(mTitle.getText().toString());
                    hideKeyboard(mTitle);
                    return true;
                }
                return false;
            }
        });
        mTitle.setImeOptions(EditorInfo.IME_ACTION_DONE);
        hideKeyboard(mTitle);
    }

    private void setDescriptionEditText(final View mainView) {
        mComment = (EditText) mainView.findViewById(R.id.taskDescriptionTextEdit);
        if (!Objects.equals(commentFromActivity, "")) {
            mComment.setText(commentFromActivity);
            mCallback.sendCommentText(commentFromActivity);
        }
        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard(mComment);
            }
        });
        mComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(mComment);
                    mCallback.sendCommentText(mComment.getText().toString());
                }
            }
        });
        mComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mCallback.sendCommentText(
                            mComment.getText().toString());
                    hideKeyboard(mComment);
                    return true;
                }
                return false;
            }
        });
        mComment.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    private void showKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void closeInputs() {
        mCallback.sendTitleText(mTitle.getText().toString());
        mCallback.sendCommentText(mComment.getText().toString());
    }
}

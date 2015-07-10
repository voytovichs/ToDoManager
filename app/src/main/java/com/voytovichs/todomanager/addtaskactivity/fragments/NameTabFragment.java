package com.voytovichs.todomanager.addtaskactivity.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.voytovichs.todomanager.R;

/**
 * Created by voytovichs on 08.07.15.
 */
public class NameTabFragment extends android.support.v4.app.Fragment {

    MainPageItemsListener mCallback;

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
    }

    private void setTitleEditText(final View mainView) {
        final EditText editText = (EditText) mainView.findViewById(R.id.taskTitleTextEdit);
        editText.setOnClickListener(v -> showKeyboard(editText));
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(editText);
                mCallback.sendTitleText(editText.getText().toString());
            }
        });
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mCallback.sendTitleText(editText.getText().toString());
                hideKeyboard(editText);
                return true;
            }
            return false;
        });
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    private void setDescriptionEditText(final View mainView) {
        final EditText editText = (EditText) mainView.findViewById(R.id.taskDescriptionTextEdit);
        editText.setOnClickListener(v -> showKeyboard(editText));
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(editText);
                mCallback.sendCommentText(editText.getText().toString());
            }
        });
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mCallback.sendCommentText(
                        editText.getText().toString());
                hideKeyboard(editText);
                return true;
            }
            return false;
        });
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    private void showKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}

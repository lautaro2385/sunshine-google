package com.example.android.sunshine.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Lautaro Realpe on 1/20/2016.
 */
public class LocationEditTextPreference extends EditTextPreference {

    private int mMinLength;
    private final static int DEFAULT_MINIMUM_LOCATION_LENGTH = 2;

    public LocationEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.LocationEditTextPreference, 0, 0);
        try {
            mMinLength = a.getInteger(R.styleable.LocationEditTextPreference_minLength, DEFAULT_MINIMUM_LOCATION_LENGTH);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);

        EditText et = getEditText();
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Dialog d = getDialog();
                if(d instanceof AlertDialog){
                    AlertDialog ad = (AlertDialog)d;
                    Button okButton = ad.getButton(AlertDialog.BUTTON_POSITIVE);
                    if(s.length() < mMinLength)
                        okButton.setEnabled(false);
                    else
                        okButton.setEnabled(true);
                }
            }
        });
    }
}

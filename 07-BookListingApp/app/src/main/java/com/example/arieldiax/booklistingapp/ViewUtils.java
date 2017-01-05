package com.example.arieldiax.booklistingapp;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public final class ViewUtils {

    /**
     * Creates a new ViewUtils object (no, it won't).
     */
    private ViewUtils() {
        // Required empty private constructor (to prevent instantiation).
    }

    /**
     * Gets the edit text value.
     *
     * @param editText Instance of the EditText class.
     * @return The edit text value.
     */
    public static String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * Hides the keyboard.
     *
     * @param activity Instance of the Activity class.
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

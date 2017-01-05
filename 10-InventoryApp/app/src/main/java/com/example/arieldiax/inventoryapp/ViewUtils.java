package com.example.arieldiax.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

    /**
     * Shows an alert dialog.
     *
     * @param activity                      Instance of the Activity class.
     * @param titleResourceId               Resource ID of the title.
     * @param positiveButtonOnClickListener Instance of the DialogInterface.OnClickListener class.
     */
    public static void showAlertDialog(Activity activity, int titleResourceId, DialogInterface.OnClickListener positiveButtonOnClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(titleResourceId);
        alertDialogBuilder.setMessage(R.string.message_are_you_sure);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, positiveButtonOnClickListener);
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.create().show();
    }
}

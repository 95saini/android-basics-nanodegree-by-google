package com.example.arieldiax.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements OnPreferenceChangeListener {

        /**
         * @var mPageSizePreference Preference field for page size.
         */
        private Preference mPageSizePreference;

        /**
         * @var mOrderByPreference Preference field for order by.
         */
        private Preference mOrderByPreference;

        /**
         * @var mMainToast Toast message for interaction buttons.
         */
        private Toast mMainToast;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            initUi();
            init();
            initListeners();
        }

        /**
         * Initializes the user interface view bindings.
         */
        private void initUi() {
            mPageSizePreference = findPreference(getString(R.string.setting_key_page_size));
            mOrderByPreference = findPreference(getString(R.string.setting_key_order_by));
        }

        /**
         * Initializes the back end logic bindings.
         */
        private void init() {
            mMainToast = Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT);
        }

        /**
         * Initializes the event listener view bindings.
         */
        private void initListeners() {
            bindPreferenceSummaryToValue(mPageSizePreference);
            bindPreferenceSummaryToValue(mOrderByPreference);
        }

        /**
         * Binds the preference summary to its value.
         *
         * @param preference Instance of the Preference class.
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = sharedPreferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            boolean preferenceHasPassedFlag = isValidPreference(preference, newValue);
            String newValueString = newValue.toString();
            newValueString = (!TextUtils.isEmpty(newValueString) && TextUtils.isDigitsOnly(newValueString)) ? String.valueOf(Integer.parseInt(newValueString)) : newValueString;
            if (preferenceHasPassedFlag) {
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    int preferenceIndex = listPreference.findIndexOfValue(newValueString);
                    if (preferenceIndex != -1) {
                        CharSequence[] entries = listPreference.getEntries();
                        preference.setSummary(entries[preferenceIndex]);
                    }
                } else {
                    preference.setSummary(newValueString);
                }
            }
            return preferenceHasPassedFlag;
        }

        /**
         * Determines whether or not the general preference is valid.
         *
         * @param preference The changed Preference.
         * @param newValue   The new value of the Preference.
         * @return Whether or not the general preference is valid.
         */
        private boolean isValidPreference(Preference preference, Object newValue) {
            boolean preferenceHasPassedFlag = true;
            String preferenceKey = preference.getKey();
            String newValueString = newValue.toString();
            int newValueInt = (!TextUtils.isEmpty(newValueString) && TextUtils.isDigitsOnly(newValueString)) ? Integer.parseInt(newValueString) : 0;
            if (preferenceKey.equals(getString(R.string.setting_key_page_size))) {
                preferenceHasPassedFlag = isValidPageSize(newValueInt);
                mMainToast.setText(R.string.message_cannot_select_zero);
            }
            if (TextUtils.isEmpty(newValueString)) {
                mMainToast.setText(R.string.message_cannot_leave_blank);
            }
            if (!preferenceHasPassedFlag) {
                mMainToast.show();
            }
            return preferenceHasPassedFlag;
        }

        /**
         * Determines whether or not the page_size preference is valid.
         *
         * @param pageSize Preference to validate.
         * @return Whether or not the page_size preference is valid.
         */
        private boolean isValidPageSize(int pageSize) {
            return (pageSize > 0);
        }
    }
}

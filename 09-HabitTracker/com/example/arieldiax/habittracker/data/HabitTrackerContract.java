package com.example.arieldiax.habittracker.data;

import android.provider.BaseColumns;

public final class HabitTrackerContract {

    /**
     * Creates a new HabitTrackerContract object (no, it won't).
     */
    private HabitTrackerContract() {
        // Required empty private constructor (to prevent instantiation).
    }

    public static final class HabitEntry implements BaseColumns {

        /**
         * @const TABLE_NAME Name of the table.
         */
        public static final String TABLE_NAME = "habits";

        /**
         * @const COLUMN_NAME_* Name of the columns.
         */
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_OCCURRENCES = "occurrences";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";

        /**
         * @const COLUMN_KEY_TYPE_* Keys of the column (type).
         */
        public static final int COLUMN_KEY_TYPE_UNKNOWN = 0;
        public static final int COLUMN_KEY_TYPE_BAD = 1;
        public static final int COLUMN_KEY_TYPE_GOOD = 2;

        /**
         * @const COLUMN_VALUE_TYPE_* Values of the column (type).
         */
        public static final String COLUMN_VALUE_TYPE_UNKNOWN = "Unknown";
        public static final String COLUMN_VALUE_TYPE_BAD = "Bad";
        public static final String COLUMN_VALUE_TYPE_GOOD = "Good";

        /**
         * @const COLUMN_LIST_KEYS_TYPE List of keys of the column (type).
         */
        public static final String[] COLUMN_LIST_KEYS_TYPE = new String[]{
                COLUMN_KEY_TYPE_UNKNOWN,
                COLUMN_KEY_TYPE_BAD,
                COLUMN_KEY_TYPE_GOOD,
        };

        /**
         * @const COLUMN_LIST_VALUES_TYPE List of values of the column (type).
         */
        public static final String[] COLUMN_LIST_VALUES_TYPE = new String[]{
                COLUMN_VALUE_TYPE_UNKNOWN,
                COLUMN_VALUE_TYPE_BAD,
                COLUMN_VALUE_TYPE_GOOD,
        };

        /**
         * @const SQL_STATEMENT_CREATE_TABLE SQL statement to create the table.
         */
        public static final String SQL_STATEMENT_CREATE_TABLE = "" +
                "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                COLUMN_NAME_OCCURRENCES + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_NAME_TYPE + " INTEGER NOT NULL DEFAULT " + COLUMN_KEY_TYPE_UNKNOWN + ", " +
                COLUMN_NAME_CREATED_AT + " INTEGER NOT NULL DEFAULT (STRFTIME('%s', 'now')), " +
                COLUMN_NAME_UPDATED_AT + " INTEGER" +
                ");";
    }
}

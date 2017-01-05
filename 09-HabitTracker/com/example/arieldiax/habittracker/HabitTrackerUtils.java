package com.example.arieldiax.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.arieldiax.habittracker.data.HabitTrackerContract.HabitEntry;
import com.example.arieldiax.habittracker.data.HabitTrackerSQLiteOpenHelper;

public final class HabitTrackerUtils {

    /**
     * Creates a new HabitTrackerUtils object (no, it won't).
     */
    private HabitTrackerUtils() {
        // Required empty private constructor (to prevent instantiation).
    }

    /**
     * Queries the rows from the habits table.
     *
     * @param context Instance of the Context class.
     * @return The rows from the habits table.
     */
    public static Cursor queryHabits(Context context) {
        SQLiteDatabase database = new HabitTrackerSQLiteOpenHelper(context).getReadableDatabase();
        String[] projection = new String[]{
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME_NAME,
                HabitEntry.COLUMN_NAME_OCCURRENCES,
                HabitEntry.COLUMN_NAME_TYPE,
                HabitEntry.COLUMN_NAME_CREATED_AT,
                HabitEntry.COLUMN_NAME_UPDATED_AT,
        };
        Cursor cursor = null;
        try {
            cursor = database.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, HabitEntry.COLUMN_NAME_CREATED_AT + " DESC");
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            database.close();
        }
        return cursor;
    }

    /**
     * Inserts the rows into the habits table.
     *
     * @param context Instance of the Context class.
     */
    public static void insertHabits(Context context) {
        SQLiteDatabase database = new HabitTrackerSQLiteOpenHelper(context).getWritableDatabase();
        ContentValues habit1 = new ContentValues();
        habit1.put(HabitEntry.COLUMN_NAME_NAME, "Run");
        habit1.put(HabitEntry.COLUMN_NAME_OCCURRENCES, 1);
        habit1.put(HabitEntry.COLUMN_NAME_TYPE, HabitEntry.COLUMN_KEY_TYPE_GOOD);
        habit1.put(HabitEntry.COLUMN_NAME_CREATED_AT, 1451628000); // 2016-01-01T06:00:00Z
        ContentValues habit2 = new ContentValues();
        habit2.put(HabitEntry.COLUMN_NAME_NAME, "Drink");
        habit2.put(HabitEntry.COLUMN_NAME_OCCURRENCES, 2);
        habit2.put(HabitEntry.COLUMN_NAME_TYPE, HabitEntry.COLUMN_KEY_TYPE_BAD);
        habit2.put(HabitEntry.COLUMN_NAME_CREATED_AT, 1451671200); // 2016-01-01T18:00:00Z
        try {
            database.insert(HabitEntry.TABLE_NAME, null, habit1);
            database.insert(HabitEntry.TABLE_NAME, null, habit2);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            database.close();
        }
    }
}

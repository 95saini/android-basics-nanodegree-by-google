package com.example.arieldiax.inventoryapp;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Utils {

    /**
     * Creates a new Utils object (no, it won't).
     */
    private Utils() {
        // Required empty private constructor (to prevent instantiation).
    }

    /**
     * Determines whether or not the object is a null.
     *
     * @param object Object to compare.
     * @return Whether or not the object is a null.
     */
    public static boolean isNull(Object object) {
        return (object == null);
    }

    /**
     * Determines whether or not the object is a positive.
     *
     * @param object Object to compare.
     * @return Whether or not the object is a positive.
     */
    public static boolean isPositive(Integer object) {
        return (object >= 0);
    }

    /**
     * Determines whether or not the object is a positive.
     *
     * @param object Object to compare.
     * @return Whether or not the object is a positive.
     */
    public static boolean isPositive(Double object) {
        return (object >= 0.0);
    }

    /**
     * Formats the timestamp.
     *
     * @param timestampSeconds Seconds of the timestamp.
     * @param outcomePattern   Pattern of the outcome.
     * @return The timestamp formatted.
     */
    public static String formatTimestamp(long timestampSeconds, String outcomePattern) {
        return (new SimpleDateFormat(outcomePattern, Locale.getDefault())).format(timestampSeconds);
    }
}

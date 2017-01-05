package com.example.arieldiax.booklistingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Utils {

    /**
     * Creates a new Utils object (no, it won't).
     */
    private Utils() {
        // Required empty private constructor (to prevent instantiation).
    }

    /**
     * Formats the date.
     *
     * @param dateString     String of the date.
     * @param incomePattern  Pattern of the income.
     * @param outcomePattern Pattern of the outcome.
     * @return The date formatted.
     */
    public static String formatDate(String dateString, String incomePattern, String outcomePattern) {
        Date date = null;
        try {
            date = (new SimpleDateFormat(incomePattern, Locale.getDefault())).parse(dateString);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        if (date != null) {
            return (new SimpleDateFormat(outcomePattern, Locale.getDefault())).format(date);
        }
        return dateString;
    }
}

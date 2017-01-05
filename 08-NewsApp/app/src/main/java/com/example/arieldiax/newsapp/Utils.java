package com.example.arieldiax.newsapp;

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
     * Capitalizes the text.
     *
     * @param textString String of the text.
     * @return The text capitalized.
     */
    public static String capitalizeText(String textString) {
        return String.valueOf(textString.charAt(0)).toUpperCase() + textString.substring(1).toLowerCase();
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

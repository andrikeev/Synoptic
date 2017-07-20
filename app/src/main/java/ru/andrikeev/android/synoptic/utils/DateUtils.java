package ru.andrikeev.android.synoptic.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class for converting dates.
 */
public class DateUtils {

    public static String formatDate(Date date) {
        DateFormat shortenedDateFormat = SimpleDateFormat.getDateTimeInstance();
        return shortenedDateFormat.format(date);
    }
}

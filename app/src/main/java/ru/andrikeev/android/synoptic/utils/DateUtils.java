package ru.andrikeev.android.synoptic.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.text.DateFormat.SHORT;

/**
 * Helper class for converting dates.
 */
public class DateUtils {

    public static String formatDate(Date date) {
        DateFormat shortenedDateFormat = SimpleDateFormat.getDateTimeInstance(SHORT, SHORT);
        return shortenedDateFormat.format(date);
    }
}

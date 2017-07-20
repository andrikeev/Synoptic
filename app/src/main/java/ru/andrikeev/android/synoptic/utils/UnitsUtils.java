package ru.andrikeev.android.synoptic.utils;

/**
 * Helper class for converting units.
 */
public class UnitsUtils {

    public enum Units {
        METRIC,
        IMPERIAL
    }

    public static int formatTemperature(float temperature, Units units) {
        if (units == Units.METRIC) {
            return Math.round(temperature);
        } else {
            return (int) Math.round((temperature * 1.8) + 32);
        }
    }

    public static int round(float unit) {
        return Math.round(unit);
    }
}

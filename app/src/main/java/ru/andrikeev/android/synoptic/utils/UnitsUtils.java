package ru.andrikeev.android.synoptic.utils;

import ru.andrikeev.android.synoptic.utils.units.TemperatureUnits;

/**
 * Helper class for converting units.
 */
public class UnitsUtils {

    public static int formatTemperature(float temperature, TemperatureUnits units) {
        switch (units) {
            case CELSIUS:
                return Math.round(temperature - 273.15f);
            case FAHRENHEIT:
                return Math.round(((temperature - 273.15f) * 1.8f) + 32);
            default:
                throw new IllegalStateException(String.format("Unknown temperature unit: %s", units));
        }
    }

    public static int round(float unit) {
        return Math.round(unit);
    }
}

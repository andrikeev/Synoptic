package ru.andrikeev.android.synoptic;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import ru.andrikeev.android.synoptic.utils.UnitsUtils;
import ru.andrikeev.android.synoptic.utils.units.TemperatureUnits;

import static org.junit.Assert.assertEquals;

/**
 * Created by overtired on 27.07.17.
 */

@RunWith(JUnitParamsRunner.class)
public class UnitUtilsTest {

    @Test
    @Parameters({"311, 38", "300, 27", "0, -273"})
    public void fromToCelsius(float temperature, int expTempCelsius){
        int tempCelsius = UnitsUtils.formatTemperature(temperature, TemperatureUnits.CELSIUS);
        assertEquals(expTempCelsius,tempCelsius);
    }

    @Test
    @Parameters({"311, 100", "300, 80", "0, -460"})
    public void fromToFahrenheit(float temperature, int expTempFahrenheit){
        int tempFahrenheit = UnitsUtils.formatTemperature(temperature, TemperatureUnits.FAHRENHEIT);
        assertEquals(expTempFahrenheit,tempFahrenheit);
    }
}

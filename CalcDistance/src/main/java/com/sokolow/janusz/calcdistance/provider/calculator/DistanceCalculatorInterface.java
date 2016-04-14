package com.sokolow.janusz.calcdistance.provider.calculator;

import com.sokolow.janusz.calcdistance.exception.WrongDistanceCalculationMethodException;
import com.sokolow.janusz.calcdistance.model.City;

/**
 *
 * @author Y50
 */
public interface DistanceCalculatorInterface {

    static final double EARTH_RADIUS = 6371.00; // meters
    static final int DEFAULT_PRECISSION = 3; // 1 - low, 2 - medium, 3 - high

    public abstract double countDistance(City c1, City c2) throws WrongDistanceCalculationMethodException;
}

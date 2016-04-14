package com.sokolow.janusz.calcdistance.provider.calculator;

import com.sokolow.janusz.calcdistance.exception.WrongDistanceCalculationMethodException;
import com.sokolow.janusz.calcdistance.model.City;

/**
 *
 * @author Y50
 */
public class HaversineDistanceCalculator implements DistanceCalculatorInterface {

    @Override
    public double countDistance(City c1, City c2) throws WrongDistanceCalculationMethodException {
        double lat1 = c1.getLatitude();
        double lon1 = c1.getLongitude();
        double lat2 = c2.getLatitude();
        double lon2 = c2.getLongitude();
        
        double latDif = (lat2 - lat1) * Math.PI / 180;
        double lonDif = (lon2 - lon1) * Math.PI / 180;

        lat1 = lat1 * Math.PI / 180;    // convert to radians
        lat2 = lat2 * Math.PI / 180;    // convert to radians

        double a = Math.sin(latDif / 2) * Math.sin(latDif / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(lonDif / 2) * Math.sin(lonDif / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * EARTH_RADIUS;
    }

}

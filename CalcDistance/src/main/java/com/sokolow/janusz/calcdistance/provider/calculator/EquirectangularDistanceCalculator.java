package com.sokolow.janusz.calcdistance.provider.calculator;

import com.sokolow.janusz.calcdistance.exception.WrongDistanceCalculationMethodException;
import com.sokolow.janusz.calcdistance.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Y50
 */
public class EquirectangularDistanceCalculator implements DistanceCalculatorInterface {

    Logger log = LogManager.getLogger(EquirectangularDistanceCalculator.class.getCanonicalName());
    private static final int LINE_DEGREESE_CONTROL = 1; // Method is ultra inaccuracy for large distance, that's why control is nessesary

    @Override
    public double countDistance(City c1, City c2) throws WrongDistanceCalculationMethodException {
        log.debug("Calculate distance for : \n" + c1 + " \n" + c2);
        double lat1 = c1.getLatitude();
        double lon1 = c1.getLongitude();
        double lat2 = c2.getLatitude();
        double lon2 = c2.getLongitude();

        double xDif = (lon2 - lon1) * Math.PI / 180;
        double yDif = (lat2 - lat1)  * Math.PI / 180;

        double lineDegreeDistance = Math.sqrt(yDif * yDif + xDif * xDif);

        log.debug("Got lineat distane :" + lineDegreeDistance);
        if (lineDegreeDistance > LINE_DEGREESE_CONTROL) {
            throw new WrongDistanceCalculationMethodException();
        }

        return lineDegreeDistance * EARTH_RADIUS;
    }

}

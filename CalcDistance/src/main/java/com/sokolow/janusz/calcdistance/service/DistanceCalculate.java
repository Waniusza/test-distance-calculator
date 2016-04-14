package com.sokolow.janusz.calcdistance.service;

import com.sokolow.janusz.calcdistance.dao.CityDAO;
import com.sokolow.janusz.calcdistance.dao.DistanceDAO;
import com.sokolow.janusz.calcdistance.exception.CDDatabaseException;
import com.sokolow.janusz.calcdistance.exception.WrongDistanceCalculationMethodException;
import com.sokolow.janusz.calcdistance.model.City;
import com.sokolow.janusz.calcdistance.provider.calculator.DistanceCalculatorInterface;
import com.sokolow.janusz.calcdistance.provider.calculator.EquirectangularDistanceCalculator;
import com.sokolow.janusz.calcdistance.provider.calculator.HaversineDistanceCalculator;
import com.sokolow.janusz.calcdistance.provider.calculator.LawOfCosinesDistanceCalculator;
import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Waniusza
 */
@Path("calculate")
public class DistanceCalculate {

    Logger log = LogManager.getLogger(DistanceCalculate.class.getCanonicalName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calcDistance(@QueryParam("calcMethod") String method,
            @QueryParam("cityFrom") int cityFromId,
            @QueryParam("cityTo") long cityToId,
            @QueryParam("accuracy") int accuracy) {
        log.debug(" handle " + method + " " + accuracy + " " + cityFromId + " " + cityToId);

        String result;

        if (cityFromId == cityToId) {
            result = "You choose same city, distance os ZERO";
        } else {
            switch (method) {
                case "matrix":
                    try {
                        Double calculateCrowfligthDistance = DistanceDAO.findDistance(cityToId, cityFromId);
                        result = "Matrix distance got "
                                + new BigDecimal(calculateCrowfligthDistance).setScale(3, BigDecimal.ROUND_HALF_UP)
                                + " kilometers";
                    } catch (CDDatabaseException ex) {
                        result = ex.getMessage();
                    }
                    break;
                case "crowflight":
                    try {
                        result = calculateCrowfligthDistance(cityToId, cityFromId, accuracy);
                    } catch (CDDatabaseException | WrongDistanceCalculationMethodException ex) {
                        result = ex.getMessage();
                    }
                    break;

                case "all":
                    try {
                        Double calculateCrowfligthDistance = DistanceDAO.findDistance(cityToId, cityFromId);
                        result = "Matrix distance got "
                                + new BigDecimal(calculateCrowfligthDistance).setScale(3, BigDecimal.ROUND_HALF_UP)
                                + " kilometers <br>";
                        result += calculateCrowfligthDistance(cityToId, cityFromId, accuracy);
                    } catch (CDDatabaseException | WrongDistanceCalculationMethodException ex) {
                        result = ex.getMessage();
                    }
                    break;
                default:
                    result = "Unsupported method definiton";
            };
        }

        return result;

//        try {
//            result = CityDAO.findCity();
//        } catch (CDDatabaseException ex) {
//            result = new LinkedList<>();
//        }
//        log.debug("Ready to return  " + result.get(0).toString());
    }

    private String calculateCrowfligthDistance(long cityFromId, long cityToId, int accuracy) throws CDDatabaseException, WrongDistanceCalculationMethodException {
        DistanceCalculatorInterface calc;
        ;
        String result = "Crowflight distance calculated by ";
        switch (accuracy) {
            case 1:
                calc = new EquirectangularDistanceCalculator();
                result += "equirectangular method ";
                break;
            case 2:
                calc = new LawOfCosinesDistanceCalculator();
                result += "law of cosines method ";
                break;
            case 3:
                calc = new HaversineDistanceCalculator();
                result += "Haversine method ";
                break;
            default:
                calc = new LawOfCosinesDistanceCalculator();    // Default medium precission
                result += "default method ";
        }

        City cityTo = CityDAO.getCity(cityToId);
        City cityFrom = CityDAO.getCity(cityFromId);
        result += "got "
                + new BigDecimal(calc.countDistance(cityTo, cityFrom)).setScale(3, BigDecimal.ROUND_HALF_UP)
                + " kilometers";
        return result;
    }
}

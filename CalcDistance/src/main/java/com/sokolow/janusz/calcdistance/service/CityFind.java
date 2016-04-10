package com.sokolow.janusz.calcdistance.service;

import com.sokolow.janusz.calcdistance.dao.CityDAO;
import com.sokolow.janusz.calcdistance.exception.CDDatabaseException;
import com.sokolow.janusz.calcdistance.model.City;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Waniusza
 */
@Path("citiList")
public class CityFind {

    Logger log = LogManager.getLogger(CityFind.class.getCanonicalName());
    private final String qry = "SELECT * FROM city";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> findCities() {
        log.debug(" handle");

        List<City> result;

        try {
            result = CityDAO.findCity();
        } catch (CDDatabaseException ex) {
            result = new LinkedList<>();
        }
       
        log.debug("Ready to return  " + result.get(0).toString());
        return  result;
    }
}

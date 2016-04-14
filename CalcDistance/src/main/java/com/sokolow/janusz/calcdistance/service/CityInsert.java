package com.sokolow.janusz.calcdistance.service;

import com.sokolow.janusz.calcdistance.constant.Config;
import java.sql.DriverManager;
import java.sql.SQLException;
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
@Path("cityInsert")
public class CityInsert {

    Logger log = LogManager.getLogger(CityInsert.class.getCanonicalName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String findCities() {
        log.debug(" handle");

        String qry;
        qry = "INSERT INTO city (name, latitude, longitude) "
                + "VALUES (\"Gdansk\", \"54.3475\", \"18.645278\")";
        String result;

        try {
            Class.forName(Config.MYSQL_JDBC_DRIVER);

            DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USER, Config.DATABASE_PASSWORD)
                    .createStatement()
                    .executeUpdate(qry);
            result = "New city inserted!\n";
        } catch (SQLException | ClassNotFoundException ex) {
            log.error("Error on invoking database connection", ex);
            result = "Something goes wrong";
        } 
        return result;
    }

}

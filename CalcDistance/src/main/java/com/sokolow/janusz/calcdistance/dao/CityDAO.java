package com.sokolow.janusz.calcdistance.dao;

import com.sokolow.janusz.calcdistance.constant.configTmp;
import com.sokolow.janusz.calcdistance.exception.CDDatabaseException;
import com.sokolow.janusz.calcdistance.model.City;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Waniusza
 */
public class CityDAO {

    private static Logger log = LogManager.getLogger(CityDAO.class.getCanonicalName());

    public static List<City> findCity() throws CDDatabaseException {

        List<City> result = new ArrayList<>();
        final String qry = "SELECT city_id, name FROM city";
        ResultSet rs = null;

        try {
            Class.forName(configTmp.MYSQL_JDBC_DRIVER);
            rs = DriverManager.getConnection(configTmp.DATABASE_URL, configTmp.DATABASE_USER, configTmp.DATABASE_PASSWORD)
                    .createStatement()
                    .executeQuery(qry);

            while (rs.next()) {
//                log.info("Got " + rs.getString("name"));
                int id = rs.getInt("city_id");
                String name = rs.getString("name");
                result.add(new City(id, name));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            log.error("Error on invoking database connection", ex);
            throw new CDDatabaseException("Error on invoking database connection");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                log.error("Error on closing database connection", ex);
                throw new CDDatabaseException("Error on closing database connection");
            }
        }
        return result;
    }

    public static City getCity(long cityId) throws CDDatabaseException {

        City result;
        
        final String qry = "SELECT latitude, longitude FROM city WHERE city_id=" + cityId;
        
        log.debug("Executing query : " + qry);
        ResultSet rs = null;

        try {
            Class.forName(configTmp.MYSQL_JDBC_DRIVER);
            rs = DriverManager.getConnection(configTmp.DATABASE_URL, configTmp.DATABASE_USER, configTmp.DATABASE_PASSWORD)
                    .createStatement()
                    .executeQuery(qry);

            if (rs.next()) {
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                result = new City(latitude, longitude);
                log.debug("GOT city " + result);
            } else {
                throw new CDDatabaseException("City data not found.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            log.error("Error on invoking database connection", ex);
            throw new CDDatabaseException("Error on invoking database connection");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                log.error("Error on closing database connection", ex);
                throw new CDDatabaseException("Error on closing database connection");
            }
        }
        return result;
    }
}

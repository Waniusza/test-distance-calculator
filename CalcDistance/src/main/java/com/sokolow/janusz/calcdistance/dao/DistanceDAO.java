package com.sokolow.janusz.calcdistance.dao;

import com.sokolow.janusz.calcdistance.constant.config;
import com.sokolow.janusz.calcdistance.exception.CDDatabaseException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Waniusza
 */
public class DistanceDAO {

    private static Logger log = LogManager.getLogger(DistanceDAO.class.getCanonicalName());

    public static double findDistance(long cityFrom, long cityTo) throws CDDatabaseException {

        final String qry = "SELECT distance_id, distance FROM distance WHERE "
                + " (fromCity=" + cityFrom + " AND toCity=" + cityTo + ") OR "
                + " (fromCity=" + cityTo + " AND toCity=" + cityFrom + ")";
        ResultSet rs = null;
        double distance;
        try {
            Class.forName(config.MYSQL_JDBC_DRIVER);
            rs = DriverManager.getConnection(config.DATABASE_URL, config.DATABASE_USER, config.DATABASE_PASSWORD)
                    .createStatement()
                    .executeQuery(qry);

            if (rs.next()) {
                log.info("Got " + rs.getInt("distance_id"));
                distance = rs.getDouble("distance");
            } else {
                throw new CDDatabaseException("Distance data not found.");
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
        return distance;
    }
}

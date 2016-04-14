package com.sokolow.janusz.calcdistance.service;

import com.sokolow.janusz.calcdistance.constant.Config;
import com.sokolow.janusz.calcdistance.exception.CDDatabaseException;
import com.sokolow.janusz.calcdistance.provider.DBConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("databaseVersion")
public class ControlDatabaseVersion {

    Logger log = LogManager.getLogger(ControlDatabaseVersion.class.getCanonicalName());

    private final String qry = "SELECT VERSION()";

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        log.debug(" handle");

        String result;

        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            rs = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USER, Config.DATABASE_PASSWORD)
                    .createStatement()
                    .executeQuery(qry);
            result = "Got it!";
            if (rs.next()) {
                log.info("Got " + rs.getString(1));
                result += " " + rs.getString(1);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            log.error("Error on invoking database connection", ex);
            result = "Something goes wrong";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                log.error("Error on closing database connection", ex);
                result = "Something goes wrong";
            }
        }
        return result;
    }
}

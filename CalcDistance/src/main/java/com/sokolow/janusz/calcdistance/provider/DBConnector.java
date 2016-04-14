package com.sokolow.janusz.calcdistance.provider;

import com.sokolow.janusz.calcdistance.constant.Config;
import com.sokolow.janusz.calcdistance.exception.CDDatabaseException;
import com.sokolow.janusz.calcdistance.service.ControlDatabaseVersion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Waniusza
 */
public class DBConnector {

    private static  Logger log = LogManager.getLogger(DBConnector.class.getCanonicalName());


    public static void executeQuery(String qry) throws CDDatabaseException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USER, Config.DATABASE_PASSWORD);
            st = con.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                System.out.println(rs.getString(1));
                log.info("Got " + rs.getString(1));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            log.error("Error on invoking database connection", ex);
            throw new CDDatabaseException("Error on invoking database connection", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.first();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            log.error("Error on closing database connection", ex);
                throw new CDDatabaseException("Error on closing database connection", ex);
            }
        }
    }
}

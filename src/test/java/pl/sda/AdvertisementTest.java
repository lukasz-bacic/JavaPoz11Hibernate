package pl.sda;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AdvertisementTest {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/portalaukcyjny?serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";


    @Test
    public void selectAll() {

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String SQL = "SELECT * FROM `portalaukcyjny`.`advertisement` ;";

            ResultSet resultSet = stmt.executeQuery(SQL);

            while (resultSet.next()) {
                Advertisement advertisement = Advertisement.toEntity(resultSet);
                System.out.println(advertisement);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

}
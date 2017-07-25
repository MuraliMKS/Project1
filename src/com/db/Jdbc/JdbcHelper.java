
package com.db.Jdbc;
//import Jdbc.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcHelper {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(Constants.UNAME);
            con = DriverManager.getConnection(Constants.URL, Constants.UID, Constants.PASSWORD);
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("error connecting data base..!! try again..!! " + e.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void close(Statement statement) {
        if (statement != null)
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

}

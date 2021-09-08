package com.generactive.db.connection;

import com.generactive.db.constants.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private DbConnection() {
    }

    public static Connection get() {
        try{
            FileInputStream file = new FileInputStream(Resources.PROPERTIES_PATH);
            Properties properties = new Properties();
            properties.load(file);
            String driver = (String) properties.get("driver");
            String URL = (String) properties.get("URL");
            String username = (String) properties.get("username");
            String password = (String) properties.get("password");
            Class.forName(driver);
            return DriverManager.getConnection(URL, username, password);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("ABSOLUTE PATH IS : " + new File(".").getAbsolutePath());
           e.printStackTrace();
        }
        return null;
    }
}

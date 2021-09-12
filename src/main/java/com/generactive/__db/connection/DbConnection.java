//package com.generactive.db.connection;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Properties;
//
//public class DbConnection {
//
//    private DbConnection() {
//    }
//
//    public static final String DATABASE_PROPERTIES_FILE = "database.properties";
//
//    private static Properties getConnectionProperties() {
//        // Create Properties object.
//        Properties props = new Properties();
//
//        try {
//            // Load jdbc related properties in above file.
//            props.load(DbConnection.class
//                    .getClassLoader()
//                    .getResourceAsStream(DATABASE_PROPERTIES_FILE));
//
//            return props;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Unable to load db properties from: "
//                    + DATABASE_PROPERTIES_FILE);
//        }
//    }
//
//    public static Connection get(){
//        Properties props = DbConnection.getConnectionProperties();
//        String dbDriverClass = props.getProperty("driver");
//        String dbConnUrl = props.getProperty("URL");
//        String dbUsername = props.getProperty("username");
//        String dbPassword = props.getProperty("password");
//
//        try {
//            Class.forName(dbDriverClass);
//            return DriverManager.getConnection(dbConnUrl,
//                    dbUsername,
//                    dbPassword);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            // As our application is heavily depends on the database connection,
//            // we want it to be terminated if driver class has not been found.
//            System.exit(1);
//        }
//       return null;
//    }
//}

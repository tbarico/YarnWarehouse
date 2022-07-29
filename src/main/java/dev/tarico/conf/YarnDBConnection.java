package dev.tarico.conf;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Singleton class to connect to the database.
 * 
 * @author Tara Arico - 7.24.2022
 */
public class YarnDBConnection {
    private static YarnDBConnection connectionInstance = new YarnDBConnection();
    private String password;
    private String username;
    private String connUrl;

    /**
     * Creates a YarnDBConnection object.
     */
    private YarnDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try(InputStream input = YarnDBConnection.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                Properties properties = new Properties();
                properties.load(input);
                this.password = properties.getProperty("db.password");
                this.username = properties.getProperty("db.username");
                this.connUrl = properties.getProperty("db.url");

            } catch(IOException e) {
                //silently fail for now
                System.out.println("Failed reading from stream.");
                e.printStackTrace();
            }
        } catch(ClassNotFoundException e) {
            //silently fail for now
            System.out.println("Failed loading Driver.");
        }
    }


    /**
     * Returns the singleton YarnDBConnection object as the database connection.
     * 
     * @return this object
     */
    public static YarnDBConnection getConnectionInstance() {
        return connectionInstance;
    }

    /**
     * Return the connection made by this object to the database.
     * 
     * @return connection object
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connUrl, username, password);
    }

    /**
     * Returns the password for the database connection.
     *  
     * @return password as a string object
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username for the database connection.
     * 
     * @return username as a string object
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return the connection url for this database connection.
     * 
     * @return connection url as a string object
     */
    public String getConnectionUrl() {
        return connUrl;
    }
}

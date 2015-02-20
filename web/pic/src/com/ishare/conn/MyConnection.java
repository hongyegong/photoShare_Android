package com.ishare.conn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@SuppressWarnings("unused")
public class MyConnection {
    private static MyConnection instance    = null;
    private static String url        = null;
    private static String drivername = null;
    private static String username   = null;
    private static String password   = null;
    private static Connection connection    = null;
    static {
        Properties pro             = null;
        InputStream fileInput  = null;
        try {
            fileInput = MyConnection.class.getResourceAsStream("/datasource.properties");
            pro    = new Properties();
            pro.load(fileInput);
            
            drivername = pro.getProperty("driver");
            url        = pro.getProperty("url");
            username   = pro.getProperty("username");
            password   = pro.getProperty("password");
            
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(fileInput != null){
                try {
                    fileInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private MyConnection(){
        try {
            Class.forName(drivername);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection(){
        if(instance == null){
            instance = new MyConnection();
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public static void main(String[] args) {
        
        try {
            Connection con = MyConnection.getConnection();
            Statement st = con.createStatement();
            st.execute("SELECT NOW() ");
            st.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


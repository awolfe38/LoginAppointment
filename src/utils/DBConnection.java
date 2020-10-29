/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ajw51
 */
public class DBConnection {
    
        private static String url = "jdbc:mysql://wgudb.ucertify.com/WJ07BzQ";
        private static String user = "U07BzQ";
        private static String pass = "53688979306";
        
        private static Connection conn = null;
    
    
    public static Connection startConnection() {
         try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pass);
             System.out.println("Connection opened");
        
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
        
        return conn;
    }
    
    public static void closeConnection() {
        try {
        conn.close();
        System.out.println("Connection closed!");
        } catch(SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    
}

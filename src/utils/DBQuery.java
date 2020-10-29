/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ajw51
 */
public class DBQuery {
    
    private static PreparedStatement statement; // Statement method
    
    // Create statement object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        
        statement = conn.prepareStatement(sqlStatement);
    }
    
    // return statement object
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
    
}
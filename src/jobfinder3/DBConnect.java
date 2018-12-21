/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobfinder3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aaron
 */
public class DBConnect{
    
    //Attributes for the database connection
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ZZaq32!!";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/job"+
            "finder3";
    
    static Connection Connect(){
            
            Connection conn = null;

            try {

                //connect
                conn = DriverManager.getConnection(CONN_STRING,USERNAME,
                        PASSWORD);

            } catch(SQLException e) {

               System.err.println(e);

            }
            
            return conn;
            
        }
    
    static void Disconnect(Connection conn){
        
        try{
            
            conn.close();
            
        } catch(SQLException e) {

               System.err.println(e);

        }
        
    }
    
}
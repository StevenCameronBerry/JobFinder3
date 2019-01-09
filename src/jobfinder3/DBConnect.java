/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobfinder3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    private static int rowcount = 0, x = 0;
    
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
    
    //Get a result set for ID's in the DB
    static ResultSet ID(Connection conn, String Website) throws 
            SQLException{
        
        //SQL statement
        Statement stmt = (Statement) conn.createStatement();
        String query = "SELECT ID FROM jobfinder3.jf3 WHERE Website = '" + 
                Website + "'";

        //Obtain the results
        ResultSet Results = stmt.executeQuery(query);

        return Results;
        
    }
    
    //See if an ID is in the database.
    static ResultSet IDIn(Connection conn, String Website, String ID) throws 
            SQLException{
        
        //SQL statement
        Statement stmt = (Statement) conn.createStatement();
        String query = "SELECT ID FROM jobfinder3.jf3 WHERE Website = '" + 
                Website + "' " + "AND ID = '" + ID + "'";

        //Obtain the results
        ResultSet Results = stmt.executeQuery(query);

        return Results;
        
    }
    
    //Get the number of rows in a DB.
    static int NumAddsIn(ResultSet Results) throws Exception{
        
        //Find out how many null results there were for Description
        if (Results.last()) {
          rowcount = Results.getRow();
          Results.beforeFirst();
        }
        
        return rowcount;
        
    }
    
    //Get an array of Job adds already in the DB
    static String[] AlreadyIn(ResultSet Results, int rowcount) throws Exception{
        
        String[] InDB = new String[rowcount];

        while (Results.next() && x < rowcount) {

            // do your standard per row stuff 
            InDB[x] = Results.getString("ID");

            x = x + 1;

        }

        return InDB;
        
    }
    
}
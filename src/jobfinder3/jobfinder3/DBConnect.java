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
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Arrays;

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
    
    //Get a result set for ID's in the DB
    public static ResultSet Search(Connection conn, String Query) throws 
            SQLException{
        
        //SQL statement
        Statement stmt = (Statement) conn.createStatement();

        //Obtain the results
        ResultSet Results = stmt.executeQuery(Query);

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
        
        int rowcount = 0;
        
        //Find out how many null results there were for Description
        if (Results.last()) {
          rowcount = Results.getRow();
          Results.beforeFirst();
        }
        
        return rowcount;
        
    }
    
    //Get an array of Job adds already in the DB
    static String[] AlreadyIn(ResultSet Results, int rowcount) throws Exception{
        
        int x = 0;
        
        String[] InDB = new String[rowcount];

        while (Results.next() && x < rowcount) {

            // do your standard per row stuff 
            InDB[x] = Results.getString("ID");

            x = x + 1;

        }

        return InDB;
        
    }
    
    static void InsertAdd(JobAdd add, int[] Counters, Connection conn) 
            throws SQLException{

        Statement stmt = (Statement) conn.createStatement();
        String insert = "INSERT INTO jobfinder3.jf3 (uniqueID, " + 
                "ID, Title, Location, Distance_km, Age, URL, " +
                "SalaryType, JobType, Description, CompanyName, " +
                "`Title+Desc`, Website, AdvertiserName, KeyWord, CoverLetter)" +
                " VALUES ('" + add.GetUniqueID() + "', '" + add.GetID() + 
                "', '" + add.GetTitle() + "', '" + add.GetLocation() + 
                "', '" + add.GetDistance_km() + "', '"
                + add.GetAge() + "', '" + add.GetURL() + "', '" + 
                add.GetSalaryType() + "', '" + add.GetJobType() + "', '" +
                add.GetDescription() + "', '" + add.GetCompanyName() + "', '" +
                add.GetTitleDesc() + "', '" + add.GetWebsite() + "', '" + 
                add.GetAdvertiserName() + "', '" + add.GetKeyWord() + "', '" + add.GetMessage() + "')";

        System.out.println(add.toString());

        System.out.println(Counters[2] + 1 + " new adds inserted into the DB out of " + Counters[1]);
        
        //Catch and ignore any duplicate entries
        try {
        	
        	stmt.executeUpdate(insert);
        	
        }catch(SQLIntegrityConstraintViolationException e){
        	
        	//Do nothing
        	
        }catch(SQLSyntaxErrorException eee) {
        	
        	//Log in the future DELETE ME EVENTUALLLLYYYYYYYYYY OR LOGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
        	
        }
        
    }
    
}
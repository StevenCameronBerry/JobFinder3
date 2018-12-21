/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobfinder3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author aaron
 */
public class JobAdd {
        
    public String ID, Title, Name, Description, Location, SalaryType,
            JobType, Website, UniqueID, URL, Title_Desc, Age;
    public int Distance;
    private String AgeT;
    private int AgeMH;

    JobAdd(String ID, String Location, String SalaryType, String JobType, 
            String Website, String AgeStr, String Title, int Distance){

        //Standard constructor paramaters
        this.ID = ID;
        this.Location = Location;
        this.SalaryType = SalaryType;
        this.JobType = JobType;
        this.Website = Website;
        this.Title = Title;
        this.Distance = Distance;

        //Ones created from input
        this.UniqueID = this.Website.charAt(0) + this.ID;
        //URL
        if(this.Website.equals("Gumtree")){

            this.URL = "https://www.gumtree.com.au/s-ad/" + this.ID;

        }else if(this.Website.equals("Seek")){

            this.URL = "" + this.ID; //Add Seek URL

        }

        //Age
        //Get the current time in this format YYYY-MM-DD HH:MM:SS
        if(AgeStr.equals("Yesterday") == true){

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String Yesterday = new SimpleDateFormat
                ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
            this.Age = Yesterday;

        }else if(AgeStr.contains("minutes ago") == true){

            //Get how many minutes it was
            AgeT = AgeStr.replace(" minutes ago", "");
            AgeMH = Integer.parseInt(AgeT);

            //Format Data
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, - AgeMH);
            String MinsAgo = new SimpleDateFormat
                ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
            this.Age = MinsAgo;

        }else if(AgeStr.contains("hours ago") == true){

            //Get how many hours it was
            AgeT = AgeStr.replace(" hours ago", "");
            AgeMH = Integer.parseInt(AgeT);

            //Format Data
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, - AgeMH);
            String HoursAgo = new SimpleDateFormat
                ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
            this.Age = HoursAgo;

        }else{ //If it just had a date listed

            String DatePosted = new SimpleDateFormat
                ("YYYY-MM-dd HH:MM:ss").format(AgeStr);

            this.Age = DatePosted;

        }

    }

    //Setters
    void SetName(String NameRaw){

        //Title and Description
        this.Name = NameRaw.replaceAll("'", "");
        this.Name = this.Name.replaceAll("&", "and");

    }

    void SetDesc(String DescRaw){

        this.Description = DescRaw.replaceAll("'", "");
        this.Description = this.Description.replaceAll("&", "and");

    }

    //Method to combine Title and Description
    void TitleDesc(){

        this.Title_Desc = this.Title.toLowerCase() + " || " +
                this.Description.toLowerCase();

    }

    //Method to insert the data into the DB
    void DBInsert(Connection conn){

        try {

            if(this.UniqueID != null){

                Statement stmt = (Statement) conn.createStatement();
                String insert = "INSERT INTO jobfinder3.jf3 (uniqueID, " + 
                        "ID, Title, Location, Distance_km, Age, URL, " +
                        "SalaryType, JobType, Description, Name, " +
                        "`Title+Desc`, Website)" +
                        " VALUES ('" + this.UniqueID + "', '" + this.ID + 
                        "', '" + this.Title + "', '" + this.Location + 
                        "', '" + this.Distance + "', '"
                        + this.Age + "', '" + this.URL + "', '" + 
                        this.SalaryType + "', '" + this.JobType + "', '" +
                        this.Description + "', '" + this.Name + "', '" +
                        this.Title_Desc + "', '" + this.Website + "')";
                stmt.executeUpdate(insert);
                
               //System.out.println("\n\n===================\n\n" + "ID: " + 
                        this.UniqueID + "\nTite: " + this.Title + 
                            "\nLocation: " + this.Location + "\nDistance: " +
                            this.Distance + "\nAge: " + this.Age + "\nURL: " + 
                            this.URL + "\n\n===================\n\n");

            }

        } catch(SQLException e) {

            //There will be an error if Unique ID is not unique.
          //System.err.println(e);

        }

    }
    
}

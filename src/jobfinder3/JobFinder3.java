/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobfinder3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.sql.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


/**
 *
 * @author aaron
 */
public class JobFinder3 {

    //Attributes for the database connection
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ZZaq32!!";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/job"+
            "finder3";
    
    public static void main(String[] args) throws Exception {
    
       /*Method to connect to the DB*/
       Connection conn = null;

        try {

            //connect
            conn = DriverManager.getConnection(CONN_STRING,USERNAME,
                    PASSWORD);
            //////System.out.println("Connected!");

        } catch(SQLException e) {

           //System.err.println(e);

        }
        
    while(true){
        
        //Initialize Attributes
        int status, i, j, x, y, b, ageMins, ageHours;
        int z = 0, rowcount = 0, row = 0, a = 0;
        String JSONStr, AgeT;
        String[] uniqueIDStr = new String[96*13];
        String[] IDStr = new String[96*13];
        String[] titleStr = new String[96*13];
        String[] locationStr = new String[96*13];
        String[] distanceStr = new String[96*13];
        String[] ageStr = new String[96*13];
        String[] ageReal = new String[96*13];
        String[] URLStr = new String[96*13];
        String[] IDOld = new String[21474836];
        String[] SalaryType = new String[96*13];
        String[] JobType = new String[96*13];
        
        /*Get all 13 pages of gumtrees job listing. When there are 96 results
    Per page on gumtree there are 13 pages.*/
/*New method to make sure that the new ID's havent been run through 
                     already*/
                    try {

                        //SQL statement
                        Statement stmt = (Statement) conn.createStatement();
                        String query = "SELECT ID FROM jobfinder3.jf3";

                        //Obtain the results
                        ResultSet Results = stmt.executeQuery(query);

                        //Find out how many null results there were for Description
                        if (Results.last()) {
                          row = Results.getRow();
                          Results.beforeFirst();
                          //////System.out.println(rowcount);
                        }
                        while (Results.next() && a <= row) {

                            // do your standard per row stuff 
                            IDOld[a] = Results.getString("ID");
                           //////System.out.println(IDOld[a] + "\n");
                           //////System.out.println(a);
                            
                            a = a + 1;
                            
                        }

                    } catch(SQLException e) {

                        //There will be an error if Unique ID is not unique.
                       //System.err.println(e);

                    }
        
    /*Output for this method is array of JSON*/
    /* THERE NEEDS TO BE A BREAK FROM LOOP IF THERE IS A DUPLICATE ID */
    for(i=1, j=13; i <= j; i++){
        
            //Web Scrape all of the job adds off of gumtree
            URL url = new URL("https://www.gumtree.com.au/ws/search.json?categ"+
                    "oryId=9302&locationId=3008546&locationStr=SouthPerth&offe"+
                    "rType=OFFER&pageNum=" + i + "&pageSize=96&previousCategor"+
                    "yId=9302&radius=50&sortByName=date");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //Response type
            status = con.getResponseCode();

            //Parse the information into a string, "content"
            BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            //Make 'content' a string
            JSONStr = content.toString();
            
            //Print the content
            //////System.out.println(content);
            
            /*New Method here, this methods output is an array of add ID's
            Parse JSON*/
            
            //Get JSON object.
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(JSONStr);
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            //Naviagate down the JSON Tree and obtain appropriate information.
            JsonElement data = jsonObject.get("data");
            JsonObject dataObj = data.getAsJsonObject();
            JsonElement results = dataObj.get("results");
            JsonObject resultsObj = results.getAsJsonObject();
            JsonElement resultList = resultsObj.get("resultList");
            JsonArray resultListArr = resultList.getAsJsonArray();
            
            //Get all of the result List (0 - 95)
            for(x=0, y=95; x <= y; x++, z++){
             
                try {
                   
                    //Job listings on a page
                    JsonElement Ecs = resultListArr.get(x);
                    JsonObject EcsObj = Ecs.getAsJsonObject();

                    //Data we care about
                    JsonElement id = EcsObj.get("id");
                    JsonElement title = EcsObj.get("title");
                    JsonElement location = EcsObj.get("location");
                    JsonElement distance = EcsObj.get("distance");
                    JsonElement age = EcsObj.get("age");
                    
                    //Naviagate down the JSON Tree and obtain appropriate information.
                    JsonElement mainAttributes = EcsObj.get("mainAttributes");
                    JsonObject mainAttributesObj = mainAttributes.getAsJsonObject();
                    JsonElement data2 = mainAttributesObj.get("data");
                    JsonObject data2Obj = data2.getAsJsonObject();
                    JsonElement salarytype = data2Obj.get("salarytype");
                    JsonElement jobtype = data2Obj.get("jobtype");
                    System.out.println(salarytype.toString() + " " + jobtype.toString());

                    //Make them strings
                    IDStr[z] = id.toString();
                    //If the add has already been ran through, this is the last
                    //page of the JSON query
                    //If the ID in the DB
                    for(b=0; b <= a; b++){
                        
                        if(IDStr[z].equals(IDOld[b]) == true){

                            //Break from the JSON loopo
                            j = i;

                        }
                        
                    }
                    uniqueIDStr[z] = "G" + id.toString();
                    //Get rid of " at the end of the strings.
                    titleStr[z] = title.toString().substring(1, 
                            title.toString().length()-1);
                    titleStr[z] = titleStr[z].replaceAll("'", "");
                   titleStr[z] = titleStr[z].replaceAll("&", "and");
                    locationStr[z] = location.toString().substring(1, 
                            location.toString().length()-1);
                    distanceStr[z] = distance.toString().substring(1, 
                            distance.toString().length()-1);
                    distanceStr[z] = distanceStr[z].replaceAll("< ", "");
                    distanceStr[z] = distanceStr[z].replaceAll(" km", "");
                    if(Integer.parseInt(distanceStr[z]+0) == 0){
                        
                        distanceStr[z] = "0";
                        
                    }
                    SalaryType[z] = salarytype.toString().substring(1, 
                            salarytype.toString().length()-1);
                    JobType[z] = jobtype.toString().substring(1, 
                            jobtype.toString().length()-1);
                    ageStr[z] = age.toString().substring(1, 
                            age.toString().length()-1);
                    //Get the current time in this format YYYY-MM-DD HH:MM:SS
                    if(ageStr[z].equals("Yesterday") == true){
                        
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, -1);
                        String Yesterday = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
                        ageReal[z] = Yesterday;
                        //System.out.println(ageReal[z]);

                    }else if(ageStr[z].contains("minutes ago") == true){
                        
                        //System.out.println("had minutes ago");
                        
                        //Get how many minutes it was
                        AgeT = ageStr[z].replace(" minutes ago", "");
                        //System.out.println(AgeT);
                        ageMins = Integer.parseInt(AgeT);
                        //System.out.println(ageMins);
                        
                        //Format Data
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MINUTE, - ageMins);
                        String MinsAgo = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
                        //System.out.println(MinsAgo);
                        ageReal[z] = MinsAgo;
                        //System.out.println(ageReal[z]);

                    }else if(ageStr[z].contains("hours ago") == true){
                        
                        //System.out.println("had hours ago");
                        
                        //Get how many hours it was
                        AgeT = ageStr[z].replace(" hours ago", "");
                        //System.out.println(AgeT);
                        ageHours = Integer.parseInt(AgeT);
                        //System.out.println(ageHours);
                        
                        //Format Data
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.HOUR, - ageHours);
                        String HoursAgo = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
                        //System.out.println(HoursAgo);
                        ageReal[z] = HoursAgo;
                        //System.out.println(ageReal[z]);

                    }else{ //If it just had a date listed
                        
                        String DatePosted = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(ageReal[z]);
                        
                        ageReal[z] = DatePosted;
                        //System.out.println(ageReal[z]);
                        
                    }
                    
                    URLStr[z] = "https://www.gumtree.com.au/s-ad/" + IDStr[z];
                    ////System.out.println(URLStr[z]);
                    

                    String Add = "ID: " + IDStr[z] + "\nTite: " + titleStr[z] + 
                            "\nLocation: " + locationStr[z] + "\nDistance: " +
                            Integer.parseInt(distanceStr[z]) + "\nAge: " + 
                            ageReal[z] + "\nURL: " + URLStr[z] + 
                            "\n\n===================\n\n";

                    ////System.out.println(Add);
                
                } catch(Exception e) {
                  
                    //Do nothin
                    
                }   
                    
                }
             
                con.disconnect();
            
            }
        
        /*Make a method to get an array of all ID's

        /* SQL query NEW METHOD */
        for(x=0, y=95*13; x <= y; x++){
            
            ////////System.out.println(uniqueIDStr[x]);
            ////////System.out.println(x);
            //////System.out.println(Integer.parseInt(distanceStr[x]+0));
        
            try {
            
                if(uniqueIDStr[x] != null){
                
                    Statement stmt = (Statement) conn.createStatement();
                    String insert = "INSERT INTO jobfinder3.jf3 (uniqueID, " + 
                            "ID, Title, Location, Distance_km, Age, URL, " +
                            "SalaryType, JobType)" +
                            " VALUES ('" + uniqueIDStr[x] + "', '" + IDStr[x] + 
                            "', '" + titleStr[x] + "', '" + locationStr[x] + 
                            "', '" + Integer.parseInt(distanceStr[x]) + "', '"
                            + ageReal[x] + "', '" + URLStr[x] + "', '" + 
                            SalaryType[x] + "', '" + JobType[x] + "')";
                    stmt.executeUpdate(insert);
                
                }
                
            } catch(SQLException e) {
                
                //There will be an error if Unique ID is not unique.
               System.err.println(e);
                
            }

        }
        
        /*New method to obtain a string list of all empty adds*/
        String[] URLList = new String[1241]; //This will need to be a pre-determined number
        
        try {

            //SQL statement
            Statement stmt = (Statement) conn.createStatement();
            String query = "SELECT URL FROM jobfinder3.jf3 WHERE Description "
                    + "IS null";
            
            //Obtain the results
            ResultSet Results = stmt.executeQuery(query);
            
            x = 0;
            
            //Find out how many null results there were for Description
            if (Results.last()) {
              rowcount = Results.getRow();
              Results.beforeFirst();
              //////System.out.println(rowcount);
            }
            while (Results.next() && x <= rowcount) {
                
                // do your standard per row stuff 
                URLList[x] = Results.getString("URL");
                x = x + 1;
                
            }
            
        } catch(SQLException e) {

            //There will be an error if Unique ID is not unique.
           //System.err.println(e);

        }
        
        /*New method to make Get requests for all Job adds with no description.
        */
        
        /*Add a method here to get all existing URL's in an array */
        
        /*Add a method here to reduce all of URLList based on the above method
        */
        
        //Output for unparsed HTML
        String[] UnParsed = new String[rowcount]; //1235
        ////System.out.println(URLList);
        //1235
        for(x=0; x < rowcount; x++){
            
            ////System.out.println(URLList[x]);
            
            URL url = new URL(URLList[x]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //Response type
            try{
                
                status = con.getResponseCode();
                //////System.out.println(URLList[x] + " responded");
                y = x + 1;
                //////System.out.println("\n" + y + " completed out of " + rowcount);
                
            } catch(Exception e){
                
               //////System.out.println(e + " was the error!!!!");
               //////System.out.println(URLList[x] + " did not respond");
                
            }//Add a catch here for timeouts!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //} catch(){
                
                
                
            //}
            /* MAKE LIST OF URLS THAT DID NOT RESPOND AND TRY AGAIN SOMEHOW */

            //Parse the information into a string, "content"
            BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            ////////System.out.println(content);
            
            try{
            
                UnParsed[x] = content.toString();
                
            } catch(Exception e){
                
                //////System.out.println("Couldn't parse");
                //////System.out.println(e);
                
            }
            
            ////////System.out.println(UnParsed[x]);
            
        
        
        /*Method to Parse the HTML from Gumtree*/
        Document[] doc = new Document[rowcount];
        //HERE WAS A LOOP
        
            
            try{
             
                //To HTML
                doc[x] = Jsoup.parse(UnParsed[x]);
                
            } catch(Exception e) {
                
                //////System.out.println("Couldnt parse");
                //////System.out.println(e);
                
            }
            
        
        
        /*Method to get the name of the Advertiser */
        String[] Descriptions = new String[rowcount]; //1235
        //HERE WAS A LOOP
        
            
            ////////System.out.println(a);
            
            try{
                
                //Get Description
                Elements DescriptionEl = doc[x].getElementsByClass("vip-ad-des"+
                        "cription");
                Descriptions[x] = DescriptionEl.text();
                
            } catch(Exception e) {
                
                //////System.out.println("Couldnt parse");
                //////System.out.println(e);
                
            }
        //////System.out.println(Descriptions[x]);
        /*Method to delete URL's if they have been taken down*/
        if(Descriptions[x].isEmpty() == true){
                    
            //REMOVE IT FROM URLLIST AND MAKE IT SO IT CANT BE PUT BACK THERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            URLList[x] = null;
            rowcount = rowcount - 1;
           //////System.out.println("worked "+rowcount);
            
        }
        
        /*Method to get the title of the Add so it synchs with previous and next
        methods*/
        String[] Titles = new String[rowcount]; //1235
        //HERE WAS A LOOP
        
            
            ////////System.out.println(a);
            
            try{
                
                //Get Description
                Elements TitleEl = doc[x].getElementsByClass("vip-ad-title__he"+
                        "ader");
                Titles[x] = TitleEl.text();
                //////System.out.println(Titles[x]);
                
            } catch(Exception e) {
                
                //////System.out.println("Couldnt parse");
                //////System.out.println(e);
                
            }
            
        
        
        /*Advertiser Names */
        String[] Names = new String[rowcount]; //1235
        //HERE WAS A LOOP
        
            
            ////////System.out.println(a);
            
            try{
                
                Elements NameEl = doc[x].getElementsByClass("seller-profile__n"+
                        "ame");
                Names[x] = NameEl.text();
                //////System.out.println(Names[x]);
                
            } catch(Exception e) {
                
                //////System.out.println("Couldnt parse");
                //////System.out.println(e);
                
            }
            
        
        
        /*Clean Titles for apostrophes marks*/
        //HERE WAS A LOOP
         
             
             try{
                 
                 Titles[x] = Titles[x].replaceAll("'", "");
                 Titles[x] = Titles[x].replaceAll("&", "and");
                 
             }catch(Exception e){
                 
                 //////System.out.println(e);
                 
             }
             
         
        
        /*Make the title lower case*/
        String[] TitleLower = new String[rowcount]; //1235
        //HERE WAS A LOOP
        
            
            try{
             
                TitleLower[x] = Titles[x].toLowerCase();
                
            } catch(Exception e){
                
                //////System.out.println("No Title");
                
            }
            
        
         
         /*Clean Titles for apostrophes marks*/ /*MAKE THESE DIFF ARR INPUT!!!!!!!!!!!!*/
         //HERE WAS A LOOP
         
             
             try{
             
                Descriptions[x] = Descriptions[x].replaceAll("'", "");
                Descriptions[x] = Descriptions[x].replaceAll("&", "and");
                
             } catch(Exception e){
                 
                 //////System.out.println(e);
                 
             }
             
             try{
                Names[x] = Names[x].replaceAll("'", "");
             }catch(Exception e){
                 System.out.println(e);
             }
        
        /*Make the description lower case*/
        String[] DescLower = new String[rowcount]; //1235
        //HERE WAS A LOOP
        
             
             try{
             
                DescLower[x] = Descriptions[x].toLowerCase();
            
             }catch(Exception e){
            
                 //////System.out.println(e);
                 
            }
             
         
         
         /*Combine the lower case title with the lower case Description*/
         String[] TitleDesc = new String[rowcount]; //1235
         //HERE WAS A LOOP
         
            try{
                
                TitleDesc[x] = TitleLower[x] + " || " + DescLower[x];
                //////System.out.println(TitleDesc[x]);
             
            }catch(Exception e){
                    
                //////System.out.println(e);
                    
            }
         
         /*New method to update the DB with titles and descriptions*/
         //HERE WAS A LOOP
         
             
            try {

                   Statement stmt = (Statement) conn.createStatement();
                    String insert = "UPDATE jobfinder3.jf3 SET Description "+
                            "= '" + Descriptions[x] + "', Name = '" + 
                            Names[x] + "', `Title+Desc` = '" + TitleDesc[x] +
                            "' WHERE Title = '" + Titles[x] + "'";
                    stmt.executeUpdate(insert);

               } catch(SQLException e) {

                   //There will be an error if Unique ID is not unique.
                  //System.err.println(e);

               } catch(Exception e){
                   
                  //////System.out.println(e);
                   
               }
    
        }
         
    }
        
    }
   
    
    
}

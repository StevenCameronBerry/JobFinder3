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
import org.jsoup.nodes.Document;
import java.util.Collections;
import java.util.*;


/**
 *
 * @author aaron
 */
public class JobFinder3{
    
    public static void main(String[] args) throws Exception {
        
        int status;

    while(true){
        
        //Initialize Attributes
        int  i, j, x, y, b, c;
        int z = 0, rowcount = 0, row = 0, a = 0, d = 0;
        String JSONStr;
        String[] IDStr = new String[96*13];
        JobAdd[] Gumtree = new JobAdd[96*13];
        String[] IDOld = new String[21474836];
        String[] titleStr = new String[96*13];
        String[] locationStr = new String[96*13];
        String[] distanceStr = new String[96*13];
        String[] ageStr = new String[96*13];
        String[] SalaryType = new String[96*13];
        String[] JobType = new String[96*13];
        JobAdd[] InsertList = new JobAdd[96*13];
        String[] URLListDB = new String[96*13];
        
        /*Get all 13 pages of gumtrees job listing. When there are 96 results
        Per page on gumtree there are 13 pages.*/
        /*New method to make sure that the new ID's havent been run through 
        already*/
        Connection conn = DBConnect.Connect();
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
             
           }
           while (Results.next() && a <= row) {

               // do your standard per row stuff 
               IDOld[a] = Results.getString("ID");

               a = a + 1;

           }

       } catch(SQLException e) {

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
            
            ////System.out.println(status);
            
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
            
            ////System.out.println(resultListArr);
            
            //Get all of the result List (0 - 95)
            for(x=0, y=95; x <= y; x++, z++){
             
                try {
                   
                    //Job listings on a page
                    JsonElement Ecs = resultListArr.get(x);
                    //System.out.println(Ecs);
                    JsonObject EcsObj = Ecs.getAsJsonObject();

                    //Data we care about
                    JsonElement id = EcsObj.get("id");
                    //System.out.println(id);
                    JsonElement title = EcsObj.get("title");
                    JsonElement location = EcsObj.get("location");
                    JsonElement distance = EcsObj.get("distance");
                    JsonElement age = EcsObj.get("age");
                    //System.out.println(age);
                    
                    //Naviagate down the JSON Tree and obtain appropriate 
                    //information.
                    JsonElement mainAttributes = EcsObj.get("mainAttributes");
                    JsonObject mainAttributesObj = mainAttributes
                            .getAsJsonObject();
                    JsonElement data2 = mainAttributesObj.get("data");
                    JsonObject data2Obj = data2.getAsJsonObject();
                    JsonElement salarytype = data2Obj.get("salarytype");
                    JsonElement jobtype = data2Obj.get("jobtype");
                    //System.out.println(salarytype.toString() + " " + jobtype

                    //Make them strings
                    //IDStr[z] = id.toString();
                    //System.out.println(age);
                   //System.out.println(IDStr[z]);
                    
                    //If the add has already been ran through, this is the last
                    //page of the JSON query
                    //If the ID in the DB
                    for(b=0; b <= a; b++){
                        
                        if(IDStr[z].equals(IDOld[b]) == true){
                            
                            //Break from the JSON loop
                            i = j;
                            
                            //Skip a JSON Array
                            z++;

                        }
                        
                    }
                    //IDStr[z] = id.toString();
                   //System.out.println(IDStr[z]);
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
                    
                   //System.out.println(ageStr[z]);
                    
                   //System.out.println(ageStr[z]);
                    //System.out.println(IDStr[z]);
                    
                    Gumtree[z] = new JobAdd(IDStr[z],locationStr[z],
                            SalaryType[z],JobType[z],"Gumtree",ageStr[z],
                            titleStr[z], Integer.parseInt(distanceStr[z]));
                    
                   //System.out.println(Gumtree[z].Title);
                
                } catch(Exception e) {
                  
                   //System.out.println(e);
                    
                }
                
                try {
                    
                   //System.out.println(Gumtree[z].URL);

                    //SQL statement
                    Statement stmt = (Statement) conn.createStatement();
                    String query = "SELECT URL FROM jobfinder3.jf3 WHERE URL "+
                            "!= '" + Gumtree[z].URL + "'";

                    //Obtain the results
                    ResultSet Results = stmt.executeQuery(query);

                    c = 0;

                    //Find out how many null results there were for Description
                    if (Results.last()) {
                      rowcount = Results.getRow();
                      Results.beforeFirst();
                    }
                    while (Results.next() && c <= rowcount) {

                        // do your standard per row stuff 
                        URLListDB[c] = Results.getString("URL");
                       //System.out.println(URLListDB[c] + "\n=======\n"+ Gumtree[z].URL + "\n======\n");
                        //System.out.println(Gumtree[z].URL);
                        
                        //If the URL is not in the database it needs to be added to
                        //the list.
                        if(URLListDB[c].equals(Gumtree[z].URL) != true){

                           //System.out.println(Gumtree[z].URL);
                           InsertList[z] = Gumtree[z];
                           //System.out.println(InsertList[c].URL);
                            
                            c = c + 1;
                            d = d + 1;

                        }
                        
                        c = c + 1;

                    }

                } catch(SQLException e) {

                    //There will be an error if Unique ID is not unique.
                    //System.err.println(e);

                } catch(Exception e){
                    
                    
                    
                }
                    
                    //System.out.println(URLList[z]);
                
                }
            
                con.disconnect();
                //System.out.println(Gumtree[z].UniqueID);
            
            }
    
        z = 0; //Reset the z counter
        
       //System.out.println(rowcount);
    
        //Output for unparsed HTML
        String[] UnParsed = new String[rowcount];
        String[] Descriptions = new String[rowcount];
        String[] Names = new String[rowcount];
       //System.out.println(rowcount);
        
        for(x=0; x < rowcount; x++){
            
           
            try{
                
                UnParsed[x] = WebScrape.Connect(InsertList[x].URL, UnParsed[x]);
                //Re try connection if it failed
                if(UnParsed[x].contains("https://")){
                
                    UnParsed[x] = WebScrape.Connect(InsertList[x].URL, UnParsed[x]);
                
                }
                
                Document doc = WebScrape.Parse(UnParsed[x]);
                
                Descriptions[x] = WebScrape.Extract(doc, "vip-ad-description");
            
                //If the description wasnt there the add was taken down
                if(Descriptions[x].isEmpty() == true){

                    InsertList[x].URL = null;
                    rowcount = rowcount - 1;

                }else if (InsertList[x].ID != null){

                   Names[x] = WebScrape.Extract(doc, "seller-profile__name");
                  //System.out.println(Names[x]);

                   InsertList[x].SetDesc(Descriptions[x]);
                   InsertList[x].SetName(Names[x]);
                   InsertList[x].TitleDesc();
                  //System.out.println(Gumtree[x].Title + "\n" + InsertList[x].Name + "\n" + InsertList[x].Description);
                   InsertList[x].DBInsert(conn);   

                }
                
            }catch(Exception e){
                
                
                
            }
         
            }
        //BEGIN THE PROCESS FOR SEEK HERE
        }
    
    }
    
}
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
        JsonObject jsonObject;
        String[] IDStr = new String[20000];
        JobAdd[] Seek = new JobAdd[20000];
        String[] IDOld = new String[20000];
        String[] titleStr = new String[20000];
        String[] locationStr = new String[20000];
        String[] NameStr = new String[20000];
        String[] ageStr = new String[20000];
        JobAdd[] InsertList = new JobAdd[20000];
        String[] URLListDB = new String[20000];
        
        /*Get all 13 pages of gumtrees job listing. When there are 96 results
        Per page on gumtree there are 13 pages.*/
        /*New method to make sure that the new ID's havent been run through 
        already*/
        Connection conn = DBConnect.Connect();
        try {

           //SQL statement
           Statement stmt = (Statement) conn.createStatement();
           String query = "SELECT UniqueID FROM jobfinder3.jf3";

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
        
   jsonObject = WebScrape.JSONConnect("https://chalice-search-api.cloud.seek.c"+
           "om.au/search?siteKey=AU-Main&sourcesystem=houston&userqueryid=27f4"+
           "b80a6ed27e5e7adc7dee12103105-7545086&userid=d77ce28bc3ffd7b2db3b87"+
           "7dde5f0bda&usersessionid=d77ce28bc3ffd7b2db3b877dde5f0bda&eventCap"+
           "tureSessionId=0e1df78bf87292579e2b14f76000da90&where=All+Perth+WA&"+
           "page=", "&seekSelectAllPages=true&include=seodata&isDesktop=true",+
                   1);
   
   int totalCount = Integer.parseInt(jsonObject.get("totalCount").toString());
   
   int NumPages = totalCount/21;
        
    /*Output for this method is array of JSON*/
    /* THERE NEEDS TO BE A BREAK FROM LOOP IF THERE IS A DUPLICATE ID */
    for(i=1; i <= NumPages; i++){
        
        //Get JSON object.
        jsonObject = WebScrape.JSONConnect("https://chalice-search-api.cloud.s"+
        "eek.com.au/search?siteKey=AU-Main&sourcesystem=houston&userqueryid=27"+
        "f4b80a6ed27e5e7adc7dee12103105-7545086&userid=d77ce28bc3ffd7b2db3b87"+
        "7dde5f0bda&usersessionid=d77ce28bc3ffd7b2db3b877dde5f0bda&eventCap"+
        "tureSessionId=0e1df78bf87292579e2b14f76000da90&where=All+Perth+WA&"+
        "page=", "&seekSelectAllPages=true&include=seodata&isDesktop=true",i);

        //Naviagate down the JSON Tree and obtain appropriate information.
        JsonElement data = jsonObject.get("data");
        JsonArray resultListArr = data.getAsJsonArray();

       //System.out.println(resultListArr.toString());

        //Get all of the result List (0 - 95)
        for(x=0; x <= 21; x++, z++){

            try {

                //Job listings on a page
                JsonElement Ecs = resultListArr.get(x);
                //System.out.println(Ecs);
                JsonObject EcsObj = Ecs.getAsJsonObject();

                //Data we care about
                JsonElement id = EcsObj.get("id");
               //System.out.println(id);
                JsonElement title = EcsObj.get("title");
                JsonElement location = EcsObj.get("area"); //dif used to be location
                JsonElement age = EcsObj.get("listingDate"); //Needs to be formatted
                
                //Naviagate down the JSON Tree and obtain appropriate information.
                JsonElement advertiser = EcsObj.get("advertiser");
                String[] advertiserArr = advertiser.toString().split("description\":\"");
                //System.out.println(advertiserArr[1]);
                //System.out.println(Name.toString());
                //System.out.println(age);
                //NEXT TWO ARE ON PAGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //JsonElement salarytype = data2Obj.get("salarytype");!!!!!!!!!!!!!!!!
                //JsonElement jobtype = data2Obj.get("jobtype");!!!!!!!!!!!!!!!!!!!!!!!

                //Make them strings
                IDStr[z] = "S" + id.toString();
                //System.out.println(age);
                //System.out.println(IDStr[z]);

                //If the add has already been ran through, this is the last
                //page of the JSON query
                //If the ID in the DB
                for(b=0; b <= a; b++){

                    if(IDStr[z].equals(IDOld[b]) == true){

                        //Break from the JSON loop
                        i = NumPages;

                        //Skip a JSON Array
                        z++;

                    }

                }
                IDStr[z] = id.toString();
               //System.out.println(IDStr[z]);
                NameStr[z] = advertiserArr[1].replaceAll("\"}", "");
                NameStr[z] = NameStr[z].replaceAll("'", "");
                NameStr[z] = NameStr[z].replaceAll("&", "and");
                titleStr[z] = title.toString().substring(1, 
                        title.toString().length()-1);
                titleStr[z] = titleStr[z].replaceAll("'", "");
                titleStr[z] = titleStr[z].replaceAll("&", "and");
                locationStr[z] = location.toString().substring(1, 
                        location.toString().length()-1);
                ageStr[z] = age.toString().substring(1, 
                        age.toString().length()-1);

                Seek[z] = new JobAdd(IDStr[z],locationStr[z],
                        "","","Seek",ageStr[z],
                        titleStr[z], 1337);
                Seek[z].Name = NameStr[z];
                
               //System.out.println(Seek[z].Age);

               //System.out.println(Gumtree[z].Title);

            } catch(Exception e) {

               //System.out.println(e);

            }

            try {

                System.out.println(Seek[z].Title);
                System.out.println(Seek[z].URL);

                //SQL statement
                Statement stmt = (Statement) conn.createStatement();
                String query = "SELECT URL FROM jobfinder3.jf3 WHERE URL "+
                        "!= '" + Seek[z].URL + "'";

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
                    if(URLListDB[c].equals(Seek[z].URL) != true){

                       //System.out.println(Gumtree[z].URL);
                       InsertList[z] = Seek[z];
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

        }
    
        z = 0; //Reset the z counter
        
       //System.out.println(rowcount);
    
        //Output for unparsed HTML
        String[] UnParsed = new String[rowcount];
        String[] Bulleits = new String[rowcount];
        String[] Descriptions1 = new String[rowcount];
        String[] Descriptions = new String[rowcount];
        String[] JobType = new String[rowcount];
        String[] SalaryType = new String[rowcount];
        
        for(x=0; x < rowcount; x++){
            
           
            try{
                
                UnParsed[x] = WebScrape.Connect(InsertList[x].URL, UnParsed[x]);
                //Re try connection if it failed
                if(UnParsed[x].contains("https://")){
                
                    UnParsed[x] = WebScrape.Connect(InsertList[x].URL, UnParsed[x]);
                
                }
                
                Document doc = WebScrape.Parse(UnParsed[x]);
                
                System.out.println(doc.toString());
                
                Bulleits[x] = WebScrape.Extract(doc, "job-template__wrapper");
                System.out.println(Bulleits[x]);
                Descriptions1[x] = WebScrape.Extract(doc, "templatetext");
                System.out.println(Descriptions1[x]);
                Descriptions[x] = Bulleits[x] + Descriptions1[x];
                System.out.println(Descriptions[x]);
                JobType[x] = WebScrape.Extract(doc, "job-detail-work-type");
                System.out.println(JobType[x]);
                
                System.out.println(Descriptions[x]);
            
                //If the description wasnt there the add was taken down
                if(Descriptions[x].isEmpty() == true){

                    InsertList[x].URL = null;
                    rowcount = rowcount - 1;

                }else if (InsertList[x].ID != null){
                  //System.out.println(Names[x]);

                   InsertList[x].SetDesc(Descriptions[x]);
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
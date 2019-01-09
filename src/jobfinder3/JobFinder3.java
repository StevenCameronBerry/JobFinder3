/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobfinder3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.sql.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JobFinder3{
    
    public static void main(String[] args) throws Exception {

    while(true){
        
        //Initialize Attributes
        int  i, x;
        int z = 0, NumNotInDB = 0;
        JsonObject jsonObject;
        
        /*Get all 13 pages of gumtrees job listing. When there are 96 results
        Per page on gumtree there are 13 pages.*/
        /*New method to make sure that the new ID's havent been run through 
        already*/
        Connection conn = DBConnect.Connect();
        
        //Get an array of all of the adds in the DB
        ResultSet IDs = DBConnect.ID(conn, "Seek");
        int rowcount = DBConnect.NumAddsIn(IDs);
        String[] AddsInDB = new String[rowcount];
        AddsInDB = DBConnect.AlreadyIn(IDs, rowcount);
        
        jsonObject = WebScrape.JSONConnect("https://chalice-search-api.cloud.seek.c"+
                "om.au/search?siteKey=AU-Main&sourcesystem=houston&userqueryid=27f4"+
                "b80a6ed27e5e7adc7dee12103105-7545086&userid=d77ce28bc3ffd7b2db3b87"+
                "7dde5f0bda&usersessionid=d77ce28bc3ffd7b2db3b877dde5f0bda&eventCap"+
                "tureSessionId=0e1df78bf87292579e2b14f76000da90&where=All+Perth+WA&"+
                "page=", "&pageSize=100&seekSelectAllPages=true&include=seodata&isD"+
                "esktop=true&sortmode=ListedDate", + 1);

        //Number of job adds
        int AddsOnline = Integer.parseInt(jsonObject.get("totalCount").toString());
        
        JobAdd[] Seek = new JobAdd[AddsOnline];
        
        String[] IDStr = new String[AddsOnline];
        String[] titleStr = new String[AddsOnline];
        String[] locationStr = new String[AddsOnline];
        String[] NameStr = new String[AddsOnline];
        String[] ageStr = new String[AddsOnline];
        String[] JobType = new String[AddsOnline];
        String[] SalaryType = new String[AddsOnline];
        
       //System.out.println(AddsOnline);

        int NumPages = AddsOnline/101;
        
    /*Output for this method is array of JSON*/
    /* THERE NEEDS TO BE A BREAK FROM LOOP IF THERE IS A DUPLICATE ID */
    for(i=1; i <= NumPages; i++){
        
        //Get JSON object.
        jsonObject = WebScrape.JSONConnect("https://chalice-search-api.cloud.s"+
        "eek.com.au/search?siteKey=AU-Main&sourcesystem=houston&userqueryid=27"+
        "f4b80a6ed27e5e7adc7dee12103105-7545086&userid=d77ce28bc3ffd7b2db3b87"+
        "7dde5f0bda&usersessionid=d77ce28bc3ffd7b2db3b877dde5f0bda&eventCap"+
        "tureSessionId=0e1df78bf87292579e2b14f76000da90&where=All+Perth+WA&"+
        "page=", "&pageSize=100&seekSelectAllPages=true&include=seodata&isDesk"+
        "top=true&sortmode=ListedDate", i);

        //Naviagate down the JSON Tree and obtain appropriate information.
        JsonElement data = jsonObject.get("data");
        JsonArray resultListArr = data.getAsJsonArray();

       //System.out.println(resultListArr.toString());
       
       System.out.println("Page " + i + " of " + NumPages + "\n");
       
        //Get all of the result List (0 - 95)
        for(x=0; x <= 101; x++, z++){

            try {

                //Job listings on a page
                JsonElement Ecs = resultListArr.get(x);
                //System.out.println(Ecs);
                JsonObject EcsObj = Ecs.getAsJsonObject();

                //Data we care about
                JsonElement id = EcsObj.get("id");
               //System.out.println(id);
                JsonElement jobType = EcsObj.get("workType");
                JsonElement salary = EcsObj.get("salary");
                JsonElement title = EcsObj.get("title");
                JsonElement location = EcsObj.get("area"); //dif used to be location
                JsonElement age = EcsObj.get("listingDate"); //Needs to be formatted
                
                //Naviagate down the JSON Tree and obtain appropriate information.
                JsonElement advertiser = EcsObj.get("advertiser");
                String[] advertiserArr = advertiser.toString().split("description\":\"");

                //Make them strings
                IDStr[z] = id.toString();
                //System.out.println(age);
               //System.out.println(IDStr[z]+"\n");

                //If the add has already been ran through, this is the last
                //page of the JSON query
                //If the ID in the DB
                
                //System.out.println(i);
               //System.out.println(IDStr[z]);
                NameStr[z] = advertiserArr[1].replaceAll("\"}", "");
                NameStr[z] = NameStr[z].replaceAll("'", "");
                NameStr[z] = NameStr[z].replaceAll("&", "and");
               //System.out.println(NameStr[z]);
                titleStr[z] = title.toString().substring(1, 
                        title.toString().length()-1);
                titleStr[z] = titleStr[z].replaceAll("'", "");
                titleStr[z] = titleStr[z].replaceAll("&", "and");
                locationStr[z] = location.toString().substring(1, 
                        location.toString().length()-1);
                ageStr[z] = age.toString().substring(1, 
                        age.toString().length()-1);
                JobType[z] = jobType.toString().substring(1, 
                        jobType.toString().length()-1);
                SalaryType[z] = salary.toString().substring(1, 
                        salary.toString().length()-1);

                
                Seek[z] = new JobAdd(IDStr[z],locationStr[z],
                            SalaryType[z],JobType[z],"Seek",ageStr[z],
                            titleStr[z], 1337);
                Seek[z].Name = NameStr[z];
                
               //System.out.println(Seek[z].Age);

               //System.out.println(Gumtree[z].Title);

            } catch(Exception e) {

               //System.out.println(e);

            }

            }
        
        }
    
        //Find the number of new adds
        int MaxOnlineDB = -1;
        if(AddsOnline > AddsInDB.length){
            
            int NewAdds = AddsOnline - AddsInDB.length;
            System.out.println(NewAdds + " new adds found");
            MaxOnlineDB = AddsOnline;
            
        } else {
            
            int NewAdds = AddsInDB.length - AddsOnline;
            System.out.println(NewAdds + " new adds found");
            MaxOnlineDB = AddsInDB.length;
            
        }
        
        
        //Assign the Objects based on wether or not they are in the DB
        for(x = 0; x <= MaxOnlineDB; x++){

            for(String InDB: AddsInDB){

                try{

                    if(Seek[x].ID.equals(InDB)){

                        Seek[x].InDB = true;
                        
                        System.out.println("\n" + x + " done out of " + MaxOnlineDB + "\nMatching RESULT!!!\n");
                        System.out.println("\nObjects ID:\n" + Seek[x].ID + "\nID in DB:");
                        System.out.println(InDB);
                        
                        

                    }else{

                        //if it's not equal
                        Seek[x].InDB = false;
                        
                        System.out.println("\n" + x + " done out of " + MaxOnlineDB + "\nFalse RESULT!!!\n");
                        System.out.println("\nObjects ID:\n" + Seek[x].ID + "\nID in DB:");
                        System.out.println(InDB);

                    }

                }catch(Exception e){


                }

            }

        }
        
        
        //Output for unparsed HTML
        String[] UnParsed = new String[z];
        String[] Descriptions = new String[z];
        
        for(x=0; x < AddsOnline; x++){ //x < rowcount
           
            try{
                
                System.out.println(Seek[x].Title + " in DB: " + Seek[x].InDB);
                
                if(Seek[x].InDB == false){
                
                    UnParsed[x] = WebScrape.Connect(Seek[x].URL, UnParsed[x]);
                    //Re try connection if it failed
                    if(UnParsed[x].contains("https://")){

                        UnParsed[x] = WebScrape.Connect(Seek[x].URL, UnParsed[x]);

                    }

                    Document doc = WebScrape.Parse(UnParsed[x]);

                    //WebScrape.ClassName(doc);

                    //Job Description
                    Elements DescriptionEl = doc.getElementsByClass("_2e4Pi2B"); //"job-template__wrapper"
                    Descriptions[x] = DescriptionEl.text();

                   //System.out.println(Descriptions[x]);

                //If the description wasnt there the add was taken down
                
                    
                    Seek[x].SetDesc(Descriptions[x]);
                    Seek[x].TitleDesc();
                    Seek[x].DBInsert(conn);
                    
                }
                
                
            }catch(Exception e){
                
                
                
            }
         
            }
        //BEGIN THE PROCESS FOR SEEK HERE
        }
    
    }
    
}
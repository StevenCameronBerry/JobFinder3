package jobfinder3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.sql.*;
import java.util.Arrays;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JobFinder3{
    
    public static void main(String[] args) throws Exception {

        MainLoop: while(true){
        
        //Initialize Attributes
        int  Page, x, z = 0, q = 0, PageSize = 100;
        JsonObject jsonObject;
        
        //Connect to DB
        Connection conn = DBConnect.Connect();
        
        //Get an array of all of the adds in the DB
        ResultSet IDs = DBConnect.ID(conn, "Seek");
        int rowcount = DBConnect.NumAddsIn(IDs);
        String[] AddsInDB = new String[rowcount];
        AddsInDB = DBConnect.AlreadyIn(IDs, rowcount);
        
        //Get the number of Job Adds. THIS WILL NEED TO BE A GENERAL STRING INSIDE THE SETTINGS OBJ.
        jsonObject = WebScrape.JSONConnect("https://chalice-search-api.cloud.seek.com.au/search?siteKey=AU-Main&sourcesystem=houston&where=All+Perth+WA&page=","&pageSize=" + PageSize + "&seekSelectAllPages=false&isDesktop=true&sortmode=ListedDate", + 1);

        int AddsOnline = Integer.parseInt(jsonObject.get("totalCount").toString());
        
        //Class Initialization//
        //JobAdd Class
        JobAdd[] Seek = new JobAdd[AddsOnline];
        
        //JobAdd Builder Class (or it will be anyway)
        String[] IDStr = new String[AddsOnline];
        String[] titleStr = new String[AddsOnline];
        String[] locationStr = new String[AddsOnline];
        String[] NameStr = new String[AddsOnline];
        String[] ageStr = new String[AddsOnline];
        String[] JobType = new String[AddsOnline];
        String[] SalaryType = new String[AddsOnline];

        //Number of Pages
        int NumPages = AddsOnline/PageSize + 1;
        
        //Book Loop
        BookLoop: for(Page=1; Page <= NumPages; Page++){
        
            //Allready done if the page is one.
            if(Page != 1){
                
                jsonObject = WebScrape.JSONConnect("https://chalice-search-api.cloud.seek.com.au/search?siteKey=AU-Main&sourcesystem=houston&where=All+Perth+WA&page=", "&pageSize=" + PageSize + "&seekSelectAllPages=false&isDesktop=true&sortmode=ListedDate", + Page);
                
            }
            //Naviagate down the JSON Tree and obtain appropriate information.
            JsonElement data = jsonObject.get("data");
            JsonArray resultListArr = data.getAsJsonArray();

            //Number of adds on the page.
            int NumAddsPage = resultListArr.size() - 1;

            System.out.println("\n\n^^^^^^^^^^^^^^\nPage " + Page + " of " + NumPages);
            System.out.println(NumAddsPage + 1 + " adds on page.");

            //Get all of the result List (0 - 95)
            PageLoop: for(x=0; x <= NumAddsPage; x++, z++){

                //Extraction process
                JsonElement Ecs = resultListArr.get(x);
                JsonObject EcsObj = Ecs.getAsJsonObject();

                //Ignore Premium adds
                JsonElement isPremium = EcsObj.get("isPremium");
                if(isPremium.toString().equals("true")){

                    z--;
                    continue;

                }

                //
                JsonElement id = EcsObj.get("id");
                JsonElement jobType = EcsObj.get("workType");
                JsonElement salary = EcsObj.get("salary");
                JsonElement title = EcsObj.get("title");
                JsonElement location = EcsObj.get("area");
                JsonElement age = EcsObj.get("listingDate");

                //Naviagate down the JSON Tree and obtain appropriate information.
                JsonElement advertiser = EcsObj.get("advertiser");
                String[] advertiserArr = advertiser.toString().split(
                        "description\":\"");

                //Make them strings
                IDStr[z] = id.toString();

                //Stop if an add thats already in the DB has been come across.
                boolean InDB = Arrays.stream(AddsInDB).anyMatch(IDStr[z]::equals);
                if(InDB == true){
                    
                    break BookLoop;
                    
                }

                //Building Process
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
                JobType[z] = jobType.toString().substring(1, 
                        jobType.toString().length()-1);
                SalaryType[z] = salary.toString().substring(1, 
                        salary.toString().length()-1);
                SalaryType[z] = SalaryType[z].replaceAll("'", "");

                //Exchange from builder
                Seek[z] = new JobAdd(IDStr[z],locationStr[z],
                            SalaryType[z],JobType[z],"Seek",ageStr[z],
                            titleStr[z], 1337);
                Seek[z].Name = NameStr[z];

            }
        
            //Some logging
            System.out.println(z + " new adds scrapped from page.");
            System.out.println(z + " adds scraped in total out of " + AddsOnline + " adds online.");
            
        }
        
        //Output for unparsed HTML
        String[] UnParsed = new String[z];
        String[] Descriptions = new String[z];
        
        //Scrape the descriptions, format and insert into DB
        for(x=0; x < z; x++, q++){
            
            //Scrape Descriptions
            int[] Counters = new int[]{x,UnParsed.length,q};
            WebScrape Content = new WebScrape(Seek[q].URL, UnParsed[x],Counters);

            UnParsed[x] = Content.Connect();

            //Isolate Job Description
            Document doc = WebScrape.Parse(UnParsed[x]);

            //Insert
            Elements DescriptionEl = doc.getElementsByClass("_2e4Pi2B");
            Descriptions[x] = DescriptionEl.text();
            
            Seek[q].SetDesc(Descriptions[x]);
            Seek[q].TitleDesc();
            Seek[q].DBInsert(conn,Counters);
            
            //To prevent memory leaks
            if(x == 500){
                
                x = x - 500;
                z = z - 500;
                
            }
            
        }
    
        //Disconnect DB
        DBConnect.Disconnect(conn);
        
    }        
    
    }
    
}
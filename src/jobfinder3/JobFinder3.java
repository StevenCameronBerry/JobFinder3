/* The main method for downloading adds off of the internet. The encapsulation 
process that should always be followed is:
1. Scrape
2. Parse
3. Build

These stages should always be followed at any stage of the program. The Stages
of the program are: 
1. Main Loop
    1. Scrape
    2. Parse
2. In the Book Loop
    1. Scrape
    2. Parse
3. In The Page Loop
    1. Parse
    2. Build
4. On the Insert DB Loop.
    1. Scrape
    2. Parse
    3. Build
*/
package jobfinder3;

import com.google.gson.JsonObject;
import java.sql.*;

public class JobFinder3{
    
    public static void main(String[] args) throws Exception {
        
        //Initialize Variables
        String[] Adds = {"Seek","Gumtree","Indeed"};
        int itr = 0;
        JobAddBuilder builder;
        JobAddEngineer engineer;

        MainLoop: while(true){
        
            //Initialize Attributes
            int  Page = 1, AddItr, z = 0, q = 0, PageSize, a = 0;
            JsonObject jsonObject;
            Scrape ScrapeObj;
            Parse ParseObj;
            
            //To determine which classes to use.
            switch(Adds[itr]) {
                
                case "Seek" :
                    
                    System.out.println("\nSEEK SEEK SEEK SEEK");
                    
                    ScrapeObj = new ScrapeSeek();
                    ParseObj = new ParseSeek();
                    builder = new JobAddBuilderSeek();
                    engineer = new JobAddEngineerSeek(builder);
                    PageSize = 100;

                    break;
                    
                case "Gumtree" :
                    
                    System.out.println("\nGUMTREE GUMTREE GUMTREE GUMTREE");
                    
                    ScrapeObj = new ScrapeGumtree();
                    ParseObj = new ParseGumtree();
                    builder = new JobAddBuilderGumtree();
                    engineer = new JobAddEngineerGumtree(builder);
                    PageSize = 96;
                    break;

                //case "Indeed":
                    
                    //break;
                // You can have any number of case statements.
                default : // Optionals

                    ScrapeObj = new ScrapeSeek();
                    ParseObj = new ParseSeek();
                    builder = new JobAddBuilderSeek();
                    engineer = new JobAddEngineerSeek(builder);
                    PageSize = 100;
                    break;
                    
            }

            //Connect to DB
            Connection conn = DBConnect.Connect();

            //Get an array of all of the adds in the DB MODE OBJ FOR CUSTOM EACH SITE
            ResultSet IDs = DBConnect.ID(conn, Adds[itr]);
            int rowcount = DBConnect.NumAddsIn(IDs);
            String[] AddsInDB = new String[rowcount];
            AddsInDB = DBConnect.AlreadyIn(IDs, rowcount);

            //SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE 
            String IndxURL = ScrapeObj.BuildString(Page, PageSize); 
            ScrapeObj.SetString(IndxURL, Page);
            ScrapeObj.ScrapeIndx(Page);

            //PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE
            ParseObj.SetJsonIndx(ScrapeObj.GetJsonIndex());
            ParseObj.SetStrIndx(ScrapeObj.GetStringIndex());
            ParseObj.NavigateML();
            int AddsOnline = ParseObj.AddsOnline();
            int NumPages = ParseObj.NumPages(PageSize);
            
            //Build
            JobAdd[] add = new JobAdd[AddsOnline];
            
            System.out.println(AddsOnline + " adds found on site.");
            System.out.println("contained in " + NumPages + " pages.");

            //Book Loop
            BookLoop: for(Page=1; Page <= NumPages; Page++){

                //SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE
                if(Page != 1){
                    
                    IndxURL = ScrapeObj.BuildString(Page, PageSize);
                    ScrapeObj.SetString(IndxURL, Page);
                    ScrapeObj.ScrapeIndx(Page);
                    
                    //PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE
                    ParseObj.SetJsonIndx(ScrapeObj.GetJsonIndex());
                    ParseObj.SetStrIndx(ScrapeObj.GetStringIndex());
                    
                }
                
                //Naviagate down the JSON Tree and obtain appropriate information.
                ParseObj.NavigateBL();

                System.out.println("\n^^^^^^^^^^^^^^\nPage " + Page + " of " + 
                        NumPages);
                
                //Number of adds on the page.
                int NumAddsPage = ParseObj.NumAddsPage();
                

                //Get all of the result List (0 - 95)
                PageLoop: for(a=0,AddItr=0; AddItr <= NumAddsPage; AddItr++, z++, a++){

                    //PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE 
                    //Extraction process
                    ParseObj.NavigatePL(AddItr);

                    //Ignore Premium adds
                    if (ParseObj.Ignore(z) == true){
                        
                        z--;
                        a--;
                        continue;
                        
                    }

                    //Parse Page Specific Attrs
                    ParseObj.SetPage();
                    
                    //Stop if an add thats already in the DB has been come across.
                    boolean DBCheck = ParseObj.CheckDup(AddsInDB);
                    if (DBCheck == true){
                        
                        System.out.println(a + " new adds scrapped from page.");
                        System.out.println(z + " adds scraped in total out of " + AddsOnline
                        + " adds online.");
                        break BookLoop;
                        
                    }
                    
                    //BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD
                    //Encapsulate Parse Object into Builder
                    builder.SetParse(ParseObj);
                    engineer.SetBuilder(builder);
                    engineer.MakePage();
                    add[z] = engineer.GetJobAdd();
                    //to stop it from mutating.
                    JobAdd JobAddCopy = new JobAdd(add[z]);
                    add[z] = JobAddCopy;
                    
                }
                    //Some logging
                    System.out.println(a + " new adds scrapped from page.");
                    System.out.println(z + " adds scraped in total out of " + AddsOnline
                            + " adds online.");

            }
            
            //Need to add a final counter and make it equal to z here !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
            //Scrape the descriptions, format and insert into DB
            for(int x=0; x < z; x++, q++){

                //SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE
                int[] Counters = new int[]{x,z,q};
                String URL = ScrapeObj.BuildString(Integer.parseInt(add[q].GetID()));
                ScrapeObj.SetString(URL);
                ScrapeObj.ScrapeIndx(Counters);

                //PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE
                //Encapsulate Scrape Obj
                ParseObj.SetJsonIndx(ScrapeObj.GetJsonIndex());
                ParseObj.SetStrIndx(ScrapeObj.GetStringIndex());

                //Isolate Job Description
                ParseObj.SetDB();

                //BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD
                builder.SetJobAdd(add[q]);
                engineer.SetBuilder(builder);
                engineer.MakeDB();
                add[q] = engineer.GetJobAdd();
                
                //Insert into the DB.
                DBConnect.InsertAdd(add[q],Counters,conn);

                //To prevent memory leaks
                if(x == 500){

                    x = x - 500;
                    z = z - 500;

                }

            }

            //Disconnect DB
            DBConnect.Disconnect(conn);

            //Increase the iterator for which website to scrape from
            itr++;
            if(itr == 2){

                itr = 0; //Go back to zero or the adds index will be out of range.

            }
    
        }
    
    }
}
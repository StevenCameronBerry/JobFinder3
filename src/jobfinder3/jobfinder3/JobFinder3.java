/* The main method for downloading adds off of the internet. The encapsulation 
process that should always be followed is:
1. Scrape
2. Parse
3. Build

These stages should always be followed at any stage of the program. The Stages
of the program are: 
1. Scrape Loop
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

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DO EDIT DISTANCE AND REPLACING WITH BASE WORD ON THE SCRAPING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

package jobfinder3;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

//Gumtree Package
import Gumtree.JobAddBuilderGumtree;
import Gumtree.JobAddEngineerGumtree;
import Gumtree.ParseGumtree;
import Gumtree.ScrapeGumtree;
//Seek Package
import Seek.JobAddBuilderSeek;
import Seek.JobAddEngineerSeek;
import Seek.ParseSeek;
import Seek.ScrapeSeek;
import applying.Builder;
import applying.Engineer;
import applying.JobApplication;
import applying.JobApplicationBuilder;
import applying.JobApplicationEngineer;
import applying.JobTypes;
//Indeed Package
import Indeed.JobAddBuilderIndeed;
import Indeed.JobAddEngineerIndeed;
import Indeed.ParseIndeed;
import Indeed.ScrapeIndeed;

public class JobFinder3 {

	public static void main (String[] args) throws Exception {
        
        //Initialize Variables
        String[] Websites = {"Gumtree","Indeed"};
        HashMap<String, String[]> Words = new HashMap<String, String[]>();
        int itr = 0;
        JobAddBuilder builder;
        JobAddEngineer engineer;
        String URL;

        ScrapeLoop: while (true){
        
            //Initialize Attributes
            int  Page = 1, AddItr, z = 0, AddCtr = 0, PageSize, a = 0, AddCtr2 = 0;
            Scrape ScrapeObj;
            Parse ParseObj;
            
            //To determine which classes to use.
            switch (Websites[itr]) {
                
                case "Gumtree" :
                    
                	System.out.println("\nGUMTREE GUMTREE GUMTREE GUMTREE");
                    
                    ScrapeObj = new ScrapeGumtree();
                    ParseObj = new ParseGumtree();
                    builder = new JobAddBuilderGumtree();
                    engineer = new JobAddEngineerGumtree(builder);
                    PageSize = 96;
                    
                    break;
                    
                case "Seek" :
                    
                	System.out.println("\nSEEK SEEK SEEK SEEK");
                    
                    ScrapeObj = new ScrapeSeek();
                    ParseObj = new ParseSeek();
                    builder = new JobAddBuilderSeek();
                    engineer = new JobAddEngineerSeek(builder);
                    PageSize = 100;

                    break;

                case "Indeed":
                    
                    System.out.println("\nINDEED INDEED INDEED INDEED");
                    
                    ScrapeObj = new ScrapeIndeed();
                    ParseObj = new ParseIndeed();
                    builder = new JobAddBuilderIndeed();
                    engineer = new JobAddEngineerIndeed(builder);
                    PageSize = 50;
                    
                    break;

//                case "Facebook" :
//                    
//                    System.out.println("\nFACEBOOK FACEBOOK FACEBOOK FACEBOOK");
//                    
//                    ScrapeObj = new ScrapeGumtree();
//                    ParseObj = new ParseGumtree();
//                    builder = new JobAddBuilderGumtree();
//                    engineer = new JobAddEngineerGumtree(builder);
//                    PageSize = 96;
//                    
//                    break;
//                    
//                case "Linkedin" :
//                    
//                    System.out.println("\nLINKEDIN LINKEDIN LINKEDIN LINKEDIN");
//                    
//                    ScrapeObj = new ScrapeGumtree();
//                    ParseObj = new ParseGumtree();
//                    builder = new JobAddBuilderGumtree();
//                    engineer = new JobAddEngineerGumtree(builder);
//                    PageSize = 96;
//                    
//                    break;
                    
                default : //Optionals

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
            ResultSet IDs = DBConnect.ID(conn, Websites[itr]);
            int rowcount = DBConnect.NumAddsIn(IDs);
            String[] AddsInDB = new String[rowcount];
            AddsInDB = DBConnect.AlreadyIn(IDs, rowcount);

            //SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE 
            String IndxURL = ScrapeObj.BuildString(Page, PageSize); 
            System.out.println(IndxURL);
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
            BookLoop: for (Page=1; Page <= NumPages; Page++) {

                //SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE
                if (Page != 1) {
                    
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
                PageLoop: for (a=0, AddItr=0; AddItr < NumAddsPage; AddItr++, z++, a++) {

                    //PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE 
                    //Extraction process
                    ParseObj.NavigatePL(AddItr);

                    //Ignore Premium adds
                    if (ParseObj.Ignore(z, AddsInDB) == true) {
                        
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
                        System.out.println(z + " adds scraped in total out of "
                                + AddsOnline + " adds online.");
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
                    System.out.println(z + " adds scraped in total out of " + 
                            AddsOnline + " adds online.");

            }
            
            //This is to get a Word array (the variable "Words") so that they can be searched for in the DB loop against each add.
            for (JobTypes JobType : JobTypes.values()) {
            	
            	//Initialize Builder and Engineer
    			JobApplicationBuilder BuilderJobApplic = new Builder(JobType.JobTitle());
    			JobApplicationEngineer EngineerJobApplic = new Engineer(BuilderJobApplic);
    			
    			//Create the Words array
    			Words.put(JobType.JobTitle(), EngineerJobApplic.CombWords(JobType.OrigWords()));
    			
            }
            
            //Need to add a final counter and make it equal to z here !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED50 FOR EACH FOR INDEED
            //for(int x=0; x < z; x++, q++){System.out.println(add[q].GetTitle());}
            //Scrape the descriptions, format and insert into DB
            DBLoop: for (int x=0; x < z; x++, AddCtr++) {

                //SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE SCRAPE
                int[] Counters = new int[]{x,z,AddCtr};
                
                URL = ScrapeObj.BuildString(add[AddCtr].GetID());
                
                //System.out.println(add[q].GetID());
                System.out.println(URL);
                
                System.out.println(Counters[2] + 1 + 
                        " add descriptions scraped out of " + Counters[1] + 
                        " descriptions to be scraped.");
                ScrapeObj.SetString(URL);
                ScrapeObj.ScrapeIndx(Counters);

                //PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE PARSE
                //Encapsulate Scrape Obj
                ParseObj.SetJsonIndx(ScrapeObj.GetJsonIndex());
                ParseObj.SetStrIndx(ScrapeObj.GetStringIndex());

                //Isolate Job Description
                ParseObj.SetDB();

                //BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD BUILD
                builder.SetJobAdd(add[AddCtr]);
                engineer.SetBuilder(builder);
                engineer.MakeDB();
                add[AddCtr] = engineer.GetJobAdd();
                //Insert into the DB.
                //Thread.sleep(10000);
                
                //System.out.println(add[AddCtr].GetTitleDesc());
                
                
                //Find out if any key word was in the add, than mark it as such in the DB
                for (String Word: Words.keySet()) {
                	
                	//System.out.println(Word + ":");
                	//Initialize Builder and Engineer
        			JobApplicationBuilder BuilderJobApplic = new Builder(Word);
        			JobApplicationEngineer EngineerJobApplic = new Engineer(BuilderJobApplic);
                	
                	int Ctr = Words.get(Word).length;
                	for (int i = 0; i < Ctr; i++) {
                		
                		//If the sub-word was found, mark it as such in the DB
                		if (add[AddCtr].GetTitleDesc().contains(Words.get(Word)[i])) {
                			
                			add[AddCtr].SetKeyWord(Word);
                			
                			System.out.println(add[AddCtr].GetKeyWord());
                			
                			System.out.println("Key Word Found: " + Word);
                			
                			String Message = EngineerJobApplic.BuildMessage(add[AddCtr]);
                			
                			add[AddCtr].SetMessage(Message);
                			
                		}
                		
                	}
    				
    			}
                
                DBConnect.InsertAdd(add[AddCtr],Counters,conn);
                
                //To prevent memory leaks
                if(x == 500){

                    x = x - 500;
                    z = z - 500;

                }

            }

            //Disconnect DB
            DBConnect.Disconnect(conn);
            
            //Increase the iterator for which website to scrape from
            //Only doing gumtree atm cause seek cooked it
            itr++;
            if (itr == 1) {
            	
            	itr = 0;
            	
            //If the itr is at the second one, re-start the downloading.
            } else if (itr == 1) {
            	
            	continue;
            	
            }
            
	        //JUST SETTLE ON 2 AND NOT CRASHING UNTILL YOU ARE ACTUALLY LOOKING FOR A JOB CUNT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!    
	        //Connect to DB
	        Connection conn2 = DBConnect.Connect();
        
        }
        
	}
	
}
        
        /*This part of the main algorithm is for actually applying to the jobs*/
        //Create an array of JobApplications
        //Iterate through the different types of Job Types
        /*ApplyLoop: for (JobTypes JobType : JobTypes.values()) {
        	
        	Populate the attributes for the Job Application object.
        	To initialize this object you need:
        	
        	1. Job Title
        	2. Orig Words
        	3. Messages for each website
        	
        	
        	//Initialize Builder and Engineer
			JobApplicationBuilder BuilderJobApplic = new Builder(JobType.JobTitle());
			JobApplicationEngineer EngineerJobApplic = new Engineer(BuilderJobApplic);
			
			//Build the Job Application Object
			EngineerJobApplic.CombWords(JobType.OrigWords());
			EngineerJobApplic.BuildSQLQuery();
			
			//Get the Job Application and make it search the DB than put it back into the builder
			JobApplication JobApp = BuilderJobApplic.GetJobApplication();
			JobApp.DBExtract(conn2);
			EngineerJobApplic.BuildMessage(JobApp);
			
			//Pre-Apply (obtain all information for header and payload)
			
			//Apply to all of the appropriate Job Adds. But first initialize the job applicant which has all appropriate job adds.
			JobApplication JobApplicant = BuilderJobApplic.GetJobApplication();
			JobApplicant.Apply();
      	  
        }
        
        DBConnect.Disconnect(conn2);
        
   }*/
        
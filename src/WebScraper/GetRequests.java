/*
 * This package downloads all of the adds off of the internet for 
 */
package WebScraper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequests{
        https://www.gumtree.com.au/ws/search.json?categoryId=9302&locationId=3008546&locationStr=SouthPerth&offerType=OFFER&pageNum=1&pageSize=96&previousCategoryId=9302&radius=50&sortByName=date
    //Initialize Attributes
    int status, i, j, x, y;
    int z = 0;
    String JSONStr;
    String[] uniqueIDStr = new String[96*13];
    String[] IDStr = new String[96*13];
    String[] titleStr = new String[96*13];
    String[] locationStr = new String[96*13];
    String[] distanceStr = new String[96*13];
    String[] ageStr = new String[96*13];
    String[] URLStr = new String[96*13];
//Get the current time in this format YYYY-MM-DD HH:MM:SS
                    if(ageStr[z].equals("Yesterday") == true){
                        
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, -1);
                        String Yesterday = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
                        ageReal[z] = Yesterday;

                    }else if(ageStr[z].contains("minutes ago") == true){
                        
                        //System.out.println("had minutes ago");
                        
                        //Get how many minutes it was
                        ageMins = Integer.parseInt(ageReal[z].replace
                            (" minutes ago", ""));
                        
                        //Format Data
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MINUTE, - ageMins);
                        String MinsAgo = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
                        ageReal[z] = MinsAgo;

                    }else if(ageStr[z].contains("hours ago") == true){
                        
                        //System.out.println("had hours ago");
                        
                        //Get how many hours it was
                        ageHours = Integer.parseInt(ageReal[z].replace
                            (" hours ago", ""));
                        
                        //Format Data
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.HOUR, - ageHours);
                        String HoursAgo = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
                        ageReal[z] = HoursAgo;

                    }else{ //If it just had a date listed
                        
                        String DatePosted = new SimpleDateFormat
                            ("YYYY-MM-dd HH:MM:ss").format(ageReal[z]);
                        
                        //System.out.println(DatePosted);
                        
                    }
    /*Get all 13 pages of gumtrees job listing. When there are 96 results
    Per page on gumtree there are 13 pages.

    Output for this method is array of JSON*/    
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
            //System.out.println(content);
            
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

                    //Make them strings
                    IDStr[z] = id.toString();
                    uniqueIDStr[z] = "G" + id.toString();
                    //Get rid of " at the end of the strings.
                    titleStr[z] = title.toString().substring(1, 
                            title.toString().length()-1);
                    locationStr[z] = location.toString().substring(1, 
                            location.toString().length()-1);
                    distanceStr[z] = distance.toString().substring(1, 
                            distance.toString().length()-1);
                    ageStr[z] = age.toString().substring(1, 
                            age.toString().length()-1);
                    URLStr[z] = "https://www.gumtree.com.au/s-ad/" + IDStr[z];

                    String Add = "ID: " + IDStr[z] + "\nTite: " + titleStr[z] + 
                            "\nLocation: " + locationStr[z] + "\nDistance: " +
                            distanceStr[z] + "\nAge: " + ageStr[z] + "\nURL: " + 
                            URLStr[z] + "\n\n===================\n\n";

                    System.out.println(Add);
                
                } catch(Exception e) {
                  
                    //Do nothin
                    
                }   
                    
                }
             
                con.disconnect();
            
            }
}

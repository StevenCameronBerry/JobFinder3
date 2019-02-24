/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobfinder3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Element;

public class WebScrape {
    
    private static int y, status;
    private static String Descriptions, JSONStr;
    private String URL, UnParsed;
    private int[] x;
    
    WebScrape(String URL, String UnParsed, int[] x){
        
        this.URL = URL;
        this.UnParsed = UnParsed;
        this.x = x;
        
    }
    
    //Method to connect to JSON queries
    static JsonObject JSONConnect(String Query1, String Query2, int i) throws 
            Exception{
        
        //Web Scrape all of the job adds off of gumtree
        URL url = new URL(Query1 + i + Query2);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //Response type
        status = con.getResponseCode();
        
        //For failed connections
        if(status != 200){
            
            con.disconnect();
            con = (HttpURLConnection) url.openConnection();
            Thread.sleep(1000);
            con.setRequestMethod("GET");
            
        }

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
        
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(JSONStr);
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        
        con.disconnect();
        
        return jsonObject;
        
    }

    //Connect to each URL in an array.
    //IF THE OUTPUT OF THIS DOES NOT CONTAIN "https://" IT IS NOT A URL EVERYTHING PARSED
    String Connect(){

        try{

            URL url = new URL(this.URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            status = con.getResponseCode();
            
            //For failed connections
            if(status != 200){

                Thread.sleep(1000);
                con.setRequestMethod("GET");

            }
            
            BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            System.out.println(x[2] + 1 + " add descriptions scraped out of " + x[1] + " descriptions to be scraped.");

            try{

                UnParsed = content.toString();
                return UnParsed;

            } catch(Exception e){

                return URL + "Responded but did not parse";

            }
            

        } catch(Exception e){

           System.out.println(e + " was the error!!!!");
           System.out.println(URL + " did not respond");
           return URL;

        }
    
    }
    
    static Document Parse(String UnParsed){

        //To HTML
        Document doc = Jsoup.parse(UnParsed);

        return doc;
        
    }

    //ADEVERTISER NAME GUMTRE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! seller-profil+e__name    or     vip-ad-description
    static String Extract(Document doc, String ElementsClass){
        
        try{

            //Get Description
            Elements DescriptionEl = doc.getElementsByClass(ElementsClass);
            Descriptions = DescriptionEl.attr(Descriptions);
            return Descriptions;

        } catch(Exception e) {

            return "Description did not work";

        }
        
    }
    

    //A method to find the class name in a list from a document
    static void ClassName(Document doc){
        
        //System.out.println(doc.toString());
        Elements divs = doc.select("div");
        for(Element elem : divs){
            System.out.println(elem.className()); //get all elements inside div
            System.out.println("\n========\n");
            System.out.println(elem.text() + "\n======\n");
          }
        
    }
    
}
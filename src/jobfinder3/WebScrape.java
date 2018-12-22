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

public class WebScrape {
    
    private static int y, status;
    private static String Descriptions, JSONStr;
    
    //Method to connect to JSON queries
    static JsonObject JSONConnect(String Query1, String Query2, int i) throws 
            Exception{
        
        //Web Scrape all of the job adds off of gumtree
        URL url = new URL(Query1 + i + Query2);
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
        
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(JSONStr);
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        
        con.disconnect();
        
        return jsonObject;
        
    }

    //Connect to each URL in an array.
    //IF THE OUTPUT OF THIS DOES NOT CONTAIN "https://" IT IS NOT A URL EVERYTHING PARSED
    static String Connect(String URLList, String UnParsed){

        try{

            URL url = new URL(URLList);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            try{

                UnParsed = content.toString();
                return UnParsed;

            } catch(Exception e){

                return URLList + "Responded but did not parse";

            }
            

        } catch(Exception e){

           System.out.println(e + " was the error!!!!");
           System.out.println(URLList + " did not respond");
           return URLList;

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
            Elements DescriptionEl = doc.getElementsContainingText(ElementsClass);
            Descriptions = DescriptionEl.text();
            return Descriptions;

        } catch(Exception e) {

            return "Description did not work";

        }
        
    }
    
}

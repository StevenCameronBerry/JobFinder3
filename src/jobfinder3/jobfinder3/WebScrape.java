/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobfinder3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

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
    
    //Method to connect to JSON queries
    public static JsonObject JSONConnect(String Website) throws Exception{
        
        //Web Scrape all of the job adds off of gumtree
        URL url = new URL(Website);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //Response type
        status = con.getResponseCode();
        //This could potentially run forever!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        while(status != 200) {
        //For failed connections
            
            con.disconnect();
            con = (HttpURLConnection) url.openConnection();
            Thread.sleep(1000);
            con.setRequestMethod("GET");
            
            status = con.getResponseCode();
            
        }

        ////System.out.println(status);

        //Parse the information into a string, "content"
        BufferedReader in = new BufferedReader(
                
        //Change this to being try catch in conjunction with the above if statement!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
    public static String Connect(String URL, int[] x){

        try{

            URL url = new URL(URL);
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

            try{

                String UnParsed = content.toString();
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
    
    //Make Post Requests
    public static int POST(URL URLIn, String data, String AddUrl) throws IOException{

    	//The URL
    	String url = URLIn.toString();
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		int l = data.length();
		//User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0
		//Accept: */*
		/*Accept-Language: en-US,en;q=0.5
		Accept-Encoding: gzip, deflate, br
		Referer: https://www.gumtree.com.au/s-ad/1224829493
		X-CSRF-TIME: 21c3f9e8d5a8f492a118047edc1af413
		X-CSRF-TOKEN: cggmESi1cJovwpnuDwI94A
		X-Requested-With: XMLHttpRequest
		Content-Type: multipart/form-data; boundary=---------------------------92542923915479
		Origin: https://www.gumtree.com.au
		Content-Length: 1245
		Connection: keep-alive
		Cookie: machId=olRyFLqGMApEdTPpSlqufX0QJEK1xIMDyoNb19Uf7IIKhNDMq1-Y2soWt2q2PUDtTWlq-jJe-2wUwes_glxD7JXqyor87_akQYkb; up=%7B%22ln%22%3A%22822897446%22%2C%22nps%22%3A%2296%22%2C%22lh%22%3A%22l%3D3008507%26r%3D50%7Cl%3D3008845%26k%3Drural%26r%3D0%26icr%3Dfalse%7Cl%3D3008303%26r%3D0%26icr%3Dfalse%7Cl%3D3008546%26r%3D0%22%2C%22lbh%22%3A%22l%3D3008303%26c%3D20031%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26k%3Dlpg%2520ex%2520taxi%26c%3D18320%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26c%3D20034%26r%3D0%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%26icr%3Dfalse%22%2C%22rva%22%3A%221198763135%2C1223202968%2C1220336329%2C1213928839%2C1222099675%2C1216939616%2C1224605432%2C1224576740%2C1224204012%2C1224186903%2C1094006120%2C1224829493%22%2C%22lsh%22%3A%22l%3D3008845%26k%3Drural%26c%3D20031%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D3008546%26k%3Dtoyota%2520hiace%26p%3D2%26c%3D9299%26r%3D50%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26k%3Dlpg%2520ex%2520taxi%26c%3D18320%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D3008546%26c%3D18342%26r%3D50%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26c%3D20034%26r%3D0%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%26icr%3Dfalse%22%2C%22ls%22%3A%22l%3D3008546%26r%3D0%26sv%3DLIST%26sf%3Ddate%22%7D; optimizelyEndUserId=oeu1560869782949r0.17214926398737884; AMCV_50BE5F5858D2477A0A495C7F%40AdobeOrg=2096510701%7CMCIDTS%7C18108%7CMCMID%7C58386376110258854973203384631851669600%7CMCAAMLH-1564849448%7C8%7CMCAAMB-1565195273%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1564597673s%7CNONE%7CvVersion%7C2.0.0%7CMCCIDH%7C-1515736465; cto_lwid=e8637b52-84c2-4b32-972f-4c0322d89998; _ga=GA1.3.1587788353.1560869785; __utma=160852194.1587788353.1560869785.1564244649.1564590467.7; __utmz=160852194.1560869785.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __qca=P0-419801697-1560869785456; _fbp=fb.2.1560869787906.1226741766; ki_t=1560869788532%3B1564590473664%3B1564594418208%3B18%3B136; ki_r=; aam_dfp=aamsegid%3D6797281%2C6880889%2C7087286%2C7220740%2C7329284%2C7329285%2C7329301%2C7329276%2C7333809%2C8331089%2C8331046%2C8269554%2C8444126%2C8448793%2C8465423%2C8978953%2C8458219%2C11996984; aam_tnt=aamsegid%3D6797281%2Caamsegid%3D6880889; aam_uuid=58887149265166189473227778725039339418; __gads=ID=4b4b5f50f8e55f3e:T=1560869785:S=ALNI_Ma4OLGgqRu9xJxlpxqIw8ztXRVNHw; cto_idcpy=6daed043-c530-41df-95e2-4ab3c6941a69; _gcl_au=1.1.1529470621.1561973614; fbm_597906056993680=base_domain=.www.gumtree.com.au; sid2=1f8b0800000000000000b33435b2b034b03434b5b434327730d40332f40ccd80b4b18583836f7e55664e4ea2bea99e81824678665e4a7e79b1825f8882a1819e81b50250c0ccc45aa102441495599959e819682ab8a72667e7eb1b19181a0091a1825b66516a5a7e853e48d221c03bdbd2c4c43c2da7ca3b3137d8d12dc332c3d0c70000e1397a1f82000000; cto_bundle=uc_D1V9EQllKcFlTcjlDdzI4S1YlMkI5M1pZWVZ1aldoV0F1VUdRWCUyQk9kaWEyOFpVeTAyVGc2T05RYVdGdzRwOGlXamxMZTI4YVo2MmkyNHMybHhFdVZWd1phT3FoM2dmJTJGa3Awa21GOGI3N0tMS1NKTzYxcDZsQkxPME92SVBHS0dPRFlGUg; gtj.session=eyJjc3JmU2VjcmV0IjoiMTlkTmNZMnh4eW92V0duTHA0ZW5VUVJ1IiwidWRpZCI6IjgwMWVlMzdiNjkxNWQ0Mzg1Zjg2YmI5ZWFiMDc3MWJlIiwidG9rZW4iOiJjZGFhZTk5NGVlODI3OGRlN2JjMTBkMGQ2ZDFlMjBkNjUzNDM1Zjk2Mzk3OWJjOWY4ZDQxN2Y1Y2NhNmQ1M2E3IiwiZW1haWwiOiJzdGV2ZW5iZXJyeTMwNUBnbWFpbC5jb20iLCJ1c2VySWQiOiI5NTI4OTA5MTU5OTI3In0=; gtj.session.sig=1iSUsJbqqYSbnPAqnrMqKlsnCtU; G_ENABLED_IDPS=google; fbm_1520623457982850=base_domain=; rl_mid=TEJUTOhlQQJ2VktEZzo1SIleKdVVERWWTVkUuplewkT; AMCVS_50BE5F5858D2477A0A495C7F%40AdobeOrg=1; bs=%7B%22st%22%3A%7B%7D%7D; __utmc=160852194; GED_PLAYLIST_ACTIVITY=W3sidSI6InJQdkQiLCJ0c2wiOjE1NjQzMTE2OTUsIm52IjoxLCJ1cHQiOjE1NjQzMTE1OTYsImx0IjoxNTY0MzExNjkyfV0.; _gid=GA1.3.1297796258.1564590467; _gat=1
		TE: Trailers*/
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
		con.setRequestProperty("Content-Type", "multipart/form-data");// boundary=---------------------------92542923915479
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "*/*");
		//con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		con.setRequestProperty("Origin", "https://www.gumtree.com.au");
		con.setRequestProperty("Connection", "keep-alive");
		//con.setRequestProperty("TE","Trailers");
		con.setRequestProperty("Referer", AddUrl);

		//Form data
		String urlParameters = data;
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		System.out.println(con.getResponseMessage());
		System.out.println(con.getContent());
		System.out.println(con.getErrorStream());
		System.out.println(AddUrl);
		System.out.println(con.getContent());

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());
		
		System.out.println(responseCode);
		
		return responseCode;
    
    }
    
    public static Document Parse(String UnParsed){

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
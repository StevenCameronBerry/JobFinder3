package applying;
import java.awt.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import javax.net.ssl.HttpsURLConnection;

public class HTTPtest {

	public static void hurr(String[] args) throws Exception {
		
		String URLIn = "";
		
		URL url = new URL("https://www.gumtree.com.au/j-contact-seller.html");
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection)con;
		http.setRequestMethod("POST"); // PUT is another valid option
		http.setDoOutput(true);
		
		//String data = "message:" + URLEncoder.encode(" Hi, I'm interested in the \"Telemarketing job\" job you've listed on Gumtree. I look forward to hearing from you. Thanks.", "UTF-8" ) + " fromName:" + URLEncoder.encode(" fred", "UTF-8") + " from:" + URLEncoder.encode(" stevenberry305@gmail.com", "UTF-8") + " sendCopyToSender:" + URLEncoder.encode(" false", "UTF-8") + " adId:" + URLEncoder.encode(" 1224775027", "UTF-8") + " marketingConsent:" + URLEncoder.encode(" true", "UTF-8") + " sender:" + URLEncoder.encode(" react", "UTF-8");
		//"" sender:" + URLEncoder.encode(" react", "UTF-8");
		
		/*
		Host: www.gumtree.com.au
		User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0
		Accept: 
		Accept-Language: en-US,en;q=0.5
		Accept-Encoding: gzip, deflate, br
		Referer: https://www.gumtree.com.au/s-ad/1224775027
		X-CSRF-TIME: 8b9937095eb1e7cd05dfe56492da1389
		X-CSRF-TOKEN: vbYqYbXcWhvw5Smjuix1IQ
		X-Requested-With: XMLHttpRequest
		Content-Type: multipart/form-data; boundary=---------------------------262153039517889
		Origin: https://www.gumtree.com.au boundary=-----------------------------262153039517889
		Content-Length: 1245
		Connection: keep-alive
		Cookie: machId=olRyFLqGMApEdTPpSlqufX0QJEK1xIMDyoNb19Uf7IIKhNDMq1-Y2soWt2q2PUDtTWlq-jJe-2wUwes_glxD7JXqyor87_akQYkb; up=%7B%22ln%22%3A%22822897446%22%2C%22nps%22%3A%2296%22%2C%22lh%22%3A%22l%3D3008507%26r%3D50%7Cl%3D3008845%26k%3Drural%26r%3D0%26icr%3Dfalse%7Cl%3D3008303%26r%3D0%26icr%3Dfalse%7Cl%3D3008546%26r%3D0%22%2C%22lbh%22%3A%22l%3D3008303%26c%3D20031%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26k%3Dlpg%2520ex%2520taxi%26c%3D18320%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26c%3D20034%26r%3D0%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%26icr%3Dfalse%22%2C%22rva%22%3A%221198763135%2C1223202968%2C1220336329%2C1213928839%2C1222099675%2C1216939616%2C1224605432%2C1224576740%2C1224204012%2C1224186903%2C1094006120%2C1224829493%2C1224775027%22%2C%22lsh%22%3A%22l%3D3008845%26k%3Drural%26c%3D20031%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D3008546%26k%3Dtoyota%2520hiace%26p%3D2%26c%3D9299%26r%3D50%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26k%3Dlpg%2520ex%2520taxi%26c%3D18320%26r%3D0%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D3008546%26c%3D18342%26r%3D50%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26c%3D20034%26r%3D0%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%26icr%3Dfalse%22%2C%22ls%22%3A%22l%3D3008546%26r%3D0%26sv%3DLIST%26sf%3Ddate%22%7D; optimizelyEndUserId=oeu1560869782949r0.17214926398737884; AMCV_50BE5F5858D2477A0A495C7F%40AdobeOrg=2096510701%7CMCIDTS%7C18110%7CMCMID%7C58386376110258854973203384631851669600%7CMCAAMLH-1564849448%7C8%7CMCAAMB-1565346615%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1564748980s%7CNONE%7CvVersion%7C2.0.0%7CMCCIDH%7C-1515736465; cto_lwid=e8637b52-84c2-4b32-972f-4c0322d89998; _ga=GA1.3.1587788353.1560869785; __utma=160852194.1587788353.1560869785.1564650436.1564741815.10; __utmz=160852194.1560869785.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __qca=P0-419801697-1560869785456; _fbp=fb.2.1560869787906.1226741766; ki_t=1560869788532%3B1564721566203%3B1564741832451%3B20%3B145; ki_r=; aam_dfp=aamsegid%3D6797281%2C6880889%2C7087286%2C7220740%2C7329284%2C7329285%2C7329301%2C7329276%2C7333809%2C8331089%2C8331046%2C8269554%2C8444126%2C8448793%2C8465423%2C8978953%2C8458219%2C9501605%2C11996984%2C11996991; aam_tnt=aamsegid%3D6797281%2Caamsegid%3D6880889; aam_uuid=58887149265166189473227778725039339418; __gads=ID=4b4b5f50f8e55f3e:T=1560869785:S=ALNI_Ma4OLGgqRu9xJxlpxqIw8ztXRVNHw; cto_idcpy=6daed043-c530-41df-95e2-4ab3c6941a69; _gcl_au=1.1.1529470621.1561973614; fbm_597906056993680=base_domain=.www.gumtree.com.au; cto_bundle=c89wSV9EQllKcFlTcjlDdzI4S1YlMkI5M1pZWWZqSU1Eb21kQVlnTjY2VDR1UnhNSzAxbldkY2hCMGtIM3YlMkZZTWxHNUpCd3FxZkk0WkhjM2E5M2tKaEslMkIlMkJLZDliYjVqJTJGTEZMUDY1ZXFmWU1iNnYyR3RvdUhnZjBCWmpIZlVrWkNKZU1aJTJGZw; gtj.session=eyJjc3JmU2VjcmV0IjoiMTlkTmNZMnh4eW92V0duTHA0ZW5VUVJ1IiwidWRpZCI6IjgwMWVlMzdiNjkxNWQ0Mzg1Zjg2YmI5ZWFiMDc3MWJlIiwidG9rZW4iOiJjZGFhZTk5NGVlODI3OGRlN2JjMTBkMGQ2ZDFlMjBkNjUzNDM1Zjk2Mzk3OWJjOWY4ZDQxN2Y1Y2NhNmQ1M2E3IiwiZW1haWwiOiJzdGV2ZW5iZXJyeTMwNUBnbWFpbC5jb20iLCJ1c2VySWQiOiI5NTI4OTA5MTU5OTI3In0=; gtj.session.sig=1iSUsJbqqYSbnPAqnrMqKlsnCtU; G_ENABLED_IDPS=google; fbm_1520623457982850=base_domain=; rl_mid=TEJUTOhlQQJ2VktEZzo1SIleKdVVERWWTVkUuplewkT; AMCVS_50BE5F5858D2477A0A495C7F%40AdobeOrg=1; bs=%7B%22st%22%3A%7B%7D%7D; __utmc=160852194; GED_PLAYLIST_ACTIVITY=W3sidSI6InJQdkQiLCJ0c2wiOjE1NjQzMTE2OTUsIm52IjoxLCJ1cHQiOjE1NjQzMTE1OTYsImx0IjoxNTY0MzExNjkyfV0.; _gid=GA1.3.1297796258.1564590467; wl=%7B%22l%22%3A%22%22%7D; __utmb=160852194.1.10.1564741815; __utmt_siteTracker=1
		TE: Trailers
		*/
		String boundary = "---------------------------" + System.currentTimeMillis();
		
		http.setRequestProperty("Host", "www.gumtree.com.au");
		http.setRequestProperty("Accept", "*/*");
		http.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		http.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		http.setRequestProperty("Referer", "https://www.gumtree.com.au/s-ad/1224775027");
		http.setRequestProperty("Origin", "https://www.gumtree.com.au");
		http.setRequestProperty("Connection", "keep-alive");
		http.setRequestProperty("TE", "Trailers");
		
		Map<String,String> arguments = new HashMap<>();
		arguments.put("message", "Hi, I'm interested in the \\\"Telemarketing job\\\" job you've listed on Gumtree. I look forward to hearing from you. Thanks.");
		arguments.put("fromName", "fred"); // This is a fake password obviously
		arguments.put("from", "stevenberry305@gmail.com");
		arguments.put("sendCopyToSender", "false");
		arguments.put("adId", "1224775027");
		arguments.put("marketingConsent", "true");
		arguments.put("sender", "react");
		
		StringJoiner sj = new StringJoiner(" ");
		for(Map.Entry<String,String> entry : arguments.entrySet())
		    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "" 
		         + URLEncoder.encode(entry.getValue(), "UTF-8"));
		byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		
		//
		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		http.setRequestProperty("content-length", String.valueOf(length));
		http.connect();
		try(OutputStream os = http.getOutputStream()) {
		    os.write(out);
		}
		System.out.println(http.getResponseCode());
		System.out.println(http.getResponseMessage());
		System.out.println(sj.toString());
		
		//The URL
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
		
		
		//con.setRequestMethod("POST");
		//con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
		//con.setRequestProperty("Accept-Charset", "UTF-8");
		//con.setRequestProperty("Content-Type", "multipart/form-data");// boundary=---------------------------92542923915479
		//con.setRequestProperty("Content-Length", "1205");
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		//con.setRequestProperty("Accept", "*/*");
		//con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		//con.setRequestProperty("Origin", "https://www.gumtree.com.au");
		//con.setRequestProperty("Connection", "keep-alive");
		//con.setRequestProperty("TE","Trailers");
		//con.setRequestProperty("Referer", URLIn);

		//Form data
		//String urlParameters = data;
		
		// Send post request
		/*con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(out);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + out);
		System.out.println("Response Code : " + responseCode);
		System.out.println(con.getResponseMessage());
		System.out.println(con.getContent());
		System.out.println(con.getErrorStream());
		System.out.println(URLIn);
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
		
		System.out.println(responseCode);*/
		
	}
	
}

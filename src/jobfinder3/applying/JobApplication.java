package applying;
//For the job applicants. They are a human being who can 1. Read the job adds 2. Apply to them

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jobfinder3.DBConnect;
import jobfinder3.JobAdd;
import jobfinder3.WebScrape;

public class JobApplication implements JobApplicationPlan {

	private JobAdd[] JobAdds;
	private String JobTitle, MessageFacebook, MessageIndeed, SQLQuery;
	private String[] MessagesGumtree, MessagesSeek;
	private String[] Words;
	private ResultSet Results;
	
	public JobApplication(String JobTitle){
		
		this.JobTitle = JobTitle;
		
	}
	
	//Produces an array of all applicable job adds.
	@Override
	public void DBExtract(Connection Con) {
		
		try {
			
			Results = DBConnect.Search(Con, SQLQuery);
			
			/*while (Results.next()) {
				
				System.out.println("Title: " + Results.getString("Title") + "\nURL: " + Results.getString("URL") + "\n"); //or rs.getString("column name");
				
			}*/
			
		} catch (SQLException e) {
				
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	/*Apply to a job add. This method needs to break the rule of only doing one thing in order to obtain
	the appropriate security credentials before they expire. What this method does is:
	1) Identify Website
	2) Scrape for credentials
	3) Build HTTP Header
	4) Build HTTP body
	5) Send post request*/
	@Override
	public void Apply() throws IOException {
		
		System.out.println("Job Adds Length: " + JobAdds.length);
		
		int x = 0;
		for (JobAdd JobAdd: JobAdds) {
			
			//If the current Job Add is for Gumtree. Apply using the Gumtree message.
			if (JobAdd.GetWebsite().equals("Gumtree"))
				try {
					{
						
						//To check that the message and add are in sync
						//System.out.println(JobAdd.GetURL() + "\n" + MessagesGumtree[x]);
						
						/*
						//Whats normally on formData
						/*message: Hi, I'm interested in the "Telemarketing job" job you've listed on Gumtree. I look forward to hearing from you. Thanks.
						fromName: Steven Berry
						from: stevenberry305@gmail.com
						sendCopyToSender: false
						adId: 1224775027
						bbUserInput: 
						bbToken: d059c32464d5fa56455664e7d55d397536c5f8d1c75eb54c77db6c97636e987f0ac54eeb8a2c5a6c1217360920e6bf6d1a7988562ffaf79af0304a10cfad44e5
						marketingConsent: false
						sender: react*/
						String rawData = "message: " + MessagesGumtree[x] + " fromName: Steven Berry from: stevenberry305@gmail.com " +
						"sendCopyToSender: true adId: " + JobAdd.GetID() + " marketingConsent: false sender: react bbToken: ";
						String type = "application/x-www-form-urlencoded";
						String encodedData = URLEncoder.encode( rawData, "UTF-8" );
						
						URL URLIn = new URL("https://www.gumtree.com.au/j-contact-seller.html");
						
						WebScrape.POST(URLIn, encodedData, JobAdd.GetURL());
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (JobAdd.GetWebsite().equals("Seek")) {
				
				//To check that the message and add are in sync
				//System.out.println(JobAdd.GetURL() + "\n" + MessagesSeek[x]);
				
				/*
				//Whats normally on formData
				/*message: Hi, I'm interested in the "Telemarketing job" job you've listed on Gumtree. I look forward to hearing from you. Thanks.
				fromName: Steven Berry
				from: stevenberry305@gmail.com
				sendCopyToSender: false
				adId: 1224775027
				bbUserInput: 
				bbToken: d059c32464d5fa56455664e7d55d397536c5f8d1c75eb54c77db6c97636e987f0ac54eeb8a2c5a6c1217360920e6bf6d1a7988562ffaf79af0304a10cfad44e5
				marketingConsent: false
				sender: react*/
				String rawData = "message: " + MessagesSeek[x] + " fromName: Steven Berry from: stevenberry305@gmail.com " +
				"sendCopyToSender: false adId: " + JobAdd.GetID();
				String type = "application/x-www-form-urlencoded";
				String encodedData = URLEncoder.encode( rawData, "UTF-8" );
				
				//WebScrape.POST("encodedData");
				
			}
			
			x++;
			
		}
		
	}
	
	@Override
	public void MarkApplied() {
		// TODO Auto-generated method stub
		
	}
	
	//Setters
	@Override
	public void SetWords(String[] Words) {
		
		this.Words = Words;
		
	}
	
	@Override
	public void SetSQLQuery(String SQLQuery) {
		
		this.SQLQuery = SQLQuery;
		
	}
	
	@Override
	public void SetJobAdds(JobAdd[] JobAdds) {
		
		this.JobAdds = new JobAdd[JobAdds.length];
		
		this.JobAdds = JobAdds;
		
	}
	
	@Override
	public void SetMessagesGumtree(String[] MessagesGumtree) {
			
		this.MessagesGumtree = MessagesGumtree;
			
	}
	
	@Override
	public void SetMessagesSeek(String[] MessagesSeek) {
		
		this.MessagesSeek = MessagesSeek;
		
	}

	@Override
	public void SetMessagesFacebook(String[] MessagesFacebook) {
		
		this.MessageFacebook = MessageFacebook;
		
	}

	@Override
	public void SetMessagesIndeed(String[] MessagesIndeed) {
		
		this.MessageIndeed = MessageIndeed;
		
	}
	
	//Getters
	@Override
	public String GetJobTitle() {
		
		return JobTitle;
		
	}
	
	@Override
	public void GetSQLQuery() {
		
		System.out.println(SQLQuery);
		
	}
	
	@Override
	public ResultSet GetResults() {
		
		return Results;
		
	}

	@Override
	public void GetWords() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JobAdd GetJobAdd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void GetMessagesGumtree() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void GetMessagesSeek() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void GetMessageFacebook() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void GetMessageIndeed() {
		// TODO Auto-generated method stub
		
	}
	
}

//The plan for the JobApplication class. Defines what each JobApplication will have.
package applying;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jobfinder3.JobAdd;

public interface JobApplicationPlan {

	//Setters
	public void SetWords(String[] Words);
	public void SetJobAdds(JobAdd[] JobAdds);
	public void SetMessagesGumtree(String[] MessagesGumtree);
	public void SetMessagesSeek(String[] MessagesSeek);
	public void SetMessagesFacebook(String[] MessagesFacebook);
	public void SetMessagesIndeed(String[] MessagesIndeed);
	public void SetSQLQuery(String SQLQuery);
	
	//Getters
	public String GetJobTitle();
	public void GetWords();
	public JobAdd GetJobAdd();
	public void GetMessagesGumtree();
	public void GetMessagesSeek();
	public void GetMessageFacebook();
	public void GetMessageIndeed();
	public void GetSQLQuery();
	public ResultSet GetResults();
	
	//Methods
	public void DBExtract(Connection Con) throws SQLException;
	public void Apply() throws IOException;
	public void MarkApplied();
	
}
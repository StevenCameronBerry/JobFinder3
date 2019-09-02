package applying;

import java.sql.SQLException;

import jobfinder3.JobAdd;

public interface JobApplicationEngineer {
	
	//Building Methods
	public String[] CombWords(String[] OrigWords);
	public void NearWords();
	public void BuildSQLQuery();
	public String BuildMessage(JobAdd add) throws SQLException;
	
	//Encapsulation
	public JobApplication GetJobApplication();
    public JobApplicationBuilder GetJobApplicationBuilder();
    public void SetBuilder(JobApplicationBuilder builder);
	
}
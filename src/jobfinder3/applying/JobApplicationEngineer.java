package applying;

import java.sql.SQLException;

public interface JobApplicationEngineer {
	
	//Building Methods
	public void CombWords(String[] OrigWords);
	public void NearWords();
	public void BuildSQLQuery();
	public void BuildMessage(JobApplication JobApp) throws SQLException;
	
	//Encapsulation
	public JobApplication GetJobApplication();
    public JobApplicationBuilder GetJobApplicationBuilder();
    public void SetBuilder(JobApplicationBuilder builder);
	
}
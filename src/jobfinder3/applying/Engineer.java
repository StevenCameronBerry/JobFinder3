//This is the engineer for Job Applicants.
package applying;

import java.sql.SQLException;

import jobfinder3.JobAdd;

public class Engineer implements JobApplicationEngineer {
	
	private JobApplicationBuilder JobApplicationBuilder;
	
	public Engineer(JobApplicationBuilder JobApplicationBuilder) {
		
		this.JobApplicationBuilder = JobApplicationBuilder;
		
	}
	
	@Override
	public void SetBuilder(JobApplicationBuilder builder) {
		
		this.JobApplicationBuilder = builder;
		
	}
	
	@Override
	public JobApplicationBuilder GetJobApplicationBuilder() {
		
		return JobApplicationBuilder;
        
	}
	
	@Override
	public JobApplication GetJobApplication() {

		JobApplication JobApplication = JobApplicationBuilder.GetJobApplication();
        return JobApplication;
		
	}
	
	//All the methods that follow are the directions for constructing the JobApplication object
	@Override
	public void CombWords(String[] OrigWords) {
		
		JobApplicationBuilder.SetOrigWords(OrigWords);
		JobApplicationBuilder.IdentifySpaceSize();
		JobApplicationBuilder.PossibleCombin();
		JobApplicationBuilder.IdentifySpacePos();
		JobApplicationBuilder.InitSpacesToRep();
		JobApplicationBuilder.SpaceRep();
		JobApplicationBuilder.RawComboList();
		JobApplicationBuilder.ComboWords();
		JobApplicationBuilder.ComboWordsArr();
		JobApplicationBuilder.BuildComboWords();

	}
	
	//https://gordonlesti.com/fuzzy-fulltext-search-with-mysql/\\
	//Now you need to account for spelling mistakes
	@Override
	public void NearWords() {
		
		
		
	}
	
	//For Making the SQL Query
	@Override
	public void BuildSQLQuery() {
		
		JobApplicationBuilder.CreateSQLQuery();
		JobApplicationBuilder.BuildSQLQuery();
		
	}

	@Override
	public void BuildMessage(JobApplication JobApp) throws SQLException {
		
		JobApplicationBuilder.SetJobApplication(JobApp);
		JobApplicationBuilder.SetResults();
		JobApplicationBuilder.ProcessVarsInit();
		JobApplicationBuilder.ProcessVars();
		JobApplicationBuilder.ProcessDistance();
		JobApplicationBuilder.ProcessAge();
		JobApplicationBuilder.BuildJobAdds();
		JobApplicationBuilder.BuildMessagesGumtree();
		JobApplicationBuilder.BuildMessagesSeek();

	}
	
}
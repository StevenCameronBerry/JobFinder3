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
	public String[] CombWords(String[] OrigWords) {
		
		JobApplicationBuilder.SetOrigWords(OrigWords);
		JobApplicationBuilder.IdentifySpaceSize();
		JobApplicationBuilder.PossibleCombin();
		JobApplicationBuilder.IdentifySpacePos();
		JobApplicationBuilder.InitSpacesToRep();
		JobApplicationBuilder.SpaceRep();
		JobApplicationBuilder.RawComboList();
		JobApplicationBuilder.ComboWords();
		JobApplicationBuilder.ComboWordsArr();
		String Words[] = JobApplicationBuilder.BuildComboWords();
		
		return Words;

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
	public String BuildMessage(JobAdd JobAdd) throws SQLException {
		
		JobApplicationBuilder.SetJobAdd(JobAdd);
		JobApplicationBuilder.ProcessVars();
		String Message = JobApplicationBuilder.ProcessDistance();
		
		System.out.println(Message);
		
		return Message;

	}
	
}
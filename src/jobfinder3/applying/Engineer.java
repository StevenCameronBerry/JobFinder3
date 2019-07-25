//This is the engineer for Job Applicants.
package applying;

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

	}
	
	//https://gordonlesti.com/fuzzy-fulltext-search-with-mysql/\\
	//Now you need to account for spelling mistakes
	@Override
	public void NearWords() {
		
		
		
	}

	@Override
	public void BuildMessage() {
		
		JobApplicationBuilder.ProcessVars();
		JobApplicationBuilder.ProcessAge();

	}
	
}
package applying;

public interface JobApplicationEngineer {
	
	//Building Methods
	public void CombWords(String[] OrigWords);
	public void NearWords();
	public void BuildMessage();
	
	//Encapsulation
	public JobApplication GetJobApplication();
    public JobApplicationBuilder GetJobApplicationBuilder();
    public void SetBuilder(JobApplicationBuilder builder);
	
}
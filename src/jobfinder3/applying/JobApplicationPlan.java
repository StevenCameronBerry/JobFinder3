//The plan for the JobApplication class. Defines what each JobApplication will have.
package applying;

import jobfinder3.JobAdd;

public interface JobApplicationPlan {

	//Setters
	public void SetWords(String[] Words);
	public void SetJobAdd();
	public void SetMessageGumtree(String MessageGumtree);
	public void SetMessageSeek(String MessageSeek);
	public void SetMessageFacebook(String MessageFacebook);
	public void SetMessageIndeed(String MessageIndeed);
	
	//Getters
	public void GetJobTitle();
	public void GetWords();
	public JobAdd GetJobAdd();
	public void GetMessageGumtree();
	public void GetMessageSeek();
	public void GetMessageFacebook();
	public void GetMessageIndeed();
	
	//Methods
	public void DBExtract();
	public void Apply();
	public void MarkApplied();
	
}
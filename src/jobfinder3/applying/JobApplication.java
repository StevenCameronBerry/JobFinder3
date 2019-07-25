package applying;
//For the job applicants. They are a human being who can 1. Read the job adds 2. Apply to them

import jobfinder3.JobAdd;

public class JobApplication implements JobApplicationPlan {

	private JobAdd Job;
	private String JobTitle, MessageGumtree, MessageSeek, MessageFacebook, MessageIndeed;
	private String[] Words;
	
	public JobApplication(String JobTitle){
		
		this.JobTitle = JobTitle;
		
	}
	
	//Produces an array of all applicable job adds.
	@Override
	public void DBExtract() {
		
		
		
	}
	
	@Override
	public void Apply() {
		
		
		
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
	public void SetJobAdd() {
		
		
		
	}
	
	@Override
	public void SetMessageGumtree(String MessageGumtree) {
			
		this.MessageGumtree = MessageGumtree;
			
	}
	
	@Override
	public void SetMessageSeek(String MessageSeek) {
		
		this.MessageSeek = MessageSeek;
		
	}

	@Override
	public void SetMessageFacebook(String MessageFacebook) {
		
		this.MessageFacebook = MessageFacebook;
		
	}

	@Override
	public void SetMessageIndeed(String MessageIndeed) {
		
		this.MessageIndeed = MessageIndeed;
		
	}
	
	//Getters
	@Override
	public void GetJobTitle() {
		
		System.out.println(JobTitle);
		
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
	public void GetMessageGumtree() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void GetMessageSeek() {
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

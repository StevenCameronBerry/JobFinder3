/* The JobApplicationBuilder Interface. Defines what every Job Application must have.*/
package applying;

public interface JobApplicationBuilder {
	
	//Setters
	public void SetOrigWords(String[] OrigWords);
	
	//For Expanding the words
	public void IdentifySpaceSize();
	public void PossibleCombin();
	public void IdentifySpacePos();
	public void InitSpacesToRep();
	public void SpaceRep();
	
	//For Accounting for spelling mistakes
	public void GenerateInsertations();
	public void GenerateDeletations();
	public void GenerateSubstitutions();
	public void RemoveDifMean();
	
	//For Formatting the message
	public void ProcessVars();
	public void ProcessAge();

	public JobApplication GetJobApplication();

}

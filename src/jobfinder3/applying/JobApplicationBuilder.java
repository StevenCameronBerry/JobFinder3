/* The JobApplicationBuilder Interface. Defines what every Job Application must have.*/
package applying;

import java.sql.SQLException;

public interface JobApplicationBuilder {
	
	//Setters
	public void SetOrigWords(String[] OrigWords);
	public void SetJobApplication(JobApplication JobApp);
	public void SetResults();
	
	//For Expanding the words
	public void IdentifySpaceSize();
	public void PossibleCombin();
	public void IdentifySpacePos();
	public void InitSpacesToRep();
	public void SpaceRep();
	public void RawComboList();
	public void ComboWords();
	public void ComboWordsArr();
	public void BuildComboWords();
	
	//For Accounting for spelling mistakes
	public void GenerateInsertations();
	public void GenerateDeletations();
	public void GenerateSubstitutions();
	public void RemoveDifMean();
	
	//For Making the DB Query
	public void CreateSQLQuery();
	public void BuildSQLQuery();
	
	//For Formatting the message
	public void ProcessVarsInit() throws SQLException;
	public void ProcessVars() throws SQLException;
	public void ProcessDistance();
	public void ProcessAge();
	public void BuildJobAdds();
	public void BuildMessagesGumtree();
	public void BuildMessagesSeek();

	public JobApplication GetJobApplication();
	

}

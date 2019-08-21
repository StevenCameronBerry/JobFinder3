//The builder for the Job Application Object
package applying;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jobfinder3.JobAdd;

public class Builder implements JobApplicationBuilder {
	
	private int SpaceSize, SpaceIdx, MaxSpaceSize, NumberCombins, CombinPos = 0, OrigWordCounter = 0, z = 0;
	private int[] SpaceSizes, Combination;
	private int[][] SpacePos, Combinations;
	private String[] OrigWords, RawComboWords, ComboWords, CoverLettersGumtree, CoverLettersSeek;
	private JobApplication JobApplication;
	private HashMap<String, int[]> SpacesToRep = new HashMap<String, int[]>();
	private String NewKey, SQLQuery, JobTitle;
	private boolean DeldChar;
	private List<String> RawList;
	private Set<String> ComobWordsSet;
	private ResultSet Results;
	private JobAdd[] JobAdds;
	
	public Builder(String JobTitle){
	
		JobApplication = new JobApplication(JobTitle);
		
	}
	
	//Setters
	@Override
	public void SetOrigWords(String[] OrigWords) {
		
		this.OrigWords = OrigWords;
		
	}
	
	@Override
	public void SetJobApplication(JobApplication JobApp) {
		
		this.JobApplication = JobApp;
		
	}
	
	@Override
	public void SetResults() {
		
		this.Results = JobApplication.GetResults();
		
	}
	
	/*Build the Word List*/
	/*For each of the original words find the size of the number of spaces and return it as an array (SpaceSizes) so that 
	we can find the space positions later on*/
	@Override
	public void IdentifySpaceSize() {
		
		//Let SpaceSizes size be the number of OrigWords
		SpaceSizes = new int[OrigWords.length];
		
		int x = 0;
		for(String OrigWord: OrigWords) {
		
			SpaceIdx = OrigWord.indexOf(" ");
	    	SpaceSize = 0;
	    	
	    	while (SpaceIdx >= 0) {
	    		
	    		SpaceIdx = OrigWord.indexOf(" ", SpaceIdx + 1);
	    		SpaceSize++;
	    	    
	    	}
	    	
	    	SpaceSizes[x] = SpaceSize;
	    	
	    	x++;
		    	
		}
		
		//Find the largest of SpaceSizes
    	MaxSpaceSize = Arrays.stream(SpaceSizes).max().getAsInt();

	}
	
	/*Find all possible combinations of spaces to be replaced
	 * Output: 3D Array of OrigWods as x and Space Positions as y and wether or not to replace the space as z
	*/
	@Override
	public void PossibleCombin() {
		
		//Intialalize Variables. NOTE: JAVA IS y AXIS FIRST THAN x AXIS WITH 2D ARRAYS
		NumberCombins = (int) Math.pow(2, MaxSpaceSize);
		Combinations = new int[NumberCombins][MaxSpaceSize];
		
        for (int i = 0; i < Math.pow(2, MaxSpaceSize); i++) {
        	
            String bin = Integer.toBinaryString(i);
            
            while (bin.length() < MaxSpaceSize) {
            	
                bin = "0" + bin;
            }
            
            char[] chars = bin.toCharArray();
            
            for (int j = 0; j < chars.length; j++) {
            	
            	Combinations[i][j] = chars[j] == '0' ? 1 : 0;
            	
            }
            
        }

	}
	
	/*Identify Space position
	 * Output: 2D Array of OrigWods as x and Space Positions as y
	*/
	@Override
	public void IdentifySpacePos() {
		
		//Let SpacePositions size be the number of OrigWords x and the Maximum Space Size identified for this enum y
		SpacePos = new int[OrigWords.length][MaxSpaceSize];
		//Popularize it all with -1
		for (int[] row: SpacePos) {
			
			Arrays.fill(row, -1);
			
		}
		
		int x = 0;
		for(String OrigWord: OrigWords) {
		
			SpaceIdx = OrigWord.indexOf(" ");
	    	SpaceSize = 0;
	    	//System.out.println(OrigWord);
	    	//System.out.println(OrigWord.indexOf(" "));
	    	
	    	int y = 0;
	    	while (SpaceIdx >= 0) {
	    		
	    		SpacePos[x][y] = SpaceIdx;
	    		SpaceIdx = OrigWord.indexOf(" ", SpaceIdx + 1);
	    		
	    		//Mark any zeros as -1 to clearly identify there was no space
	    		if(SpacePos[x][y] == 0) {
	    			
	    			SpacePos[x][y] = -1;
	    			
	    		}
	    		
	    		y++;
	    		
	    	}
	    	
	    	x++;
		    	
		}
		
		//System.out.println("(7,0)\n"+SpacePos[7][0]+"\n(7,2)\n");
		//System.out.println(SpacePos[7][2]);

	}
	
	/*The HashMap SpacesToRep for SpaceRep
	Had to be done like this because of how hashmaps are populated*/
	@Override
	public void InitSpacesToRep() {
		
		RawComboWords = new String[NumberCombins*OrigWords.length];
	  
	}

	//RECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSION
	//Replaces the spaces appropriatley in each enum
	@Override
	public void SpaceRep() {
		
		//The new method for SpaceRep
		//Go down through combinations
		for (int y = 0; y < NumberCombins; y++) {
			
			//Leave once were at the last
			if (OrigWordCounter == OrigWords.length) {
			
				return;

			}
			
			NewKey = OrigWords[OrigWordCounter];
			
			//Left to right for spaces index and boolean positions
			for (int x = 0; x < MaxSpaceSize; x++) {
				
				//To show the original word
				/*
				 * System.out.println("The Original Word:");
				 * System.out.println(OrigWords[OrigWordCounter]);
				 * 
				 * //To check that the combinations are correct System.out.println("x: " + x +
				 * " y: " + y); System.out.println(Combinations[y][x]); //To check where the
				 * space position was System.out.println(SpacePos[OrigWordCounter][x]);
				 */
				
				
				if (Combinations[y][x] == 1 && SpacePos[OrigWordCounter][x] > 0 && DeldChar == false) {
					
					//Replace the space at the appropriate index
					NewKey = OrigWords[OrigWordCounter].substring(0, SpacePos[OrigWordCounter][x]) + "#" +
							OrigWords[OrigWordCounter].substring(SpacePos[OrigWordCounter][x] + 1);
					
					DeldChar = true;
					
				} else if (Combinations[y][x] == 1 && SpacePos[OrigWordCounter][x] > 0 && DeldChar == true) {
					
					//Replace the space at the appropriate index
					NewKey = NewKey.substring(0, SpacePos[OrigWordCounter][x]) + "#" +
							NewKey.substring(SpacePos[OrigWordCounter][x] + 1);
					
					//DeldChar = false;
					
				}
				
				//Output the result
				//System.out.println("The word:");
				//System.out.println(NewKey +"\n");
				
				//Recursion for going through to the next word
				if (x == MaxSpaceSize - 1) {
					
					//Replace all the "#"s with nothing
					NewKey = NewKey.replace("#", "");
					RawComboWords[z] = NewKey;
					/*
					//To show the original word
					System.out.println("The Original Word:");
					System.out.println(OrigWords[OrigWordCounter]);
					
					//To check that the combinations are correct
					System.out.println("x: " + x + " y: " + y);
					System.out.println(Arrays.toString(Combinations[y]));
					//To check where the space position was
					System.out.println(Arrays.toString(SpacePos[OrigWordCounter]));
					//Output the result
					System.out.println("The word:");
					System.out.println(NewKey +"\n");
					System.out.println("The RawComboWords:");
					System.out.println(RawComboWords[z] +"\n");
					*/
					
					z++;
				}
				
				if (x == MaxSpaceSize - 1 && y == NumberCombins - 1) {
					
					OrigWordCounter = OrigWordCounter + 1;
					
					SpaceRep();
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void RawComboList() {
		
		/*
		for(String Raw : RawComboWords) {
			
			System.out.println(Raw);
			
		}
		*/
		
		RawList = Arrays.asList(RawComboWords);
		
		//System.out.println(RawList);

	}
	
	@Override
	public void ComboWords() {
		
		ComobWordsSet = new LinkedHashSet<>(RawList);
		
		ComobWordsSet.remove(null);

	}
	
	@Override
	public void ComboWordsArr() {
		
		ComboWords = new String[ComobWordsSet.size()];
		ComboWords = ComobWordsSet.toArray(ComboWords);

	}
	
	@Override
	public void BuildComboWords() {
		
		JobApplication.SetWords(ComboWords);

	}
	
	//For making the SQL Query
	@Override
	public void CreateSQLQuery() {
		
		SQLQuery = "SELECT * FROM jobfinder3.jf3 WHERE `Title+Desc` LIKE '%" + ComboWords[0] + "%'";
		
		for (String Word: ComboWords) {
			
			  SQLQuery += " OR `Title+Desc` LIKE '%" + Word + "%'";
			
		}
		
		//Show the SQL query.
		System.out.println(SQLQuery);

	}
	
	@Override
	public void BuildSQLQuery() {
		
		JobApplication.SetSQLQuery(SQLQuery);

	}
	
	//Initialize Objects and variables for assigning the variables for the messages.
	@Override
	public void ProcessVarsInit() throws SQLException {
		
		//Find the size of the Result Set
		int size = 0;
		
		if (Results != null) {
			
			Results.last();    // moves cursor to the last row
			size = Results.getRow(); // get row id
		  
		}
		
		System.out.println(size);
		
		CoverLettersGumtree = new String[size];
		CoverLettersSeek = new String[size];
		JobAdds = new JobAdd[size];
		
		for(JobAdd Job: JobAdds) {Job = new JobAdd();}
		
		System.out.println(JobAdds.length);
		
		//Initialize the JobAdd Objects inside Job Application
		int x = 0;
		Results.beforeFirst();
		while (Results.next()) {
			
			JobAdds[x] = new JobAdd();
			JobAdds[x].SetID(Results.getString("ID"));
			JobAdds[x].SetTitle(Results.getString("Title"));
			JobAdds[x].SetLocation(Results.getString("Location"));
			JobAdds[x].SetDistance_km(Integer.parseInt(Results.getString("Distance_km")));
			JobAdds[x].SetAge(Results.getString("Age"));
			JobAdds[x].SetWebsite(Results.getString("Website"));
			JobAdds[x].SetURL(Results.getString("URL"));
			JobAdds[x].SetSalaryType(Results.getString("SalaryType"));
			JobAdds[x].SetJobType(Results.getString("JobType"));
			JobAdds[x].SetDescription(Results.getString("Description"));
			JobAdds[x].SetCompanyName(Results.getString("CompanyName"));
			JobAdds[x].SetTitleDesc(Results.getString("Title+Desc"));
			JobAdds[x].SetAdvertiserName(Results.getString("AdvertiserName"));
			
			x++;
			
		}
		
		//Put those Job Adds into the Job Application object
		

	}
	
	@Override
	public void ProcessVars() throws SQLException {
		
		//Process the variables for both
		for (int x = 0; x < JobAdds.length; x++) {
			
			CoverLettersGumtree[x] = "Dear " + JobAdds[x].GetAdvertiserName() + ", I am Steven. I am a " + JobApplication.GetJobTitle() + " with two and a half years recent industry experience. I would like to apply for the " + JobAdds[x].GetJobType() + " “" + JobAdds[x].GetTitle() + "” position you listed on " + JobAdds[x].GetWebsite() + ". I am seeking a long-term part-time or casual position for the next four years while I complete my undergraduate studies in Instrumentation and Control Systems Engineering, followed by my masters degree in Electrical Engineering. I need this position to pay for rent, food and other necessary expenses. I possess excellent customer service skills, a very fast, efficient working pace, a positive attitude and most importantly I have the desire to become even better, faster, more customer friendly and to learn as much and as many new skills as possible. I live just _Distance_km_ from your workplace in " + JobAdds[x].GetLocation() + ". I have completed the following hospitality training courses:\r\n" + 
					" \r\n" + 
					"•         West Australian Responsible Service of Alcohol (RSA)\r\n" + 
					"•         Barista Training\r\n" + 
					"•         SITXFSA101 – Use Hygienic Practices for Food Safety\r\n" + 
					" \r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        Bright Bubbly Personality\r\n" + 
					"•        Ability to carry three plates\r\n" + 
					"•        Own reliable transport and driver’s licence with no demerit points lost (Toyota Camry, well serviced)\r\n" + 
					"•        Full Australian Citizenship\r\n" + 
					"•        Ability to speak perfect English\r\n" + 
					"•        Completely drug and alcohol free\r\n" + 
					"•        Full day availability on Friday, Saturday and Sunday\r\n" + 
					"•        Availability on most nights, which can be discussed upon interview.\r\n" + 
					" \r\n" + 
					"I can be contacted on 0417 180 131 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
			CoverLettersSeek[x] = "Hello, I am Steven. I am a " + JobApplication.GetJobTitle() + " with two and a half years recent industry experience. I would like to apply for the “" + JobAdds[x].GetTitle() + "” position at " + JobAdds[x].GetCompanyName() + " you listed on " + JobAdds[x].GetWebsite() + ". I am seeking a long-term part-time or casual position for the next four years while I complete my undergraduate studies in Instrumentation and Control Systems Engineering, followed by my masters degree in Electrical Engineering. I need this position to pay for rent, food and other necessary expenses. I possess excellent customer service skills, a very fast, efficient working pace, a positive attitude and most importantly I have the desire to become even better, faster, more customer friendly and to learn as much and as many new skills as possible. I live just _Distance_km_ from your workplace in " + JobAdds[x].GetLocation() + ". I have completed the following hospitality training courses:\r\n" + 
					" \r\n" + 
					"•         West Australian Responsible Service of Alcohol (RSA)\r\n" + 
					"•         Barista Training\r\n" + 
					"•         SITXFSA101 – Use Hygienic Practices for Food Safety\r\n" + 
					" \r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        Bright Bubbly Personality\r\n" + 
					"•        Ability to carry three plates\r\n" + 
					"•        Own reliable transport and driver’s licence with no demerit points lost (Toyota Camry, well serviced)\r\n" + 
					"•        Full Australian Citizenship\r\n" + 
					"•        Ability to speak perfect English\r\n" + 
					"•        Completely drug and alcohol free\r\n" + 
					"•        Full day availability on Friday, Saturday and Sunday\r\n" + 
					"•        Availability on most nights, which can be discussed upon interview.\r\n" + 
					" \r\n" + 
					"I can be contacted on 0417 180 131 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
			//System.out.println("Job Add:\n" + JobAdds[x].GetTitle() + " || " + JobAdds[x].GetURL() + "\nJob Cover Letter:\n" + CoverLettersGumtree[x]);
			
		}
		
		//System.out.println("Job Add:\n" + JobAdds[1].GetTitle() + " || " + JobAdds[1].GetURL() + "\nJob Cover Letter:\n" + CoverLettersGumtree[1]);

	}

	@Override
	public void ProcessDistance() {
		
		for (int x = 0; x < JobAdds.length; x++) {
			
			if (Integer.parseInt(JobAdds[x].GetDistance_km()) < 2) {
				
				CoverLettersGumtree[x] = CoverLettersGumtree[x].replace(" I live just _Distance_km_ from ", " I live just under a kilometre away from ");
				CoverLettersSeek[x] = CoverLettersSeek[x].replace(" I live just _Distance_km_ from ", " I live just under a kilometre away from ");
				
			} else if (Integer.parseInt(JobAdds[x].GetDistance_km()) < 11) {
				
				CoverLettersGumtree[x] = CoverLettersGumtree[x].replace(" I live just _Distance_km_ from ", " I live just " + JobAdds[x].GetDistance_km() + " kilometres away from ");
				CoverLettersSeek[x] = CoverLettersSeek[x].replace(" I live just _Distance_km_ from ", " I live just " + JobAdds[x].GetDistance_km() + " kilometres away from ");
				
			} else {
				
				CoverLettersGumtree[x] = CoverLettersGumtree[x].replace(" I live just _Distance_km_ from ", " I have no problem getting to ");
				CoverLettersSeek[x] = CoverLettersSeek[x].replace(" I live just _Distance_km_ from ", " I have no problem getting to ");
				
			}
			
		}
		
		//To show what Add and URL it was
		//System.out.println("Job Add:\n" + JobAdds[0].GetTitle() + " || " + JobAdds[0].GetURL() + "\nJob Cover Letter:\n" + CoverLettersGumtree[0]);

	}
	
	@Override
	public void ProcessAge() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void BuildJobAdds() {
		
		JobApplication.SetJobAdds(JobAdds);

	}
	
	@Override
	public void BuildMessagesGumtree() {
		
		JobApplication.SetMessagesGumtree(CoverLettersGumtree);

	}
	
	@Override
	public void BuildMessagesSeek() {
		
		JobApplication.SetMessagesSeek(CoverLettersSeek);

	}
	
	@Override
	public void GenerateInsertations() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void GenerateDeletations() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void GenerateSubstitutions() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void RemoveDifMean() {
		// TODO Auto-generated method stub

	}

	@Override
	public applying.JobApplication GetJobApplication() {

		return JobApplication;
		
	}

}

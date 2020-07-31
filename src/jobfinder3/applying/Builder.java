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
	private String[] OrigWords, RawComboWords, ComboWords;
	private JobApplication JobApplication;
	private HashMap<String, int[]> SpacesToRep = new HashMap<String, int[]>();
	private String NewKey, SQLQuery, JobTitle, CoverLetter;
	private boolean DeldChar;
	private List<String> RawList;
	private Set<String> ComobWordsSet;
	private ResultSet Results;
	private JobAdd[] JobAdds;
	private JobAdd JobAdd;
	
	public Builder(String JobTitle){
	
		JobApplication = new JobApplication(JobTitle);
		
	}
	
	//Setters
	@Override
	public void SetOrigWords(String[] OrigWords) {
		
		this.OrigWords = OrigWords;
		
	}
	
	@Override
	public void SetJobApplication(JobAdd JobAdd) {
		
		this.JobAdd = JobAdd;
		
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
	public String[] BuildComboWords() {
		
		JobApplication.SetWords(ComboWords);
		
		return ComboWords;

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
	
	//Fill in the variables
	@Override
	public void ProcessVars() throws SQLException {

		//do for each type of job
		if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Kitchen Hand")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am a " + JobAdd.GetKeyWord() + " with over two and a half years recent industry experience. I would like to apply for the " + JobAdd.GetJobType() + " “" + JobAdd.GetTitle() + "” position you listed on " + JobAdd.GetWebsite() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I possess a very fast, efficient working pace, a positive attitude and most importantly I have the desire to become even better, faster, more efficient and to learn as much and as many new skills as possible. I understand that no two kitchens are the same and I have many questions about your system and the first thing I plan on doing is learning the fridges well. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + ".\n I have completed the following hospitality training courses:\r\n" + 
					" \r\n" + 
					"•         AHA Hospitality & Hygiene COVID-19 Hygiene Course\r\n" + 
					"•         West Australian Responsible Service of Alcohol (RSA)\r\n" + 
					"•         Barista Training\r\n" + 
					"•         SITXFSA101 – Use Hygienic Practices for Food Safety\r\n" + 
					" \r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        High organizational skills\r\n" +
					"•        Own Reliable Vehicle\r\n" +
					"•        Full Australian Citizenship\r\n" + 
					"•        Ability to speak perfect English\r\n" + 
					" \r\n" + 
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Glassy")) { 
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am a Hospitality Professional with over two years and a half years recent industry experience. I would like to apply for the " + JobAdd.GetJobType() + " “" + JobAdd.GetTitle() + "” position you listed on " + JobAdd.GetWebsite() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I possess excellent customer service skills, a very fast, efficient working pace, a positive attitude and most importantly I have the desire to become even better, faster, more customer friendly and to learn as much and as many new skills as possible. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + ". I have completed the following hospitality training courses:\r\n" + 
					" \r\n" + 
					"•         AHA Hospitality & Hygiene COVID-19 Hygiene Course\r\n" + 
					"•         West Australian Responsible Service of Alcohol (RSA)\r\n" + 
					"•         Barista Training\r\n" + 
					"•         SITXFSA101 – Use Hygienic Practices for Food Safety\r\n" + 
					" \r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        Bright bubbly personality\r\n" + 
					"•        High organizational skills\r\n" + 
					"•        Own Reliable Vehicle\r\n" +
					"•        Full Australian Citizenship\r\n" + 
					"•        Ability to speak perfect English\r\n" + 
					" \r\n" + 
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Bar Man")) { 
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am a Hospitality Professional with over two years and a half years recent industry experience. I would like to apply for the " + JobAdd.GetJobType() + " “" + JobAdd.GetTitle() + "” position you listed on " + JobAdd.GetWebsite() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I possess excellent customer service skills, a very fast, efficient working pace, a positive attitude and most importantly I have the desire to become even better, faster, more customer friendly and to learn as much and as many new skills as possible. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + ". I have completed the following hospitality training courses:\r\n" + 
					" \r\n" + 
					"•         AHA Hospitality & Hygiene COVID-19 Hygiene Course\r\n" + 
					"•         West Australian Responsible Service of Alcohol (RSA)\r\n" + 
					"•         Barista Training\r\n" + 
					"•         SITXFSA101 – Use Hygienic Practices for Food Safety\r\n" + 
					" \r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        Bright bubbly personality\r\n" + 
					"•        High organizational skills\r\n" + 
					"•        Own Reliable Vehicle\r\n" +
					"•        Full Australian Citizenship\r\n" + 
					"•        Ability to speak perfect English\r\n" + 
					" \r\n" + 
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Waiter")) {
		
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am a Hospitality Professional with over two years and a half years recent industry experience. I would like to apply for the " + JobAdd.GetJobType() + " “" + JobAdd.GetTitle() + "” position you listed on " + JobAdd.GetWebsite() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I possess excellent customer service skills, a very fast, efficient working pace, a positive attitude and most importantly I have the desire to become even better, faster, more customer friendly and to learn as much and as many new skills as possible. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + ". I have completed the following hospitality training courses:\r\n" + 
					" \r\n" + 
					"•         AHA Hospitality & Hygiene COVID-19 Hygiene Course\r\n" + 
					"•         West Australian Responsible Service of Alcohol (RSA)\r\n" + 
					"•         Barista Training\r\n" + 
					"•         SITXFSA101 – Use Hygienic Practices for Food Safety\r\n" + 
					" \r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        Bright bubbly personality\r\n" + 
					"•        High organizational skills\r\n" + 
					"•        Own Reliable Vehicle\r\n" +
					"•        Full Australian Citizenship\r\n" + 
					"•        Ability to speak perfect English\r\n" + 
					" \r\n" + 
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
	
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Labourer")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am a " + JobAdd.GetKeyWord() + " with one years industry experience. I would like to apply for the " + JobAdd.GetJobType() + " “" + JobAdd.GetTitle() + "” position you listed on " + JobAdd.GetWebsite() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I have construction experience ranging from residential buildings to large Civil projects. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + ". I have completed the following construction training courses:\r\n" + 
					" \r\n" + 
					"•         Construction White Card\n" + 
					"•         Working in Confined Spaces\r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        ABN Number\r\n" + 
					"•        Own reliable vehicle\r\n" +
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
		
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Administration Clerk")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am available full time untill the 27th of July and part-time thereafter. I am hard working and possess a positive attitude. I am customer focused and have a high attention to detail. I have one year and four months experience in a corporate setting as an IT Manager / Software Developer. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".\r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Trolley Boy")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Super Market Professional")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I am customer focused and have a high attention to detail. I have one years experience in a supermarket as a nightfiller. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";

		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Driver")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I possess a C-A class drivers license. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";

		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Retail Professional")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I am customer focused and have a high attention to detail. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Call Centre")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I am customer focused and have a high attention to detail. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";

		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Dog Walker")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I live just _Distance_km_ from you in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";

		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Mathematics Tutor")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am an Instrumentation and Control Systems Engineering Honours student at Murdoch University. I have also created Business Intelligence Reports for some of Western Australia’s largest infrastructure projects. I am keen on providing your tutoring needs. I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I live just _Distance_km_ from you in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Physics Tutor")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am an Instrumentation and Control Systems Engineering Honours student at Murdoch University. I have also created Business Intelligence Reports for some of Western Australia’s largest infrastructure projects. I am keen on providing your tutoring needs. I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I live just _Distance_km_ from you in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";

		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("IT Professional")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am a " + JobAdd.GetKeyWord() + " with one year and four months industry experience. I would like to apply for the " + JobAdd.GetJobType() + " “" + JobAdd.GetTitle() + "” position you listed on " + JobAdd.GetWebsite() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I am customer focused with a strong attention to detail. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + 
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\nThis was an automatic application written by a bot programmed in Java using a MySQL database and the builder design pattern." + 
					"";

		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Software Developer")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am a " + JobAdd.GetKeyWord() + " with one year and four months industry experience. I would like to apply for the " + JobAdd.GetJobType() + " “" + JobAdd.GetTitle() + "” position you listed on " + JobAdd.GetWebsite() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I have experience in Java, Business Intelligence, Python and some experience in SQL. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + 
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\nThis was an automatic application written by a bot programmed in Java using a MySQL database and the builder design pattern." + 
					"";

		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Cleaner")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I have over two and half years experience as a porter in commercial kitchens. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else if (JobAdd.GetWebsite().equals("Gumtree") && JobAdd.GetKeyWord().equals("Logistics Professional")) {
			
			CoverLetter = "Dear " + JobAdd.GetAdvertiserName() + ", I am Steven Berry. I am interested in applying for the position you advertised for a " + JobAdd.GetJobType() + " " + JobAdd.GetKeyWord() + ". I am looking for a part-time or casual job that works around my Control Systems Engineering studies. I have a strong preferance for weekend hours. I am hard working and possess a positive attitude. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() +
					".r\n" +
					"\r\nI can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		} else {
			
			CoverLetter = "Hello, I am Aaron. I am a " + JobAdd.GetKeyWord() + " with two years recent industry experience. I would like to apply for the “" + JobAdd.GetTitle() + "” position at " + JobAdd.GetCompanyName() + " you listed on " + JobAdd.GetWebsite() + ". I am available on nights and weekends. I possess excellent customer service skills, a very fast, efficient working pace, a positive attitude and most importantly I have the desire to become even better, faster, more customer friendly and to learn as much and as many new skills as possible. I live just _Distance_km_ from your workplace in " + JobAdd.GetLocation() + ". I have completed the following hospitality training courses:\r\n" + 
					" \r\n" + 
					"•         West Australian Responsible Service of Alcohol (RSA)\r\n" + 
					"•         Barista Training\r\n" + 
					"•         SITXFSA101 – Use Hygienic Practices for Food Safety\r\n" + 
					" \r\n" + 
					"I also possess the following desirable attributes:\r\n" + 
					" \r\n" + 
					"•        High organizational skills\r\n" + 
					"•        Full Australian Citizenship\r\n" + 
					"•        Ability to speak perfect English\r\n" +
					" \r\n" + 
					"I can be contacted on 0481 941 601 or at stevenberry305@gmail.com if interested, thank you for this opportunity.\r\n" + 
					"\r\n" + 
					"";
			
		}
		
		System.out.println(CoverLetter);
			
	}

	@Override
	public String ProcessDistance() {
			
			if (Integer.parseInt(JobAdd.GetDistance_km()) < 2) {
				
				CoverLetter = CoverLetter.replace(" I live just _Distance_km_ from ", " I live just under a kilometre away from ");
				//CoverLettersSeek[x] = CoverLettersSeek[x].replace(" I live just _Distance_km_ from ", " I live just under a kilometre away from ");
				
				
			} else if (Integer.parseInt(JobAdd.GetDistance_km()) < 11) {
				
				CoverLetter = CoverLetter.replace(" I live just _Distance_km_ from ", " I live just " + JobAdd.GetDistance_km() + " kilometres away from ");
				//CoverLettersSeek[x] = CoverLettersSeek[x].replace(" I live just _Distance_km_ from ", " I live just " + JobAdds[x].GetDistance_km() + " kilometres away from ");
				
				
			} else {
				
				CoverLetter = CoverLetter.replace(" I live just _Distance_km_ from ", " I have no problem getting to ");
				//CoverLettersSeek[x] = CoverLettersSeek[x].replace(" I live just _Distance_km_ from ", " I have no problem getting to ");
				
			}
		
		return CoverLetter;
	}
		//To show what Add and URL it was
		//System.out.println("Job Add:\n" + JobAdds[0].GetTitle() + " || " + JobAdds[0].GetURL() + "\nJob Cover Letter:\n" + CoverLettersGumtree[0]);

	
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
		
		//JobApplication.SetMessagesGumtree(CoverLettersGumtree);

	}
	
	@Override
	public void BuildMessagesSeek() {
		
		//JobApplication.SetMessagesSeek(CoverLettersSeek);

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

	@Override
	public void SetJobAdd(JobAdd jobAdd1) {
		
		this.JobAdd = jobAdd1;
		
	}
	
	@Override
	public JobAdd GetJobAdd() {
		
		return JobAdd;
		
	}

	@Override
	public void ProcessVarsInit() throws SQLException {
		// TODO Auto-generated method stub
		
	}

}

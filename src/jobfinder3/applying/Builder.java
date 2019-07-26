//The builder for the Job Application Object
package applying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Builder implements JobApplicationBuilder {
	
	private int SpaceSize, SpaceIdx, MaxSpaceSize, NumberCombins, CombinPos = 0, OrigWordCounter = 0, Combination = 0;
	private int[] SpaceSizes;
	private int[][] SpacePos, Combinations;
	private String[] OrigWords;
	private JobApplication JobApplication;
	private HashMap<String, int[]> SpacesToRep = new HashMap<String, int[]>();
	private String NewKey;
	
	public Builder(){
	
		
		
	}
	
	//Setters
	@Override
	public void SetOrigWords(String[] OrigWords) {
		
		this.OrigWords = OrigWords;
		
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
	    	System.out.println(OrigWord);
	    	System.out.println(OrigWord.indexOf(" "));
	    	
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
		
		SpacesToRep = new HashMap<String, int[]>();
		
		int x = 0;
		for(int[] Pos: SpacePos) {
			
			SpacesToRep.put(OrigWords[x], Pos);
			System.out.println(OrigWords[x] + ":"+ Arrays.toString(Pos));
			
			x++;
			
		}
	  
	}

	//RECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSIONRECURSION
	//Replaces the spaces appropriatley in each enum
	@Override
	public void SpaceRep() {
		
		//The new method for SpaceRep
		//Go down through combinations
		for (int y = 0; y < NumberCombins; y++) {
			
			//Left to right for spaces index and boolean positions
			for (int x = 0; x < MaxSpaceSize; x++) {
				
				//Leave once were at the last
				if (OrigWordCounter == OrigWords.length) {
				
					return;

				}
				
				//To show the original word
				System.out.println("The Original Word:");
				System.out.println(OrigWords[OrigWordCounter]);
				
				//To check that the combinations are correct
				System.out.println("x: " + x + " y: " + y);
				System.out.println(Combinations[y][x]);
				//To check where the space position was
				System.out.println(SpacePos[OrigWordCounter][x]);
				
				//Recursion for going through to the next word
				if (x == MaxSpaceSize - 1 && y == NumberCombins - 1) {
					
					OrigWordCounter = OrigWordCounter + 1;
					SpaceRep();
					
				}
				
			}
			
		}
		
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
	public void ProcessVars() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ProcessAge() {
		// TODO Auto-generated method stub

	}

	@Override
	public applying.JobApplication GetJobApplication() {

		return JobApplication;
		
	}

}

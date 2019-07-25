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
		
		//The Value of the hashmap
		System.out.println(Arrays.toString(SpacesToRep.get(OrigWords[OrigWordCounter])));
		//The Key of the hashmap
		System.out.println("HashMap Key:\n" + SpacesToRep.keySet().toArray()[OrigWordCounter].toString());
		
		//Loop through the hashmap
				//for (String SpaceToRep: SpacesToRep.keySet()) {
					
					//When we get to the final combination it's time to go to the next word and re-start the loop.
					   if (Combination == NumberCombins) {
							
							Combination = 0;
							OrigWordCounter = OrigWordCounter + 1;
							
						}
					
					//System.out.println("\n" + SpaceToRep + " : " + Arrays.toString(SpacesToRep.get(SpaceToRep)) + "\n");
					//StringBuilder NewWord = new StringBuilder(SpaceToRep);
					//for(int[] Combination: Combinations) {
						//System.out.println(NewWord.toString());
						
					System.out.println(NumberCombins);
					System.out.println(Combination);
					
						System.out.println("This Combination Specifically:");
						System.out.println(Combinations[Combination][CombinPos]);
						
						System.out.println("Combination Position:");
						System.out.println(CombinPos);
						
						if (Combinations[Combination][CombinPos] == 1 && SpacesToRep.get(OrigWords[OrigWordCounter])[CombinPos] != -1 && CombinPos == 0) {
							
							//System.out.println(SpacesToRep.get(SpaceToRep)[CombinPos]);
							NewKey = SpacesToRep.keySet().toArray()[OrigWordCounter].toString().substring(0, SpacesToRep.get(OrigWords[OrigWordCounter])[CombinPos]) + 
									SpacesToRep.keySet().toArray()[OrigWordCounter].toString().substring(SpacesToRep.get(OrigWords[OrigWordCounter])[CombinPos]+1);
							//NewWord.deleteCharAt(SpacesToRep.get(SpaceToRep)[CombinPos]);
							//String NewKey = NewWord.toString();
							
						} else if (Combinations[Combination][CombinPos] == 1 && SpacesToRep.get(OrigWords[OrigWordCounter])[CombinPos] != -1) {
							
							NewKey = NewKey.substring(0, SpacesToRep.get(OrigWords[OrigWordCounter])[CombinPos]) + 
									SpacesToRep.keySet().toArray()[OrigWordCounter].toString().substring(SpacesToRep.get(OrigWords[OrigWordCounter])[CombinPos]+1);
							
						}
						
						if(CombinPos < MaxSpaceSize - 1) {
							
							System.out.println(NewKey + "\n");
							CombinPos++;
							
							
							SpaceRep();
							
							//If it's at the last index it needs to be taken back to start over agian.
						}if(CombinPos == MaxSpaceSize - 1) {
							
							System.out.println(NewKey + "\n");
							
							//Go right
							CombinPos = 0;
							//Go down
							Combination++;
							
							//The Word needs to be re-set as well.
							NewKey = SpacesToRep.keySet().toArray()[OrigWordCounter].toString();
							System.out.println();
							
							SpaceRep();
							
						
						}
						
					}
				
				  
				 
				
					//System.out.println(Arrays.toString(SpacesToRep.get(SpaceToRep).get(0)) + " " + Arrays.toString(SpacesToRep.get(SpaceToRep).get(1)));
					
					//Loop through the combinations
					/*
					 * for (int[] Combination: Combinations) {
					 * 
					 * //To check where stuff is visually if need be:
					 * System.out.println(SpaceToRep);
					 * System.out.println(Arrays.toString(SpacesToRep.get(SpaceToRep)));
					 * System.out.println(Arrays.toString(Combination) + "\n");
					 * 
					 * values.add(Combination); StringBuilder ReplaceSpace = new
					 * StringBuilder(SpaceToRep); //ReplaceSpace.
					 * 
					 * }
					 */
	
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

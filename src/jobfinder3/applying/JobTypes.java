//For creating Job Application objects. This also contains the title of the job for each enum.
package applying;

public enum JobTypes {

	KitchenHand("Kitchen Hand", new String[] {"kitchen hand", "kitchen helper", "kitchen assistant", "dishy",
		"dish pig", "dishie", "dish washer", "dish hand"}),
	Glassy("Glassy", new String[]{"glassy", "bar back", "glassie"}),
	BarMan("Bar Man", new String[]{"bar tender", "bar man", "bar woman", "bar staff", "cocktail maker",
			"bar keep", "bar chef", "alcohol chef", "fair man", "bar attendant"}),
	Waiter("Waiter", new String[]{"waiter", "waitress", "front end staff", "wait person",
			"wait staff", "bus boy", "steward", "food runner", "foh", "front of house", "floor staff", "cafe all rounder", 
			"cafe staff"});
	
	private String JobTitle;
	private String[] OrigWords;
	
	JobTypes(String JobTitle, String[] OrigWords){
		
		this.JobTitle = JobTitle;
		this.OrigWords = OrigWords;
		
	}
	
	public String JobTitle() {
		
		return JobTitle;
		
	}
	
	public String[] OrigWords() {
		
		return OrigWords;
		
	}
}

//For creating Job Application objects. This also contains the title of the job for each enum.
package applying;

public enum JobTypes {

	//The job titles followed by each way that that could have been said
	KitchenHand("Kitchen Hand", new String[] {"kitchen hand", "kitchen helper", "kitchen assistant",
			"dishy", "dish pig", "dishie", "dish washer", "dish hand"}),
	Glassy("Glassy", new String[]{"glassy", "bar back", "glassie"}),
	BarMan("Bar Man", new String[]{"bar tender", "bar man", "bar woman", "bar staff", "cocktail maker",
			"bar keep", "bar chef", "alcohol chef", "fair man", "bar attendant", "pub staff",
			"bar work", "pub work", "bar people", "bar men", "bar women",
			"food and beverage attendant"}),
	Waiter("Waiter", new String[]{"waiter", "waitress", "front end staff", "wait person",
			"wait staff", "bus boy", "steward", "food runner", "foh", "front of house", "floor staff",
			"cafe all rounder", "cafe staff", "waiting staff", "lunch bar all rounder",
			"restraunt staff", "floor person", "cafe job", "bistro staff"}),
	Labourer("Labourer", new String[]{"labourer", "labouring", "off sider", "trade assistant", 
			"trades assistant", "t/a", "hammer hand", "mud boy", "barrow boy", "factory hand"}),
	Admin("Administration Clerk", new String[]{"administration", "admin", "receptionist", 
			"office support", "office clerk", "office administration", "office admin", "front desk",
			"data entry", "personal assistant", "office assistant"}),
	TrolleyBoy("Trolley Boy", new String[]{"trolley boy", "trolley pusher"}),
	SuperMarket("Super Market Professional", new String[]{"shop assistant", "night filler", 
			"shelf stacker", "replenishment", "fruit and veg assistant", 
			"fruit and vegetable assistant", "dairy assistant", "freezer assistant", 
			"check out operator", "check out chick", "grocery department"}),
	Driver("Driver", new String[] {"driver", "courier", "delivery", "delivery driver"}),
	Retail("Retail Professional", new String[]{"sales assistant", "sales", "shop assistant", 
			"lotto sale", "retail assistant", "retail professional"}),
	CallCentre("Call Centre", new String[]{"call centre operator", "sales representative",
			"tele caller"}),
	DogWalker("Dog Walker", new String[]{"dog walker", "dog walking"}),
	MathsTutor("Mathematics Tutor", new String[]{"math tutor", "maths tutor", "mathematics tutor"}),
	PhysicsTutor("Physics Tutor", new String[]{"physics tutor", "physical sciences tutor"}),
	ITSupport("IT Professional", new String[] {"it support", "level one help desk",
			"level one support", "help desk", "it support technician"}),
	Developer("Software Developer", new String[] {"software developer", "software engineer",
			"app developer", "developer", "web developer", "business intelligence", "coder", "software development"}),
	Cleaner("Cleaner", new String[]{"cleaner", "cleaning", "industrial cleaner"}),
	TransportLogistics("Logistics Professional", new String[]{"traffic controller", "packer", 
			"pick packer", "work shop assistant", "ware house assistant", "ware house worker",
			"store person"});
	
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

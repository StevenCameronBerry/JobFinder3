/* The Job Add Object
 */
package jobfinder3;

public class JobAdd implements JobAddPlan {
        
    private String UniqueID, Title, Location, Age, URL, SalaryType, JobType,
            Description, TitleDesc, Website, AdvertiserName, CompanyName, 
            InvalidStr, TitleClear, DescriptionClear;
    private int ID, Distance_km;
    
    JobAdd(){
        
    }
    
    //Copy Constructor to make an immutable object
    public JobAdd(JobAdd add)
    {
        
        //For Unique ID
        this.SetWebsite(add.GetWebsite());
        this.SetID(Integer.parseInt(add.GetID()));
        this.SetUniqueID(add.GetUniqueID());
        this.SetTitle(add.GetTitle());
        this.SetURL(add.GetURL());
        this.SetAge(add.GetAge());
        this.SetDistance_km(Integer.parseInt(add.GetDistance_km()));
        this.SetJobType(add.GetJobType());
        this.SetLocation(add.GetLocation());
        this.SetSalaryType(add.GetSalaryType());
        this.SetCompanyName(add.GetCompanyName());
        
    }
        
    /*Setters*/
    //Required to make UniequeID
    @Override
    public void SetWebsite(String Website){
        //Must be Gumtree, Seek Linkedin or Facebook. AND 46 CHARS IN LENGTH
        
        this.InvalidStr = Website + " is an invalid Website.\nThe Website " +
                "must be one of the following:\nSeek\nGumtree\nIndeed\nFaceb" +
                "ook\nLinkedin\nOtherwise the URL can not be built from it.";

        if (Website.equals("Seek") || Website.equals("Gumtree") ||
                Website.equals("Indeed") || Website.equals("Facebook") || 
                Website.equals("Linkedin") && Website.length() < 46){
            
            this.Website = Website;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }
        

    }
    @Override
    public void SetID(int ID){
        
        /*TODO:
        Add any validation for ID types in specific websites.
        */

        this.ID = ID;

    }
    @Override
    public void SetUniqueID(String UniqueID){
        /*Must be the first Char of Website followed by the ID AND 46 CHARS IN 
        LENGTH*/
        
        this.InvalidStr = UniqueID + " is an invalid Unique ID.\nThe Unique" +
                " ID must be the first letter of the website " + this.Website + 
                " followed by the ID of this object " + this.ID;
        
        if (UniqueID.equals(this.Website.charAt(0) + String.valueOf(ID)) && 
                UniqueID.length() < 46){
            
            this.UniqueID = UniqueID;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }

    }

    //Required to make Title+Desc
    @Override
    public void SetTitle(String Title){
        //Must be 85 chars in length
        
        this.InvalidStr = Title + " must be under 86 characters in length.";

        if (Title.length() < 86){
            
            this.Title = Title;
            
        } else {
         
            System.err.println(this.InvalidStr);
            
        }

    }
    @Override
    public void SetDescription(String Description){

        this.Description = Description;

    }
    @Override
    public void SetTitleDesc(String TitleDesc){
        /*Must be all lower case this.Title followed by " || " followed by 
        this.Description all lower case. Can not contain "&" or "é"*/
        
        /*Make clean appropriately formatted attributes for title and
        description*/
        this.TitleClear = this.Title.replaceAll("&", "and");
        this.TitleClear = this.TitleClear.replaceAll("é", "e");
        this.TitleClear = this.TitleClear.replaceAll("'", "");
        this.TitleClear = this.TitleClear.toLowerCase();
        this.DescriptionClear = this.Description.replaceAll("&", "and");
        this.DescriptionClear = this.DescriptionClear.replaceAll("'", "");
        this.DescriptionClear = this.DescriptionClear.replaceAll("é", "e");
        this.DescriptionClear = this.DescriptionClear.replace("`", "");
        this.DescriptionClear = this.DescriptionClear.replace("-", "");
        this.DescriptionClear = this.DescriptionClear.toLowerCase();
        
        this.InvalidStr = TitleDesc + "\n is an invalid TitleDesc.\n\nThe " + 
                " TitleDesc must not have the characters '&' or 'é' and must " +
                "be the the Title followed by || followed by the Description." +
                " ie.\n\n" + TitleClear.toLowerCase() + " || " + 
                DescriptionClear.toLowerCase();
        
        if (TitleDesc.equals(TitleClear + " || " + DescriptionClear)){
            
            this.TitleDesc = TitleDesc;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }
        
    }

    //Required to make on some
    //Distance
    @Override
    public void SetLocation(String Location){
        //MUST BE 46 CHARS IN LENGTH

        this.InvalidStr = Location + " must be under 46 characters in length.";
        
        if (Location.length() < 46){
            
            this.Location = Location;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }

    }
    @Override
    public void SetDistance_km(int Distance_km){

        this.Distance_km = Distance_km;

    }
    //Advertiser
    @Override
    public void SetAdvertiserName(String AdvertiserName){
        //MUST BE 82 CHARS IN LENGTH

        this.InvalidStr = AdvertiserName + 
                " must be under 82 characters in length.";
        
        if (AdvertiserName.length() < 81){
            
            this.AdvertiserName = AdvertiserName;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }

    }
    @Override
    public void SetCompanyName(String CompanyName){
        //MUST BE 46 CHARS IN LENGTH

        this.InvalidStr = CompanyName + 
                " must be under 81 characters in length.";
        
        if (CompanyName == null){
            
            this.CompanyName = "null";
            
        } else if (CompanyName.length() < 81) {
            
            this.CompanyName = CompanyName;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }

    }

    //Has dependencies allready listed
    @Override
    public void SetURL(String URL){
        //MUST CONTAIN this.website.toLowerCase, start with https://www., 
        //AND BE < 46 CHARS IN LENGTH
        
        this.InvalidStr = URL + "must contain " + this.Website.toLowerCase() + 
                " and start with https://www. and be < 46 characters in " +
                "length.";
        
        String upToNCharacters = URL.substring(0, Math.min(URL.length(), 12));
        
        if (upToNCharacters.equals("https://www.") && 
                URL.contains(this.Website.toLowerCase()) && URL.length() < 46){
            
            this.URL = URL;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }

    }

    //Not built from multiple constituent parts or required for thereof.
    @Override
    public void SetAge(String Age){
        //MUST BE yyyy-MM-dd HH:mm:ss
        
        this.InvalidStr = Age + "must be of the format\nyyyy-MM-dd HH:mm:ss";
        
        this.Age = Age;

    }
    @Override
    public void SetSalaryType(String SalaryType){
        //MUST BE 51 CHAR IN LENGTH

        this.InvalidStr = SalaryType + " must be under 52 characters in length.";
        
        if (SalaryType.length() < 52){
            
            this.SalaryType = SalaryType;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }

    }
    @Override
    public void SetJobType(String JobType){
        //MUST BE 46 CHAR IN LENGTH 

        this.InvalidStr = JobType + " must be under 46 characters in length.";
        
        if (JobType.length() < 46){
            
            this.JobType = JobType;
            
        } else {
            
            System.err.println(this.InvalidStr);
            
        }

    }

    /*Getters*/
    //Required to make UniequeID
    @Override
    public String GetWebsite(){

        return this.Website;
    }
    @Override
    public String GetID(){

        return String.valueOf(ID);

    }
    @Override
    public String GetUniqueID(){

        return this.UniqueID;
        
    }

    @Override
    //Required to make Title+Desc
    public String GetTitle(){

        return this.Title;
                    
    }
    @Override
    public String GetDescription(){

        return this.Description;
                    
    }
    @Override
    public String GetTitleDesc(){

        return this.TitleDesc;
                    
    }

    //Required to make on some
    //Distance
    @Override
    public String GetLocation(){

        return this.Location;
                    
    }
    @Override
    public String GetDistance_km(){

        return String.valueOf(this.Distance_km);
                    
    }
    //Advertiser
    @Override
    public String GetAdvertiserName(){

        return this.AdvertiserName;
                    
    }
    @Override
    public String GetCompanyName(){
        
        return this.CompanyName;
        
    }

    //Has dependencies allready listed
    @Override
    public String GetURL(){

        return this.URL;
                    
    }

    //Not built from multiple constituent parts or required for thereof.
    @Override
    public String GetAge(){

        return this.Age;
        
    }

    @Override
    public String GetSalaryType(){

        return this.SalaryType;
                    
    }

    @Override
    public String GetJobType(){

        return this.JobType;
                    
    }
    
    @Override
    public String toString(){
        
        return "\n===================\n" + "UniqueID: " + 
            this.UniqueID + "\nTite: " + this.Title + "\nCompany Name: " + 
            this.CompanyName + "\nLocation: " + this.Location + "\nDistance: " +
            this.Distance_km + "\nAge: " + this.Age + "\nURL: " + this.URL + 
            "\n===================\n";
        
    }
    
}
/* Interface for job applications
 */
package jobfinder3;

public interface JobAddPlan {
    
    /*Setters*/
    //Required to make UniqueID
    public void SetWebsite(String Website);
    public void SetID(int ID);
    public void SetUniqueID(String UniqueID);
    
    //Required to make Title+Desc
    public void SetTitle(String Title);
    public void SetDescription(String Description);
    public void SetTitleDesc(String TitleDesc);
    
    //Required to make on some
    //Distance
    public void SetLocation(String Location);
    public void SetDistance_km(int Distance);
    //Advertiser
    public void SetAdvertiserName(String AdvertiserName);
    public void SetCompanyName(String CompanyName);
    
    //Has dependencies allready listed
    public void SetURL(String URL);
    
    //Not built from multiple constituent parts or required for thereof.
    public void SetAge(String Age);
    public void SetSalaryType(String SalaryType);
    public void SetJobType(String JobType);
    
    /*Getters*/
    //Required to make UniequeID
    public String GetWebsite();
    public String GetID();
    public String GetUniqueID();
    
    //Required to make Title+Desc
    public String GetTitle();
    public String GetDescription();
    public String GetTitleDesc();
    
    //Required to make on some
    //Distance
    public String GetLocation();
    public String GetDistance_km();
    //Advertiser
    public String GetAdvertiserName();
    public String GetCompanyName();
    
    //Has dependencies allready listed
    public String GetURL();
    
    //Not built from multiple constituent parts or required for thereof.
    public String GetAge();
    public String GetSalaryType();
    public String GetJobType();
    
}
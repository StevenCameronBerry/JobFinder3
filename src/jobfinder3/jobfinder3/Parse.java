/*For parsing pages and JSON*/
package jobfinder3;

import com.google.gson.JsonObject;

public interface Parse {
    
    public void SetPage();
    public void SetDB();
    public Parse GetParse();
    
    public String GetID();
    public String GetJobType();
    public String GetSalaryType();
    public String GetTitle();
    public String GetLocation();
    public String GetAge();
    public String GetCompanyName();
    public String GetAdvertiserName();
    public String GetDescription();
    public String GetDistance();
    
    //Main Loop
    public void SetJsonIndx(JsonObject Index);
    public void SetStrIndx(String Index);
    public int AddsOnline();
    public int NumPages(int PageSize);
    public void NavigateML();
    
    //Book Loop
    public void NavigateBL();
    public int NumAddsPage();
    
    //Page Loop
    public void NavigatePL(int PageItr);
    public boolean Ignore(int z);
    public boolean CheckDup(String[] AddsInDB);
    
    //Add Specific Loop
    public void NavigateAS();
    public void Parse();
    
}

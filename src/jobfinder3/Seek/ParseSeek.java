/* For Seek
Most of the data for data for seek comes from the JsonArray "ResultList"
 */
package Seek;

import com.google.gson.JsonArray;
import jobfinder3.Parse;
import jobfinder3.WebScrape;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParseSeek implements Parse {
    
    private JsonObject Index;
    private JsonArray ResultList;
    private JsonElement id, jobType, salary, title, location, age, advertiser;
    private String IndexStr, Advertiser, Description, ID = " ";
    private String[] AdvertiserArr;
    private int AddsOnline, NumPages;
    
    public ParseSeek(){
            
        
            
    }
    
    @Override
    public void SetJsonIndx(JsonObject Index){
        
        this.Index = Index;
        
    }
    
    @Override
    public void SetStrIndx(String IndexStr){
        
        this.IndexStr = IndexStr;
        
    }
    
    @Override
    public void SetPage() {
        
        id = Index.get("id");
        jobType = Index.get("workType");
        salary = Index.get("salary");
        title = Index.get("title");
        location = Index.get("area");
        age = Index.get("listingDate");
        advertiser = Index.get("advertiser");
        AdvertiserArr = advertiser.toString().split(
                        "description\":\"");
        this.Advertiser = AdvertiserArr[1].replaceAll("\"}", "");
        
        //For checking if "id" is a duplicate in the "ignore" method
        ID = id.toString();
        
    }
    //Getters from SetPage.
    @Override
    public String GetID(){
        
        return this.id.toString();
        
    }
    @Override
    public String GetJobType(){
        
        return this.jobType.toString();
        
    }
    @Override
    public String GetSalaryType(){
        
        return this.salary.toString();
        
    }
    @Override
    public String GetTitle(){
        
        return this.title.toString();
        
    }
    @Override
    public String GetLocation(){
        
        return this.location.toString();
        
    }
    @Override
    public String GetAge(){
        
        return this.age.toString();
        
    }
    @Override
    public String GetCompanyName(){
        AdvertiserArr = advertiser.toString().split(
                        "description\":\"");
        this.Advertiser = AdvertiserArr[1].replaceAll("\"}", "");
        
        return this.Advertiser;
        
    }
    
    @Override
    public void SetDB() {
        
        Document doc = WebScrape.Parse(IndexStr);

        //Set
        Elements DescriptionEl = doc.getElementsByClass("_2e4Pi2B");
        Description = DescriptionEl.text();
        
    }

    @Override
    public Parse GetParse() {
        
        return this;
        
    }
    
    //Main Loop
    @Override
    public int AddsOnline() {
        
        AddsOnline = Integer.parseInt(Index.get("totalCount").toString());
        
        return AddsOnline;
        
    }

    @Override
    public void NavigateML() {
        
    }

    /*Indeeds Navigage Bookloop has a special function which is to get the 
    appropriate JSON of the descriptions for that job add which all come on the 
    one request.*/
    @Override
    public void NavigateBL() {
        
        ResultList = Index.get("data").getAsJsonArray();
        //System.out.println(Index);
        //System.out.println(ResultList.size());
        
    }
    
    @Override
    public void NavigatePL(int PageItr) {
        
        JsonElement Ecs = ResultList.get(PageItr);
        Index = Ecs.getAsJsonObject();
                
    }

    @Override
    public int NumAddsPage() {
        
        System.out.println(ResultList.size() + " adds on page.");
        
        return ResultList.size();
        
    }

    @Override
    public void NavigateAS() {
        
    }

    @Override
    public boolean Ignore(int z, String[] AddsInDB) {
        
        JsonElement isPremium = Index.get("isPremium");
        //Also need to check for duplicate adds as they have a bug where two duplicates will show up next to each other
        //The old "id" is from the set page method which is ran after ignore
        JsonElement duplicateCheck = Index.get("id");
        
        boolean InDB = Arrays.stream(AddsInDB).anyMatch(ID.toString()::equals);
        
        if(ID.equals(duplicateCheck.toString())){
        	
        	System.out.println("Old ID: " + ID + "\nNew ID: " + duplicateCheck.toString() + "\n");
        	
        }
        
        if(isPremium.toString().equals("true") || InDB){

            return true;

        } else {
            
            return false;
            
        }
        
    }
    
    @Override
    public boolean CheckDup(String[] AddsInDB){
        
        boolean InDB = Arrays.stream(AddsInDB).anyMatch(id.toString()::equals);
        boolean INDB = Arrays.stream(AddsInDB).anyMatch(ID.toString()::equals);
            if(InDB == true || INDB == true){

                return true;

            }else{
                
                return false;
                
            }
        
    }

    @Override
    public String GetDescription() {
        
        return this.Description;
        
    }

    @Override
    public void Parse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int NumPages(int PageSize) {
        
        NumPages = AddsOnline/PageSize + 1;
        
        return NumPages;
        
    }

    //both redundant for seek
    @Override
    public String GetAdvertiserName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String GetDistance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
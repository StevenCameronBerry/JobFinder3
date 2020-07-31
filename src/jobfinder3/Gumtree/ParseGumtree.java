/* For Gumtree.
Most of the data for gumtree comes from the JsonObject "Results"
 */
package Gumtree;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import jobfinder3.Parse;
import jobfinder3.WebScrape;

public class ParseGumtree implements Parse {
    
    private JsonObject Index, Results, dataObj, mainAttributesObj;
    //^To be converted to a blank string
    private JsonArray ResultList;
    private JsonElement id, title, location, age, advertiser,
            data, resultsEL, distance, mainAttributesEl, dataEL;
    private String IndexStr, Advertiser, Description, salary, jobType, Date;
    private String[] AdvertiserArr;
    private int AddsOnline, NumPages;
    
    
    
    
    public ParseGumtree(){
            
        
            
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
        //System.out.println(id);
        title = Index.get("title");
        //System.out.println(title);
        location = Index.get("location");
        //System.out.println(location);
        distance = Index.get("distance");
        //System.out.println(distance);
        age = Index.get("age");
        //System.out.println(age);
        
        //These last two are hidden underneath an element
        mainAttributesEl = Index.get("mainAttributes");
        
        //MAIN ATTRIBUTES ARE COOKED ON GUMTREE, I DONT KNOW WHY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try{
            
            mainAttributesObj = mainAttributesEl.getAsJsonObject();
            dataEL = mainAttributesObj.get("data");
            dataObj = dataEL.getAsJsonObject();
        
        }catch(Exception e){
            
            salary = "";
            jobType = "";
            
        }
        
        //For empty string salary and job types which can not exist in java.
        try{
            
            salary = dataObj.get("salarytype").toString();
            
        }catch(Exception e){
            
            salary = "";
            
        }
        try{
            
            jobType = dataObj.get("jobtype").toString();
            
        }catch(Exception e){
            
            jobType = "";
            
        }
        
        //System.out.println(salary);
        //System.out.println(jobType);
        
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
        
        return this.Advertiser;
        
    }
    
    @Override
    public void SetDB() {
        
        Document doc = WebScrape.Parse(IndexStr);

        //Set
        Elements DescriptionEl = doc.getElementsByClass("vip-ad-description");
        Description = DescriptionEl.text();
        Elements AdvertiserEl = doc.getElementsByClass("seller-profile__name");
        Advertiser = AdvertiserEl.text();
        
    }

    @Override
    public Parse GetParse() {
        
        return this;
        
    }
    
    //Main Loop
    @Override
    public int AddsOnline() {
        
        AddsOnline = Integer.parseInt(Results.get("numFound").toString());
        
        //1200 = 1248 for Gumtree
        if(AddsOnline == 1200){
            
            AddsOnline = 1248;
            
        }
        
        return AddsOnline;
        
    }

    @Override
    public void NavigateML() {
        
        data = Index.get("data");
        dataObj = data.getAsJsonObject();
        resultsEL = dataObj.get("pager");
        this.Results = resultsEL.getAsJsonObject();
        
    }

    @Override
    public void NavigateBL() {
        
        data = Index.get("data");
        dataObj = data.getAsJsonObject();
        resultsEL = dataObj.get("results");
        Results = resultsEL.getAsJsonObject();
        resultsEL = Results.get("resultList");
        ResultList = resultsEL.getAsJsonArray();
        
    }
    
    @Override
    public void NavigatePL(int PageItr) {
        
        JsonElement Ecs = ResultList.get(PageItr);
        Index = Ecs.getAsJsonObject();
                
    }

    @Override
    public int NumAddsPage() {
        
        System.out.println(ResultList.size() + " adds on page.");
        
        return ResultList.size() - 1;
        
    }

    @Override
    public void NavigateAS() {
        
    }

    //Ignore Premium or Freatured Adds.
    @Override
    public boolean Ignore(int z, String[] AddsInDB) {
        
        JsonElement isPremium = Index.get("isPremium");
        JsonElement isFeatured = Index.get("isFeatured");
        
        if(isPremium.toString().equals("true") || 
                isFeatured.toString().equals("true")){

            return true;

        } else {
            
            return false;
            
        }
        
    }
    
    @Override
    public boolean CheckDup(String[] AddsInDB){
        
        boolean InDB = Arrays.stream(AddsInDB).anyMatch(id.toString()::equals);
            if(InDB == true){

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
        
        NumPages = Integer.parseInt(Results.get("lastPageNum").toString());
        
        /*Gumtree is always limited to 13 pages when you have 96 adds per page
        but has a glitch sometimes where it says it went over in their API*/
        if(NumPages > 13){
            
            NumPages = 13;
            
        }
        
        return NumPages;
        
    }

    @Override
    public String GetAdvertiserName() {
        
        return Advertiser;
        
    }
    
    @Override
    public String GetDistance() {
        
        return distance.toString();
        
    }
    
}
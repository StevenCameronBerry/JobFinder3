/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indeed;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import jobfinder3.Parse;
import jobfinder3.WebScrape;
/**
 *
 * @author Administrator
 */
public class ParseIndeed implements Parse {

    private JsonObject Index;
    private JsonArray ResultList;
    private String  Advertiser, Description, AddsOnlineTxt, id, jobType, salary,
            title, location, age, advertiser, CompanyName, Wage;
    private String[] AdvertiserArr;
    private int AddsOnline, NumPages, NumAddsPage;
    private Document IndexStr;
    private Elements PageAdds, LastPageAdd;
    private Element Add;
    
    public ParseIndeed(){
            
    }
    
    @Override
    public void SetJsonIndx(JsonObject Index){
        
        this.Index = Index;
        
    }
    
    @Override
    public void SetStrIndx(String IndexStr){
        
        this.IndexStr = Jsoup.parse(IndexStr);
        
    }
    
    @Override
    public void SetPage() {
    	
    	Elements Adds = Add.children();
        
        //Everything Except last
        //attributes in HTML are like attributes in OOP, Steven.
        id = Add.attr("data-jk");
        title = Add.getElementsByClass("turnstileLink").attr("title");
        location = Add.getElementsByClass("location").text();
        CompanyName = Add.getElementsByClass("company").text();
        Wage = Add.getElementsByClass("salarySnippet").text();
        
        //Get Age too
        
        //System.out.println(title);
        
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
        
        return CompanyName;
        
    }
    
    @Override
    public void SetDB() {
        
        //Document doc = WebScrape.Parse(IndexStr);

        //Set
        //Elements DescriptionEl = doc.getElementsByClass("_2e4Pi2B");
        //Description = DescriptionEl.text();
        
    }

    @Override
    public Parse GetParse() {
        
        return this;
        
    }
    
    //Main Loop
    @Override
    public int AddsOnline() {
        
        AddsOnlineTxt = IndexStr.getElementById("searchCount").text();
        //Page n of x jobs, the text after "f" + 2
        String AddsOnlineFormat1 = AddsOnlineTxt.substring(AddsOnlineTxt.lastIndexOf("f") + 2);
        //before the space
        String AddsOnlineFormat2 = AddsOnlineTxt.substring(AddsOnlineTxt.lastIndexOf(" "));
        String AddsOnlineFormat3 = AddsOnlineFormat1.replace(AddsOnlineFormat2, "");
        String AddsOnlineFormat4 = AddsOnlineFormat3.replace(",", "");
        AddsOnline = Integer.parseInt(AddsOnlineFormat4);
        
        //Indeed is actually limited to 1000 adds.
        
        
        return AddsOnline;
        
    }

    @Override
    public void NavigateML() {
        
    }

    @Override
    public void NavigateBL() {
        
        //Get all of the job add "cards" into an Elements object.
        PageAdds = IndexStr.getElementsByClass
        ("jobsearch-SerpJobCard unifiedRow row result");
        
    }
    
    @Override
    public void NavigatePL(int PageItr) {
    	
    	NavigateBL();
    	
        Add = PageAdds.get(PageItr);
        
    }

    @Override
    public int NumAddsPage() {
        
        /*Get the number of job add "cards" and count the number of elemnts in 
        the array.*/
        NumAddsPage = PageAdds.size();
        
        System.out.println(NumAddsPage + " adds on page.");
        
        return NumAddsPage;
        
    }

    @Override
    public void NavigateAS() {
        
    }

    //Redundant for Indeed
    @Override
    public boolean Ignore(int z) {
        
        return false;
        
    }
    
    @Override
    public boolean CheckDup(String[] AddsInDB){
        
        boolean InDB = Arrays.stream(AddsInDB).anyMatch(id.toString()::equals);
        
            if(InDB == true){

                return true;

            } else {
                
                return false;
                
            }
        
    }

    @Override
    public String GetDescription() {
        
    	//System.out.println(this.Description);
        return this.Description;
        
    }

    @Override
    public void Parse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int NumPages(int PageSize) {
        
        NumPages = AddsOnline/PageSize + 1;
        
        //For indeed the number of pages can not be greater than 20
        if(NumPages > 20){
            
            NumPages = 20;
            
        }
        
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

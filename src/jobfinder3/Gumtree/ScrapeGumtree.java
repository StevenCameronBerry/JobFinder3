package Gumtree;

import java.math.BigInteger;

import com.google.gson.JsonObject;

import jobfinder3.Scrape;
import jobfinder3.WebScrape;

public class ScrapeGumtree implements Scrape {
    
    private JsonObject JsonIndex;
    private String str, StrIndex;

    @Override
    public String BuildString(int Page, int PageSize) {
        
        String Website = "https://www.gumtree.com.au/ws/search.json?categoryId=9302&locationId=3008546&locationStr=SouthPerth&offerType=OFFER&pageNum=" + Page + "&pageSize=" + PageSize + "&previousCategoryId=9302&radius=50&sortByName=date";
        
        return Website;
        
    }

    @Override
    public void SetString(String Website, int Page) {
        
        this.str = Website;
        
    }

    //Only to be ran if it's not the first page.
    @Override
    public void ScrapeIndx(int Page) throws Exception{
        
        JsonIndex = WebScrape.JSONConnect(str);
        
    }

    @Override
    public JsonObject GetJsonIndex() {
        
        return this.JsonIndex;
        
    }

    @Override
    public String GetStringIndex() {
        
        return this.StrIndex;
        
    }

    //Add Specific Loop
    @Override
    public String BuildString(String ID) {
        
        String Website = "https://www.gumtree.com.au/s-ad/" + ID;
        
        return Website;
        
    }

    @Override
    public void SetString(String Website) {
        
        this.str = Website;
        
    }
    

    @Override
    public void ScrapeIndx(int[] Counter) {
        
        this.StrIndex = WebScrape.Connect(str, Counter);
        
    }
    
}

package jobfinder3;

import com.google.gson.JsonObject;

public class ScrapeSeek implements Scrape {
    
    private JsonObject JsonIndex;
    private String str, StrIndex;

    @Override
    public String BuildString(int Page, int PageSize) {
        
        String Website = "https://chalice-search-api.cloud.seek.com.au/search?siteKey=AU-Main&sourcesystem=houston&where=All+Perth+WA&page=" + Page + "&pageSize=" + PageSize + "&seekSelectAllPages=false&isDesktop=true&sortmode=ListedDate";
        
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
    public String BuildString(int ID) {
        
        String Website = "https://www.seek.com.au/job/" + ID;
        
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

/* For the scraper whose only purpose is to be encapsulated.*/
package jobfinder3;

import java.math.BigInteger;

import com.google.gson.JsonObject;

public interface Scrape {
    
    public JsonObject GetJsonIndex();
    public String GetStringIndex();
    
    //Main Loop
    public String BuildString(int Page, int PageSize);
    public void SetString(String Website, int Page);
    public void ScrapeIndx(int Page) throws Exception;
    
    //Add Specific Loop
    public String BuildString(String ID);
    public void SetString(String Website);
    public void ScrapeIndx(int[] Counter);
    
}
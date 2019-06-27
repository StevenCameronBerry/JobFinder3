/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indeed;

import java.math.BigInteger;

import com.google.gson.JsonObject;
import jobfinder3.Scrape;
import jobfinder3.WebScrape;

public class ScrapeIndeed implements Scrape {

    public ScrapeIndeed() {
    }
    
    private JsonObject JsonIndex;
    private String str, IDStr;
    private int Page;
    private String[] StrIndex = new String[1];
    BigInteger IDBigInt;

    @Override
    public String BuildString(int Page, int PageSize) {
        
        //Indeeds pages are Page * 50
        PageDet(Page);
        System.out.println(this.Page);
        String Website = "https://au.indeed.com/jobs?as_and=&as_phr=&as_any=&as_not=&as_ttl=&as_cmp=&jt=all&st=&as_src=&salary=&radius=50&l=Perth+WA&fromage=any&limit=" + PageSize + "&sort=date&psf=advsrch&start=" + this.Page;
        
        return Website;
        
    }
    
    //Indeeds pages are Page * 50
    private void PageDet(int Page){
        
        this.Page = (-1 + Page)*50;
        
    }

    @Override
    public void SetString(String Website, int Page) {
        
        this.str = Website;
        
    }

    //Only to be ran if it's not the first page.
    @Override
    public void ScrapeIndx(int Page) throws Exception{
        
        int[] i = {1};
        
        StrIndex[0] = WebScrape.Connect(str, i);
        
        //System.out.println(StrIndex[0]);
        
    }

    @Override
    public JsonObject GetJsonIndex() {
        
        return this.JsonIndex;
        
    }

    @Override
    public String GetStringIndex() {
        
        return this.StrIndex[0];
        
    }

    //Add Specific Loop
    @Override
    public String BuildString(String ID) {
    	
        String Website = "https://au.indeed.com/viewjob?jk=" + ID + "&from=vjs&vjs=1&tk&advn=6489561747155152&adid=277648077";
        
        return Website;
        
    }

    @Override
    public void SetString(String Website) {
        
        this.str = Website;
        
    }
    

    @Override
    public void ScrapeIndx(int[] Counter) {
        
        this.StrIndex[0] = WebScrape.Connect(str, Counter);
        
    }
    
}

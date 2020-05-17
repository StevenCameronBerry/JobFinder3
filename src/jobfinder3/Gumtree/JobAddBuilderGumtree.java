/*Job Add Builder Implementation for seek. There are two steps to building most 
objects these are:
1. Format
2. Build

UniqueID, TitleDesc, AdvertiserName and URL are made from pre-existing builds*/
package Gumtree;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import jobfinder3.JobAdd;
import jobfinder3.JobAddBuilder;
import jobfinder3.Parse;

public class JobAddBuilderGumtree implements JobAddBuilder {
    
    private JobAdd JobAdd;
    private Parse Parse;
    private String IDStr, UniqueID, Input, Description, AdvertiserName,
            Title, Location, JobType, SalaryType, Age, DistTmp, 
            Website = "Gumtree";
    private int ID, Distance;
    
    public JobAddBuilderGumtree(){
        
        this.JobAdd = new JobAdd();
        
    }
    
    @Override
    public void SetParse(Parse ParseObj) {
        
        Parse = ParseObj;
        
    }
    @Override
    public void SetJobAdd(JobAdd add) {
        
        JobAdd = add;
        
    }
    
    /*General Format that most parsed objects should go through*/
    public String Format(String Input) {
        
        try{
            
            this.Input = Input.substring(1, Input.length()-1);
            this.Input = this.Input.replace("'", "");
            this.Input = this.Input.replace("\"", "");
            this.Input = this.Input.replace("&", "and");
            this.Input = this.Input.replace("é", "e");
            this.Input = this.Input.replace("�", "e");
            
        } catch(Exception e){
            return this.Input;
        }
        return this.Input;
        
    }
    
    /* To create UniqueID */
    //ID
    //Format
    @Override
    public void FormatID(){
        
        this.IDStr = Parse.GetID();
        this.ID = Integer.parseInt(this.IDStr);
        
    }
    //Build
    @Override
    public void BuildID() {
        
        JobAdd.SetID(this.IDStr);
        
    }
    //Website
    @Override
    public void BuildWebsite(){
        
        JobAdd.SetWebsite(this.Website);
        
    }
    //UniqueID
    //Build
    @Override
    public void BuildUniqueID() {
        //First Char of Website + ID
        
        this.UniqueID = this.Website.charAt(0) + Integer.toString(this.ID);
        JobAdd.SetUniqueID(UniqueID);
        
    }

    /* To create TitleDesc */
    //Title
    @Override
    public void FormatTitle() {
        
        Title = Format(Parse.GetTitle());
        
    }
    //Build
    @Override
    public void BuildTitle() {
        
        JobAdd.SetTitle(this.Title);
        
    }
    
    //Description
    //Format -- CANT USE THE FORMAT METHOD FIRST AS IT CHOPS OFF THE START AND END OF THE STRING BY 1 CHAR
    @Override
    public void FormatDescription(){
        
        this.Description = Parse.GetDescription().replace("'", "");
        this.Description = this.Description.replace("&", "and");
        this.Description = this.Description.replace("é", "e");
        this.Description = this.Description.replace("`", "");
        this.Description = this.Description.replace("-", "");
        this.Description = this.Description.replace("�", "");
        
    }
    //Build
    @Override
    public void BuildDescription() {
        
        JobAdd.SetDescription(this.Description);
        
    }
    //TitleDesc
    //Build
    @Override
    public void BuildTitleDesc() {
        
        /*Must be the title from the Jobadd object as it is updated in the main 
        algorithm*/
        JobAdd.SetTitleDesc(JobAdd.GetTitle().toLowerCase() + " || " + 
                this.Description.toLowerCase());
        
    }

    /*To make Distance_km*/
    //Location
    @Override
    public void FormatLocation() {
        
        this.Location = this.Format(Parse.GetLocation());
        
    }
    @Override
    public void BuildLocation() {
        
        JobAdd.SetLocation(this.Location);
        
    }
    @Override
    public void FormatDistance_km() {
        
        this.DistTmp = this.Format(Parse.GetDistance());
        DistTmp = DistTmp.replace("< ","");
        DistTmp = DistTmp.replace(" km","");
        
        //In case it was zero
        if(DistTmp.equals("") == true){
            
            Distance = 0;
            
        }else{
            
            Distance = Integer.parseInt(DistTmp);
            
        }
        
    }
    //Distance
    @Override
    public void BuildDistance_km() {
        
        JobAdd.SetDistance_km(Distance);
        
    }

    @Override
    public void FormatAdvertiserName() {
        
        this.AdvertiserName = Parse.GetAdvertiserName();
        AdvertiserName = AdvertiserName.replace("'", "");
        
    }
    @Override
    public void BuildAdvertiserName() {
        
        JobAdd.SetAdvertiserName(this.AdvertiserName);
        
    }

    //CompanyName
    //Format
    @Override
    public void FormatCompanyName(){
        
        /*TO DO:
        NLP shit for this one.*/
        
    }
    //Build
    @Override
    public void BuildCompanyName() {
        
        /*TO DO:
        NLP shit for this one.*/
        
    }

    @Override
    public void BuildURL() {
        
        JobAdd.SetURL("https://www." + Website.toLowerCase() + ".com.au/s-ad/" +
                ID);
        
    }

    //Age
    //Format
    @Override
    public void FormatAge() {
        
        if(Parse.GetAge().contains("Yesterday") == true){

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String Yesterday = new SimpleDateFormat
                ("YYYY-MM-dd HH:mm:ss").format(cal.getTime());
            
            Age = Yesterday;

        }else if(Parse.GetAge().contains("minutes ago") == true){

            //Get how many minutes it was
            Age = Parse.GetAge().replace(" minutes ago", "");
            int AgeInt = Integer.parseInt(this.Format(Age));
            //Format Data
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, - AgeInt);
            String MinsAgo = new SimpleDateFormat
                ("YYYY-MM-dd HH:mm:ss").format(cal.getTime());
            
            Age = MinsAgo;

        }else if(Parse.GetAge().contains("hours ago") == true){

            //Get how many hours it was
            Age = Parse.GetAge().replace(" hours ago", "");
            int AgeInt = Integer.parseInt(this.Format(Age));

            //Format Data
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, - AgeInt);
            String HoursAgo = new SimpleDateFormat
                ("YYYY-MM-dd HH:mm:ss").format(cal.getTime());
            
            Age = HoursAgo;

        }else{ //If it just had a date listed

            String AgeFormat = Parse.GetAge();
            /*On Gumtree the old dates get listed as dd-MM-YYYY, were swapping 
            it over to the MySQL DateTimeFormat by isolating those parts and
            re-arranging them manually through indexes*/
            String YYYY = AgeFormat.substring(7, 11);
            String MM = AgeFormat.substring(4, 6);
            String dd = AgeFormat.substring(1,3);
            String DateTime = YYYY + "-" + MM + "-" + dd + " 00:00:00";
            
            Age = DateTime;

        }
        
    }
    //Build
    @Override
    public void BuildAge() {
        
        JobAdd.SetAge(Age);
        
    }

    @Override
    public void FormatSalaryType() {
     
        SalaryType = this.Format(Parse.GetSalaryType());
        
    }
    @Override
    public void BuildSalaryType() {
        
        JobAdd.SetSalaryType(SalaryType);
        
    }
    @Override
    public void FormatJobType() {
        
        JobType = this.Format(Parse.GetJobType());
        
    }
    @Override
    public void BuildJobType() {
        
        JobAdd.SetJobType(JobType);
        
    }

    @Override
    public JobAdd GetJobAdd() {
        
        return JobAdd;
        
    }

    @Override
    public void FormatWage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void BuildWage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indeed;

import java.text.SimpleDateFormat;
import jobfinder3.JobAdd;
import jobfinder3.JobAddBuilder;
import jobfinder3.Parse;
import java.util.Calendar;

public class JobAddBuilderIndeed implements JobAddBuilder {

    private JobAdd JobAdd;
    private Parse Parse;
    private String IDStr, UniqueID, Input, Description, CompanyName,
            Title, Location, JobType, SalaryType, Age, Website = "Indeed", ID;
    private int Distance;
    
    public JobAddBuilderIndeed(){
        
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
            
            this.Input = Input;
            this.Input = this.Input.replace("'", "");
            this.Input = this.Input.replace("\"", "");
            this.Input = this.Input.replace("&", "and");
            this.Input = this.Input.replace("é", "e");
            
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
        
        this.ID = Parse.GetID();
        
    }
    //Build
    @Override
    public void BuildID() {
        
        JobAdd.SetID(this.ID);
        
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
        
        this.UniqueID = this.Website.charAt(0) + this.ID;
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
    //Format
    @Override
    public void FormatDescription(){
        
        this.Description = Parse.GetDescription().replace("'", "");
        this.Description = this.Description.replace("&", "and");
        this.Description = this.Description.replace("é", "e");
        this.Description = this.Description.replace("`", "");
        this.Description = this.Description.replace("-", "");
        
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
        
        //Redundant for seek
        
    }
    //Distance
    @Override
    public void BuildDistance_km() {
        
        JobAdd.SetDistance_km(1337);
        
    }

    @Override
    public void FormatAdvertiserName() {
        
        //To Do: NLP shit for this one.
        
    }
    @Override
    public void BuildAdvertiserName() {
        
        /*TO DO:
        NLP shit for this one.*/
        
    }

    //CompanyName
    //Format
    @Override
    public void FormatCompanyName(){
        
        this.CompanyName = Parse.GetCompanyName().replace("'", "");
        this.CompanyName = CompanyName.replace("&", "and");
        
    }
    //Build
    @Override
    public void BuildCompanyName() {
        
        JobAdd.SetCompanyName(this.CompanyName);
        
    }

    @Override
    public void BuildURL() {
        
        JobAdd.SetURL("https://au." + Website.toLowerCase() + ".com/viewjob?jk=" +
                ID);
        
    }

    //Age
    //Format
    @Override
    public void FormatAge() {
        
        if(Parse.GetAge().equals("Yesterday") == true){

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String Yesterday = new SimpleDateFormat
                ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
            
            Age = this.Format(Yesterday);

        }else if(Parse.GetAge().contains("minutes ago") == true){

            //Get how many minutes it was
            Age = Parse.GetAge().replace(" minutes ago", "");
            int AgeInt = Integer.parseInt(Age);

            //Format Data
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, - AgeInt);
            String MinsAgo = new SimpleDateFormat
                ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
            
            Age = this.Format(MinsAgo);

        }else if(Parse.GetAge().contains("hours ago") == true){

            //Get how many hours it was
            Age = Parse.GetAge().replace(" hours ago", "");
            int AgeInt = Integer.parseInt(Age);

            //Format Data
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, - AgeInt);
            String HoursAgo = new SimpleDateFormat
                ("YYYY-MM-dd HH:MM:ss").format(cal.getTime());
            
            Age = this.Format(HoursAgo);

        }else{ //If it just had a date listed

            String AgeFormat1 = Parse.GetAge().replace("T", " ");
            String AgeFormat2 = AgeFormat1.replace("Z", "");
            
            Age = this.Format(AgeFormat2);

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
        
        
        
    }

    @Override
    public void BuildWage() {
        
        
        
    }
    
}

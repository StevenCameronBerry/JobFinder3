/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indeed;

import jobfinder3.JobAdd;
import jobfinder3.JobAddEngineer;
import jobfinder3.JobAddBuilder;

/**
 *
 * @author Administrator
 */
public class JobAddEngineerIndeed implements JobAddEngineer {

    private JobAddBuilder JobAddBuilder;
    
    public JobAddEngineerIndeed(JobAddBuilder builder){
        
        this.JobAddBuilder = builder;
        
    }
    
    @Override
    public void SetBuilder(JobAddBuilder builder){
        
        this.JobAddBuilder = builder;
        
    }
    
    @Override
    public JobAdd GetJobAdd(){
        
        JobAdd jobadd = JobAddBuilder.GetJobAdd();
        return jobadd;
        
    }
    
    @Override
    public JobAddBuilder GetJobAddBuilder(){
        
        return JobAddBuilder;
        
    }
    
    @Override
    public void MakePage(){
        
        //Required to make UniqueID
        JobAddBuilder.FormatID();
        JobAddBuilder.BuildID();
        JobAddBuilder.BuildWebsite();
        JobAddBuilder.BuildUniqueID();
        JobAddBuilder.BuildURL();
        
        //Required to make Title+Desc
        JobAddBuilder.FormatTitle();
        JobAddBuilder.BuildTitle();
        
        //Has dependencies allready listed
        JobAddBuilder.FormatCompanyName();
        JobAddBuilder.BuildCompanyName();
        JobAddBuilder.FormatLocation();
        JobAddBuilder.BuildLocation();
        
        //Just Regular
        JobAddBuilder.FormatWage();
        JobAddBuilder.BuildWage();
        
    }
    
    @Override
    public void MakeDB(){
        
    	//For Indeed it's one URL with the JSON of the descriptions for all unique ID's
    	
    	
        JobAddBuilder.FormatDescription();
        JobAddBuilder.BuildDescription();
        JobAddBuilder.BuildTitleDesc();
        
    }
    
}

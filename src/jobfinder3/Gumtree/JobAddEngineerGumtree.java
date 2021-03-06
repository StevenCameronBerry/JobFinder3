/*The Engineer for JobAdd Builders*/
package Gumtree;

import jobfinder3.JobAdd;
import jobfinder3.JobAddEngineer;
import jobfinder3.JobAddBuilder;

public class JobAddEngineerGumtree implements JobAddEngineer {
    
    private JobAddBuilder JobAddBuilder;
    
    public JobAddEngineerGumtree(JobAddBuilder builder){
        
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
        JobAddBuilder.FormatLocation();
        JobAddBuilder.BuildLocation();
        JobAddBuilder.FormatAge();
        JobAddBuilder.BuildAge();
        JobAddBuilder.FormatJobType();
        JobAddBuilder.BuildJobType();
        JobAddBuilder.FormatSalaryType();
        JobAddBuilder.BuildSalaryType();
        JobAddBuilder.FormatDistance_km();
        JobAddBuilder.BuildDistance_km();
        
    }
    
    @Override
    public void MakeDB(){
        
        JobAddBuilder.FormatAdvertiserName();
        JobAddBuilder.BuildAdvertiserName();
        JobAddBuilder.FormatDescription();
        JobAddBuilder.BuildDescription();
        JobAddBuilder.BuildTitleDesc();
        
    }
    
}

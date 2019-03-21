/*The Engineer for JobAdd Builders*/
package jobfinder3;

public class JobAddEngineerSeek implements JobAddEngineer {
    
    private JobAddBuilder JobAddBuilder;
    
    public JobAddEngineerSeek(JobAddBuilder builder){
        
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
        JobAddBuilder.FormatAge();
        JobAddBuilder.BuildAge();
        JobAddBuilder.FormatJobType();
        JobAddBuilder.BuildJobType();
        JobAddBuilder.FormatSalaryType();
        JobAddBuilder.BuildSalaryType();
        JobAddBuilder.FormatDistance_km();
        JobAddBuilder.BuildDistance_km();
        
        //System.out.println(this.GetJobAdd().toString());
        
    }
    
    @Override
    public void MakeDB(){
        
        JobAddBuilder.FormatDescription();
        JobAddBuilder.BuildDescription();
        JobAddBuilder.BuildTitleDesc();
        
    }
    
}

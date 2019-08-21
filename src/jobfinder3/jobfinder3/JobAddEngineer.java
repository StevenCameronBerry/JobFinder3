/*For Engineers*/
package jobfinder3;
public interface JobAddEngineer {
    
    public void MakePage();
    public void MakeDB();
    public JobAdd GetJobAdd();
    public JobAddBuilder GetJobAddBuilder();
    
    public void SetBuilder(JobAddBuilder builder);
    
}
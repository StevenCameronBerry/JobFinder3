/* The Job Add Builder Interface. Defines what every Job Add must have.
 */
package jobfinder3;

public interface JobAddBuilder {
    
    public void SetParse(Parse ParseObj);
    public void SetJobAdd(JobAdd add);
    
    //Required to make UniqueID
    public void FormatID();
    public void BuildID();
    public void BuildWebsite();
    public void BuildUniqueID();
    public void BuildURL();

    //Required to make Title+Desc
    public void FormatTitle();
    public void BuildTitle();
    public void FormatDescription();
    public void BuildDescription();
    public void BuildTitleDesc();

    //Required to make on some
    //Distance
    public void FormatLocation();
    public void BuildLocation();
    public void FormatDistance_km();
    public void BuildDistance_km();
    //Advertiser
    public void FormatAdvertiserName();
    public void BuildAdvertiserName();
    public void FormatCompanyName();
    public void BuildCompanyName();

    //Not built from multiple constituent parts or required for thereof.
    public void FormatAge();
    public void BuildAge();
    public void FormatSalaryType();
    public void BuildSalaryType();
    public void FormatJobType();
    public void BuildJobType();
    public void FormatWage();
    public void BuildWage();
    
    public JobAdd GetJobAdd();
    
}

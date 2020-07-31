#This is the run-time environment for the program that applies to job adds.
#One Job Applicant is created which puts an array of job adds into the run-time environment which than encapsulates them
#and applies to them on a one-by one basis.

#The general procedure is this
#1) get all appropriate adds from DB (read the news paper)
#2) put the add data into JSON file for newman (read the job adds indivudally)
#3) tell claire fitzgerald to send Post request (apply to them)

from JobAdd import JobAdd
from JobApplicant import Applicant
from JobApplicantSeek import ApplicantSeek
import json
import time

#Initialize Variables
KeyWords = ["Kitchen Hand", "Glassy", "Bar Man", "Waiter","Trolley Boy","Super Market Professional",
"Retail Professional","Call Centre","Dog Walker"]
#"Kitchen Hand", "Glassy", "Bar Man", "Waiter","Labourer","Administration Clerk","Trolley Boy","Super Market Professional",
#"Retail Professional","Call Centre","Dog Walker","Mathematics Tutor","Physics Tutor",
#"IT Professional","Software Developer","Cleaner","Logistics Professional"
Adds = ["Gumtree", "Seek", "Indeed", "Facebook", "Linkedin"]
AddsItr = 0
KeyWordsItr = 0
request = 0
auth = ['']*3

while True:

    #Re-start the loop if you've gone through all of the keywords and are at the last add
    #if AddsItr == len(Adds) and KeyWordsItr == len(KeyWords):
    
        #KeyWordsItr = 0
        #AddsItr = 0
        
    #Re-start the Key Words and go up an Add once all keywords have been gone through
    if KeyWordsItr == len(KeyWords):
        
        KeyWordsItr = 0
        #AddsItr = AddsItr + 1
        
    #To find out which Job Creating Object to use
    if Adds[AddsItr] == "Gumtree":
        
        Applier = Applicant()
        
    elif Adds[AddsItr] == "Seek":
        
        Applier = ApplicantSeek()
    
    #elif Adds[AddsItr] == "Indeed":
        
        
        
    #elif Adds[AddsItr] == "Facebook":
        
        
        
    #elif Adds[AddsItr] == "Linkedin":
    
    #Extract all job adds
    myresult = Applier.DBExtract(KeyWords[KeyWordsItr])

    #Go over each result from the DB
    JobAdds = ['']*len(myresult)
    for x in range(0, len(myresult)):

        #initialize the JobAdd object
        JobAdds[x] = JobAdd(myresult[x][0])
        JobAdds[x].SetID(myresult[x][1])
        JobAdds[x].SetCoverLetter(myresult[x][15])
        
        print(JobAdds[x].GetID())
            
        #Initaliza variables
        UniqueID = JobAdds[x].GetUniqueID()
        ID = JobAdds[x].GetID()

        #Set the Job Add
        Applier.SetJobAdd(JobAdds[x])
        
        #Make the get request for authentication tokens
        Applier.Get(ID)
        
        #Open the JSON file for newman
        Applier.OpenFile()
        
        #Update the appripriate parameters
        Applier.UpdateRefer()
        Applier.UpdateID()
        Applier.UpdateMsg()
        Applier.UpdateCSRFToken()
        Applier.UpdateCSRFTime()
        Applier.UpdateBBToken()
        
        #Save and close the file
        Applier.SaveFile()

        #Make the post request
        Applier.Post()
        time.sleep(1)
        
        #Open the Gumtree.json file
        Applier.OpenRep()
            
        #Extract the Response Code and Response body from the report
        RespCode = Applier.RespCode()
        Applier.RespBody()
        
        #Mark them in the DB
        Applier.MarkRespCode()
        Applier.MarkRespBody()
        
        #Show what has been applied to and the response code
        print("\nAdd URL")
        print("https://www.gumtree.com.au/s-ad/" + ID)
        print("Request Response")
        print(RespCode)
                
    #Iterate through the keywords.
    KeyWordsItr = KeyWordsItr + 1
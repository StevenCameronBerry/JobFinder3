#This funciton applies to the jobs provided by JobFind.py
#
#input:
#"KeyWord", the keyword found in the job description. (Used to indentify what message to send)
#"URL", the URL of the job.

#Dependencies:
import requests
from CoverLetters import getText

def Apply(KeyWord, URL):

    #Initialize Variables:
    URL = str(URL)
    fromNameI = 'YOUR NAME'
    fromI = 'YOUR EMAIL'
    sendCopyToSenderI = 'true'
    _sendCopyToSenderI = 'on'
    _marketingConsentI = 'on'
    fileI = 'open("RESUME.doc","rb") Content-Type: application/msword'

    #Identify the adId for the post request based on the URL
    IDpos = URL.rfind('/')
    adIdI = URL[IDpos+1:] #How you print from one point to the end of a string in Python,   .

    #Identify message type based on the keyword found in the job description
    if KeyWord == 'Labouring':
        
        messageI = getText('Labourer-CL.docx')

    elif KeyWord == 'Hydraulic':

        messageI = getText('Hose-CL.docx')

    elif KeyWord == 'SecSys':

        messageI = getText('Sec-Syst-CL.docx')

    elif KeyWord == 'Retail':

        messageI = getText('Retail-CL.docx')

    else:

        messageI = 'Hello'

    #Create both the payload and the files to be uploaded
    ResumeUp = {'file': ('Resume.docx', open('Resume.docx','rb'),'application/vnd.openxmlformats-officedocument.wordprocessingml.document')}
    payload = {'message': messageI, 'fromName': fromNameI, 'from': fromI,
	   'sendCopyToSender': sendCopyToSenderI,
	   '_sendCopyToSender': _sendCopyToSenderI,
	   '_marketingConsent': _sendCopyToSenderI, 'adId': adIdI}
	
    #Send the request
    JobApply = requests.post('https://www.gumtree.com.au/j-contact-seller.html',
    data=payload, files=ResumeUp)
    #print(JobApply.status_code, JobApply.reason)
    #print(JobApply.text[:300] + '...')

    print(JobApply.status_code)

    return JobApply.status_code

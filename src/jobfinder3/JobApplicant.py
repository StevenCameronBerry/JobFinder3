#This is the JobApplicant Class for the python program which applies to jobs.
#There will be one instance of this class. It will create an array of JobAdds, put that in the run-time environment than apply to them
#on a one-by-one basis

from JobAdd import JobAdd
import mysql.connector
import requests
from bs4 import BeautifulSoup
import re
from json import loads
import encodings
import json
from subprocess import Popen
import bs4
import os

class Applicant:

    def __init__(self):

        print()

    #Setters
    def SetJobAdd(self, JobAdd):

        self.__JobAdd = JobAdd

    #Getters
    def GetJobAdd(self):

        return self.__JobAdd

    #Read the news paper (get adds from DB)
    def DBExtract(self, KeyWord):

        mydb = mysql.connector.connect(
            
          host="localhost",
          database="jobfinder3",
          user="root",
          password="ZZaq32!!",
          port="3306",
          auth_plugin="mysql_native_password"
          
        )
    
        print(KeyWord)

        mycursor = mydb.cursor()

        mycursor.execute("SELECT * FROM jobfinder3.jf3 where KeyWord = '" + KeyWord + "'  AND Response IS NULL AND Website = 'Gumtree'")

        myresult = mycursor.fetchall()

        mydb.commit()
        mycursor.close()
        mydb.close()

        return myresult
    
    #Open the Gumtree.json file
    def OpenFile(self):
            
        #Open the Gumtree.json file
        with open('PutGumtree.json', 'r', encoding='utf-8') as json_file:
            
            self.__data = json.load(json_file)
                
    #Methods relating to updating the JSON file
    def UpdateRefer(self):
        
        print(self.__JobAdd.GetID())
        
        for self.__p in self.__data['item']:
            
            self.__p['request']['header'][10]['value'] = 'https://www.gumtree.com.au/job-app/me/apply/' + self.__JobAdd.GetID() + '?isFramed=true'
        
    
    def UpdateCSRFToken(self):

        for self.__p in self.__data['item']:

            #CSRF Token
            self.__p['request']['header'][6]['value'] = self.__CSRF
            
    def UpdateURL(self):

        for self.__p in self.__data['item']:

            #CSRF Token
            self.__p['request']['url']['raw'] = 'https://www.gumtree.com.au/job-app/api/v1/me/applied-jobs/' + self.__JobAdd.GetID()


    def UpdatePath(self):

        for self.__p in self.__data['item']:

            #CSRF Token
            self.__p['request']['url']['path'][5] = self.__JobAdd.GetID()            
            
    #close and save that file
    def SaveFile(self):
        
        #Save the file
        jsonFile = open("PutGumtree.json", "w+")
        jsonFile.write(json.dumps(self.__data))
        
        print(jsonFile)
        jsonFile.close()

    #Get request for before updating sectuirty shit
    def Get(self,ID):
    
        headers = {'Host': 'www.gumtree.com.au',
                   'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0',
                   'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
                   'Accept-Language': 'en-US,en;q=0.5',
                   'Accept-Encoding': 'gzip, deflate, br',
                   'Connection': 'keep-alive',
                   'Cookie': 'machId=olRyFLqGMApEdTPpSlqufX0QJEK1xIMDyoNb19Uf7IIKhNDMq1-Y2soWt2q2PUDtTWlq-jJe-2wUwes_glxD7JXqyor87_akQYkb; up=%7B%22ln%22%3A%22822897446%22%2C%22nps%22%3A%2224%22%2C%22lh%22%3A%22l%3D3008845%26k%3Drural%26r%3D0%26icr%3Dfalse%7Cl%3D3008546%26r%3D0%7Cl%3D3008831%26r%3D0%26icr%3Dfalse%7Cl%3D3008766%26r%3D0%26icr%3Dfalse%7Cl%3D3008303%26r%3D50%22%2C%22lbh%22%3A%22l%3D3008546%26k%3Dtoyota%2520hiace%26c%3D18320%26r%3D50%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D0%26c%3D20034%26r%3D0%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%26icr%3Dfalse%7Cl%3D3008303%26c%3D20031%26r%3D50%26sv%3DLIST%26sf%3Dprice_asc%26icr%3Dfalse%22%2C%22rva%22%3A%221213928839%2C1094006120%2C1225640820%2C1225412070%2C1226050174%2C1226418577%2C1221700890%2C1173885938%2C1169503003%2C1227189979%2C1227709890%2C1227212428%2C1225658442%2C1226880789%2C1227480931%22%2C%22lsh%22%3A%22l%3D3008303%26k%3Dyork%26c%3D9296%26r%3D50%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D3008303%26c%3D20031%26r%3D50%26sv%3DLIST%26map%3D100000.00%26sf%3Drank%26icr%3Dfalse%7Cl%3D3008831%26c%3D9302%26r%3D0%26sv%3DLIST%26at%3DOFFER%26sf%3Ddate%7Cl%3D3008303%26k%3Dyork%26c%3D9296%26r%3D50%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%7Cl%3D3008303%26k%3Dwootating%26r%3D50%26sv%3DLIST%26sf%3Ddate%26icr%3Dfalse%22%2C%22ls%22%3A%22l%3D0%26r%3D0%26sv%3DLIST%26sf%3Ddate%22%7D; optimizelyEndUserId=oeu1560869782949r0.17214926398737884; AMCV_50BE5F5858D2477A0A495C7F%40AdobeOrg=2096510701%7CMCIDTS%7C18146%7CMCMID%7C58386376110258854973203384631851669600%7CMCAAMLH-1567856341%7C8%7CMCAAMB-1568351778%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1567754178s%7CNONE%7CvVersion%7C2.0.0%7CMCCIDH%7C-1515736465; cto_lwid=e8637b52-84c2-4b32-972f-4c0322d89998; _ga=GA1.3.1587788353.1560869785; __utma=160852194.1587788353.1560869785.1566569373.1567251540.30; __utmz=160852194.1565792580.17.2.utmcsr=transactional|utmccn=manage_ad|utmcmd=email; __qca=P0-419801697-1560869785456; _fbp=fb.2.1560869787906.1226741766; ki_t=1560869788532%3B1567746983935%3B1567753373558%3B47%3B427; ki_r=; aam_dfp=aamsegid%3D6797281%2C6880889%2C7087286%2C7220740%2C7329284%2C7329285%2C7329301%2C7329276%2C7333809%2C8331089%2C8331046%2C8269554%2C8330578%2C8465423%2C8978953%2C8978954%2C8458219%2C9501605%2C9501648%2C11996984%2C11996991; aam_tnt=aamsegid%3D6797281%2Caamsegid%3D6880889; aam_uuid=58887149265166189473227778725039339418; __gads=ID=4b4b5f50f8e55f3e:T=1560869785:S=ALNI_Ma4OLGgqRu9xJxlpxqIw8ztXRVNHw; cto_idcpy=6daed043-c530-41df-95e2-4ab3c6941a69; _gcl_au=1.1.1529470621.1561973614; cto_bundle=JkESyF9EQllKcFlTcjlDdzI4S1YlMkI5M1pZWVpubEhlQlFuTVQ2cUcxeEtjbWZZT04lMkJXVVZ3d2YlMkZzbjglMkJwZ3N4cFdHOGFzOCUyQlI2eGk2WEpDUiUyQmdaVnFMeVZCcSUyRjBheFJOTjdCalRKdHE4czIxZFFuVE4wY1NPeU5CS1dnTWt4YWdiOTZPdkt3d2c0Zzg2aWJuQUwlMkJjNHByUjlGWW8xV1lwSlVNMG5xUzJFJTJGJTJCMjRzNCUzRA; gtj.session=eyJjc3JmU2VjcmV0IjoiMTlkTmNZMnh4eW92V0duTHA0ZW5VUVJ1IiwidWRpZCI6IjgwMWVlMzdiNjkxNWQ0Mzg1Zjg2YmI5ZWFiMDc3MWJlIiwidG9rZW4iOiJjZGFhZTk5NGVlODI3OGRlN2JjMTBkMGQ2ZDFlMjBkNjUzNDM1Zjk2Mzk3OWJjOWY4ZDQxN2Y1Y2NhNmQ1M2E3IiwiZW1haWwiOiJzdGV2ZW5iZXJyeTMwNUBnbWFpbC5jb20iLCJ1c2VySWQiOiI5NTI4OTA5MTU5OTI3In0=; gtj.session.sig=1iSUsJbqqYSbnPAqnrMqKlsnCtU; G_ENABLED_IDPS=google; fbm_1520623457982850=base_domain=; rl_mid=YsVEea1mVyYlRGx2VthGdskMwRnVHp0dZdlROFFVwkT; wl=%7B%22l%22%3A%22%22%7D; __adroll_fpc=bab55fe6da3d9f3b78f7f5017bcb3210-s2-1565435116398; __ar_v4=UACM6KTFLFHBXEH4TL23NQ%3A20190809%3A2%7CXDZXXCFUMJABXJOUCMFFKX%3A20190809%3A2%7CECUBEOM5QVAJFE4BCIFUR5%3A20190809%3A2; _fbc=fb.2.1567342075605.IwAR3NZgePfqUSRkAmYhFEFCOP1VlVymxw6cr933L4GKGa1Wcz9LfK2ir88yI; __utma=1.1587788353.1560869785.1565855940.1566307803.3; __utmz=1.1565847708.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); fbm_597906056993680=base_domain=.www.gumtree.com.au; bs=%7B%22st%22%3A%7B%7D%7D; __utmc=160852194; AMCVS_50BE5F5858D2477A0A495C7F%40AdobeOrg=1; _gid=GA1.3.1962277690.1567596993; _gali=react-root; criteo_write_test=ChUIBBINbXlHb29nbGVSdGJJZBgBIAE; _gat=1',
                   'Upgrade-Insecure-Requests': '1',
                   'TE': 'Trailers'}
        r = requests.get("https://www.gumtree.com.au/job-app/me/apply/" + ID + "?isFramed=true", headers=headers)

        #print(self.__r.content)

        soup = BeautifulSoup(r.content)

        script_text = soup.find('script')

        #The right <script> tag for CSRF Token, CSRF Time and bbToken
        TokenStr = script_text.text
        

        #CSRFToken
        #print(TokenStr)
        #print("CSRF Token")
        csrflen = len("VGIBuyyv-r2s5cVTxpjhhykAacekq7yQ5Acg")
        #print(TokenStr[TokenStr.find('csrfToken":"') + 12:TokenStr.find('csrfToken":"') + 12 + csrflen])
        self.__CSRF = TokenStr[TokenStr.find('csrfToken":"') + 12:TokenStr.find('csrfToken":"') + 12 + csrflen]

            
    #Tell Newman to post
    def Post(self):
        
        p = Popen("Run.bat", cwd=r"C:\Users\Dell\Desktop\Documents\NetBeansProjects\job-finder-3\src\jobfinder3")
        stdout, stderr = p.communicate()
    
    #Open the Newman HTML Report
    def OpenRep(self):
        
        with open("LatestReport.html") as inf:
            
            txt = inf.read()
            self.__soup = bs4.BeautifulSoup(txt)
            
        inf.close()
            
    #Find the reponse code and the response body
    def RespCode(self):
        
        self.__ResponseCode = self.__soup.find('div', id="ResonseCode").text
        
        return self.__ResponseCode
        
    def RespBody(self):
        
        self.__ResponseBody = self.__soup.find('div', id="ResonseBody").text
        
    #Mark the response Code and Response Body in the DB
    def MarkRespCode(self):
        
        mydb = mysql.connector.connect(
            
          host="localhost",
          database="jobfinder3",
          user="root",
          password="ZZaq32!!",
          port="3306",
          auth_plugin="mysql_native_password"
          
        )

        mycursor = mydb.cursor()

        mycursor.execute("UPDATE jobfinder3.jf3 SET Response = '"+self.__ResponseCode+"' WHERE UniqueID = '"+self.__JobAdd.GetUniqueID()+"'")
        #for x in myresult:
            
          #print(x)

        mydb.commit()
        mycursor.close()
        mydb.close()
        
    def MarkRespBody(self):
        
        
        mydb = mysql.connector.connect(
            
          host="localhost",
          database="jobfinder3",
          user="root",
          password="ZZaq32!!",
          port="3306",
          auth_plugin="mysql_native_password"
          
        )

        mycursor = mydb.cursor()

        mycursor.execute("UPDATE jobfinder3.jf3 SET ResponseBody = '"+self.__ResponseBody+"' WHERE UniqueID = '"+self.__JobAdd.GetUniqueID()+"'")

        mydb.commit()
        mycursor.close()
        mydb.close()
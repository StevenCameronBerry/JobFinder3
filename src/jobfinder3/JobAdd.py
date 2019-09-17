#This is the JobAdd Class for the python program which applies to jobs

class JobAdd:

    #Constructor
    def __init__(self, UniqueID):
    
        self.__UniqueID = UniqueID

    #Setters
    def SetID(self, ID):

        self.__ID = ID

    def SetCoverLetter(self, CoverLetter):

        self.__CoverLetter = CoverLetter

    #Getters
    def GetUniqueID(self):

        return self.__UniqueID

    def GetID(self):

        return self.__ID

    def GetCoverLetter(self):

        return self.__CoverLetter

import socket
import time
import threading
import os
import glob
from os import listdir

s=socket.socket()
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

lastResult = ""


def getNumer(item):
    filename,file_extension = os.path.splitext(item)
    return int(filename)


def deleteFiles(folder,mesFiles):
    files = mesFiles
    print ("keeping "+str(files[-1]))
    files = files[:-1]
    # Remove all files in the other list
    for f in files:
        os.remove('./'+folder+'/'+f)

def getLatestFile(folder):
    global lastResult
    files = listdir("./"+folder)
    files.sort(key=getNumer)
    if (files[-1]==lastResult):
        return ""
    lastResult = files[-1]
    deleteFiles(folder,files)
    return "./"+folder+"/"+files[-1]


def printPercentage(msg,percentage):
    print (msg + " "+str(percentage),end='\r')

def analyzeFile(filename):
	size=0;
	contents =b""

	chunk = 1024*1024
	with open(filename,"rb") as file:
		try:
			bytes_read = file.read(chunk)
			while bytes_read:
				contents+=bytes_read
				bytes_read=file.read(chunk)
		finally:
			file.close()
		
	size=len(contents)

	print ("SIZE:")
	print (size)

	return size,contents		

def RetrieveFile(thread_name,client):
    while True:
        filename = getLatestFile("images")
        if (filename == ""):
            continue
        print (filename)
        if (os.path.isfile(filename)):
            print (filename + "is found")
            filesize,contents=analyzeFile(filename)
            print (str(filesize))
            client.send((filename+","+str(filesize)+'\n').encode())
            starttime =time.time()
            client.send(contents)
            print("done in " + str(time.time() - starttime) + " seconds")
        while True:
            resp = client.recv(10);
            resp = resp.decode();
            if ("OK" in resp):
                break
                


def Main():
    host= ""
    port=3000
    s=socket.socket()
    s.bind((host,port))
    global lastResult
    lastResult= ""
    s.listen(5)

    print("Server started")

    while (True):
        c,addr = s.accept()
        print("Client connected "+ str(addr))
        t=threading.Thread(target=RetrieveFile,args=("retrieveThread",c))
        t.start()

    s.close()


if __name__=="__main__":
    Main()



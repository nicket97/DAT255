import socket
import time
import threading
import os
from os import listdir

s=socket.socket()
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

lastResult = ""


def getNumer(item):
    filename,file_extension = os.path.splitext(item)
    return int(filename)


def getLatestFile(folder):
    global lastResult
    files = listdir("./"+folder)
    files.sort(key=getNumer)
    if (files[-1]==lastResult):
        return ""
    lastResult = files[-1]
    return "./"+folder+"/"+files[-1]


def printPercentage(msg,percentage):
    print (msg + " "+str(percentage),end='\r')


def RetrieveFile(thread_name,client):
    while True:
        filename = getLatestFile("images")
        if (filename == ""):
            continue
        print (filename)
        if (os.path.isfile(filename)):
            print (filename + "is found")
            filesize=os.path.getsize(filename)
            print (str(os.path.getsize(filename)))
            client.send((filename+","+str(os.path.getsize(filename))+'\n').encode())
            starttime =time.time()
            chunk=filesize
            with open(filename,"rb") as f:
                bytes_to_send=f.read(chunk)
                client.send(bytes_to_send)
                while (bytes_to_send!=b""):
                    bytes_to_send = f.read(chunk)
                    client.send(bytes_to_send)
                print("done in " + str(time.time() - starttime) + " seconds")
                


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



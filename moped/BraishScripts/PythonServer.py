
import socket
import time
import json
import threading
global myG 
global clientConnected

myG= None
global_speed= 0
global_steer = 0

def keepingSpeed():
        global clientConnected
        clientConnected = True
        while clientConnected:
             myDoDriveRef(global_speed,global_steer)
        myDoDriveRef(0,0)
        print ("Connection LOST")

def filter(object,interesting_fields=["height","weight"]):
    data=object.__dict__
    res=dict()
    res["timestamp"]=int(time.time()*1000)
    for key,value in data.items():
        if (key in interesting_fields):
            res[key]=data[key]
    jsonstring = json.dumps(res,ensure_ascii=True)
    return jsonstring

def update(g):
	global myG
	myG = g	



def setupServer(DO_DRIVE):

    global myDoDriveRef
    myDoDriveRef = DO_DRIVE
    # create a socket object
    serversocket = socket.socket(
        socket.AF_INET, socket.SOCK_STREAM)

    # get local machine name
    host = socket.gethostname()
    port = 9999

    # bind to the port
    serversocket.bind(("", port))
	
    print ("Starting sensor server " + host)

    # queue up to 5 requests
    serversocket.listen(5)
    while True:
        print ("listening")
        # establish a connection
        clientsocket, addr = serversocket.accept()	
        keepSpeedThread = threading.Thread(target=keepingSpeed)
        keepSpeedThread.start()
        print("Got a connection from %s" % str(addr))
        try:
            while True:  
                if myG!=None:
                    msg =filter(myG,["inspeed_avg","fodometer","odometer","can_ultra","can_speed","can_steer"])+ "\r\n"
                    clientsocket.send(msg.encode('ascii'))

                msg=clientsocket.recv(1024);
                msg=msg.decode('utf-8')
                msg = json.loads(msg)
                print ("Message form Server "+str(msg))
                global global_speed
                global global_steer
                global_speed=int(msg['Velocity'])
                global_steer=int(msg['Handling'])  
               # print ("Message from Server " +str(msg))
        except:
            print ("Client disconnected")
            ##Call stop moped     
            global clientConnected
            clientConnected=False
            clientsocket.close()


import socket
import time
import json

global myG 
myG= None

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



def setupServer():
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
	
        print("Got a connection from %s" % str(addr))
        try:
            while True:  
            	msg=clientsocket.recv();
            	msg=msg.decode('utf-8')

            	msg = json.loads(msg)

                if myG!=None:
                    msg =filter(myG,["inspeed_avg","fodometer","odometer","can_ultra","can_speed","can_steer"])+ "\r\n"
                    clientsocket.send(msg.encode('ascii'))
        except socket.error as e:
            print ("Client disconnected")
            ##Call stop moped
            clientsocket.close()

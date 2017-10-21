"""This Module handles the server input and output"""
import socket
import time
import json
import threading


class Value:
    """Wrapper class for variables"""

    def __init__(self, init_value):
        """iniittes all"""
        self.value = init_value

    def set_value(self, new_value):
        """A setter method"""
        self.value = new_value

    def get_value(self):
        """A getter method"""
        return self.value


CLIENT_CONNECTED = Value(False)
MY_G = Value(None)
GLOBAL_SPEED = Value(0)
GLOBAL_STEER = Value(0)
__myDoDriveRef__ = Value(None)


def keeping_speed():
    """This function lets us keep the speed when the client is connected"""
    CLIENT_CONNECTED.set_value(True)
    while CLIENT_CONNECTED.get_value():
        __myDoDriveRef__.get_value()(GLOBAL_SPEED.get_value(), GLOBAL_STEER.get_value())
    __myDoDriveRef__.get_value()(0, 0)
    print("Connection LOST")


def filter_object(my_object, interesting_fields=None):
    """"Filters out the data and returns them as a json string"""
    data = my_object.__dict__
    res = dict()
    res["timestamp"] = int(time.time() * 1000)
    for key in data.items():
        if key in interesting_fields:
            res[key] = data[key]
    jsonstring = json.dumps(res, ensure_ascii=True)
    return jsonstring


def update(g_variable):
    """"Updates the g variable that the canbus sends us"""
    MY_G.set_value(g_variable)


def setup_server(do_drive):
    """This function handles setup of server"""
    __myDoDriveRef__.set_value(do_drive)
    # create a socket object
    serversocket = socket.socket(
        socket.AF_INET, socket.SOCK_STREAM)

    # get local machine name
    host = socket.gethostname()
    port = 9999

    # bind to the port
    serversocket.bind(("", port))

    print("Starting sensor server " + host)

    # queue up to 5 requests
    serversocket.listen(5)
    while True:
        print("listening")
        # establish a connection
        clientsocket, addr = serversocket.accept()
        keep_speed_thread = threading.Thread(target=keeping_speed)
        keep_speed_thread.start()
        print("Got a connection from %s" % str(addr))
        try:
            while True:
                if MY_G.get_value() != None:
                    msg = filter_object(MY_G.get_value(),
                                        ["inspeed_avg", "fodometer", "odometer", "can_ultra",
                                         "can_speed",
                                         "can_steer"]) + "\r\n"
                    clientsocket.send(msg.encode('ascii'))

                msg = clientsocket.recv(1024)
                msg = msg.decode('utf-8')
                msg = json.loads(msg)
                GLOBAL_SPEED.set_value(int(msg['Velocity']))
                GLOBAL_STEER.set_value(int(msg['Handling']))
                print("Message from Server " + str(msg))
                time.sleep(0.025)
        except socket.error as err:
            print("Client disconnected")
            print("An exception occured " + str(err))
            # Call stop moped
            CLIENT_CONNECTED.set_value(False)
            clientsocket.close()

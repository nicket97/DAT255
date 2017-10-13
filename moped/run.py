import nav as n
from nav import *
from nav1 import whole4, pause, cont
from driving import stop, drive, steer
import time
import threading
import sys



sys.path.insert(0,"./BraishScripts")
sys.path.insert(1,"./ImagesSendingUnit")
import PythonServer
import FileServer

dataServerThread = threading.Thread(target=PythonServer.setupServer,args=(driving.dodrive,))
imagesServerThread = threading.Thread(target=FileServer.setupImageServer)

init()

dataServerThread.start()
imagesServerThread.start()

#time.sleep(21)
while True:
	PythonServer.update(g)

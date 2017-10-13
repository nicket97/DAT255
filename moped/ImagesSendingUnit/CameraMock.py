import os
import time
import subprocess
import shutil
from picamera import PiCamera
global flag

height = 200 
width = 300
quality = 100
timeout = 1
folder = "./images/"

global counter
counter = 0

def startCapturing():
	global flag
	global counter
	camera = PiCamera()
	camera.resolution=(width,height)
	camera.framerate=30
	flag= True
	while flag:
	    counter+=1
	    filename = str(counter)+".jpg"
	    #print(filename)
	    #subprocess.call(["raspistill","-o",folder+filename,"-w", str(width),"-h",str(height),"-q",str(quality),"-t",str(timeout)]) 
	    camera.capture(folder+filename,use_video_port=True)
	    #print (counter)


def stopCapturing():
	global flag
	flag= False

if __name__=="__main__":
	startCapturing()

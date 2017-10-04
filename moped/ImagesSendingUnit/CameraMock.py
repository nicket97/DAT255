import os
import time
import shutil

folder = "./images/"
counter = 0
while True:
    time.sleep(2)
    counter+=1
    filename = str(counter)+".jpg"


    shutil.copy("samplepic.jpg",folder+filename)
    print ("got "+filename)

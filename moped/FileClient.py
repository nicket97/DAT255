##
# This python script connects to the server socket on the moped and starts getting files sent to it from the moped
# It gets the most recent images captured from the camera and saves them into the folder ./images
# The files sent have the naming convention XXXX.ext, where .ext is the extension of the file (.jpg, .png etc)
# and XXXX is a number, files with higher numeric value of XXXX are more recent.
#
# 11.png is more recent than 7.png
#
# NOTE: The port we're using to recieve files form the MOPED is NOT the same as the one we use to receive sensor data.
##

import socket



def Main():
    #TODO Replace the host address with the actual IP of the MOPED (may vary depending on the network)
    host='192.168.43.183'
    port = 3000

    # Setup a socket and connect to the host socket on the MOPED
    s = socket.socket()
    s.connect((host,port))

    while (True):
        # Recieve the metadata about the file
        # The metadata is comprised of the file's name and size (in KB) separated by a comma
        # ex: img0001.png,13425

        # Read the sent data in binary form
        data = s.recv(1024)

        #Convert the data from binary to String (usually UTF-8)
        data=data.decode()

        # For debugging purpose, showing the filename and its size
        print(data)

        # This exception catching block is intended in case the metadata is corrupted on the way
        try:
            filename,filesize=data.split(",")
            filesize = int(filesize)
            print(filename)
        except Exception:
            continue



        print(filesize)
        # Just a check if the file is not empty (yet another safe switch)
        if (filesize!=0):
            # Create the file and opening it in binary mode
            # wb stands for write binary
            # We use binary mode since the data we're getting is not a series of strings (but an actual image file)
            f=open(filename,"wb")

            #We try and receive the entire file in one chunk
            data = s.recv(filesize)

            # Measure how much data we got
            totalRecv=len(data)

            #Write it to the file we created
            f.write(data)

            # If we still didn't manage to get the entire file in one go, we do it in a while loop
            while (totalRecv<filesize):
                data=s.recv(filesize)
                totalRecv+= len(data)
                f.write(data)

            # Once we're here we know for sure the file is downloaded completely
            print ("Download Completed "+filename)

            # We go ahead and close it
            f.close()


if __name__=="__main__":
    Main()
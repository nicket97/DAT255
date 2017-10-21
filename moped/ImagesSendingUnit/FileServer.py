"""Starts the client so the server can connect and send """
import socket
import time
import threading
import os
from os import listdir
import CameraMock


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


SOCKET = socket.socket()
SOCKET.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

LAST_RESULT = Value("")


def get_number(item):
    """Retrieves the filenumber as an int from the filename"""
    filename = os.path.splitext(item)
    return int(filename)


def delete_files(folder, mes_files):
    """Deletes all the files except the last 2 """
    files = mes_files
    if len(files) >= 2:
        files = files[:-2]
    # Remove all files in the other list
    for file in files:
        os.remove('./' + folder + '/' + file)


def reset_image_folder(folder):
    """Resets the images folder for a fresh start"""
    files = listdir("./" + folder)
    for file in files:
        os.remove('./' + folder + '/' + file)


def get_latest_file(folder):
    """Retrieves the latest file so we can send it"""
    files = listdir("./" + folder)
    files.sort(key=get_number)
    length_of_list = len(files)
    if length_of_list == 0 or length_of_list >= 2 and files[-2] == LAST_RESULT:
        return ""
    if length_of_list >= 2:
        LAST_RESULT.set_value(files[-2])
        delete_files(folder, files)
        return "./" + folder + "/" + files[-2]
    return ""


def analyze_file(filename):
    """Analyzes the file so it can be sent byte by byte"""
    with open(filename, "rb") as file:
        try:
            bytes_read = file.read()
            # while bytes_read:
            #    contents += bytes_read
            #   bytes_read = file.read(chunk)
        finally:
            file.close()

    contents = bytes_read
    size = len(bytes_read)

    print("SIZE:")
    print(size)

    return size, contents


def retrieve_file(client):
    """Retrieves files and sends them to the server as well ,
    as stopping the moped if client disconnects"""
    while True:
        time.sleep(0.025)
        filename = get_latest_file("ImagesSendingUnit/images")
        if filename == "":
            continue
        print(filename)
        try:
            if os.path.isfile(filename):
                print(filename + "is found")
                filesize, contents = analyze_file(filename)
                print(str(filesize))
                client.send((filename + "," + str(filesize) + '\n').encode())
                starttime = time.time()
                client.send(contents)
                print("done in " + str(time.time() - starttime) + " seconds")
            while True:
                resp = client.recv(10)
                resp = resp.decode()
                if "OK" in resp:
                    break
        except socket.error as err:
            client.close()
            print("Exception occurred " + str(err))
            reset_image_folder("ImagesSendingUnit/images")
            CameraMock.stop_capturing()
            print("Client disconnected")
            ##Call stop moped
            return


def setup_image_server():
    """Setups the image server when a client connects to the MOPED"""
    host = ""
    port = 3000
    sock = socket.socket()
    sock.bind((host, port))
    LAST_RESULT.set_value("")
    sock.listen(20)

    print("Image Server started")
    reset_image_folder("ImagesSendingUnit/images/")
    while True:
        clock, addr = sock.accept()
        reset_image_folder("ImagesSendingUnit/images/")
        print("Client connected " + str(addr))
        capturing_image_thread = threading.Thread(target=CameraMock.start_capturin)
        capturing_image_thread.start()
        time.sleep(0.5)
        thread = threading.Thread(target=retrieve_file, args=("retrieveThread", clock))
        thread.start()

    sock.close()


if __name__ == "__main__":
    setup_image_server()

import socket
import os

s= socket.socket()
s.bind(("",3500))

s.listen(6)

print ("Server is up")
while True:
	c,addr = s.accept()
	print ("CLient connected")
	filename = "samplepic.jpg"
	filesize = os.path.getsize(filename)
	c.send(str(filename+","+str(filesize)+'\n').encode())
	with open(filename,"rb") as f:
		bytes_to_send = f.read(filesize)
		c.send(bytes_to_send)
		while (bytes_to_send!=b""):
			bytes_to_send=f.read(filesize)
			c.send(bytes_to_send)
	print ("done")

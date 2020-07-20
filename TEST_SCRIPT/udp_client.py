"""
DOCS: https://docs.python.org/3/library/socket.html
"""
import socket 

UDP_IP = "127.0.0.1"
#UDP_PORT = 5155
UDP_PORT = 4400
#UDP_IP = input("Indirizzo host destinazione: \n")
#UDP_PORT = int(input("Porta UDP: \n"))
MESSAGE = input("Inserisci messaggio da inviare: \n").encode()
 
print("UDP target IP: ", UDP_IP)
print("UDP target port: ", UDP_PORT)
print("message: ", MESSAGE)
 
sock = socket.socket(socket.AF_INET, # Internet
                      socket.SOCK_DGRAM) # UDP

sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))

sock.close()
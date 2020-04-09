import sys
import zmq

context = zmq.Context()

HOST = sys.argv[1] if len(sys.argv) > 1 else "localhost"
PORT = sys.argv[2] if len(sys.argv) > 2 else "50007"

# como e onde conectar
p1 = "tcp://"+ HOST +":"+ PORT 

# criando socket de requisicao
s  = context.socket(zmq.REQ)     

sockets.connect(p1)                   # block until connecteds.send(b"Hello world")           # send messagemessage = s.recv()              # block until responses.send(b"STOP")                  # tell server to stopprint("Reply {}".format(message)) # print result

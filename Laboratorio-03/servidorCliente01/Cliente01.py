import sys
import zmq

context = zmq.Context()

HOST = sys.argv[1] if len(sys.argv) > 1 else "localhost"
PORT = sys.argv[2] if len(sys.argv) > 2 else "50007"

# como e onde conectar
p1 = "tcp://"+ HOST +":"+ PORT 

# criando socket de requisicao
s  = context.socket(zmq.REQ)     

sockets.connect(p1) 
# connect() é usado para conectar-se a um endereço [servidor] remoto.

s.connect(p1)     # bloquear até conectar
s.send(b"Hello world")           # enviando mensagem
message = s.recv()              # bloquear ate responder
s.send(b"STOP")                  # envia "STOP" para encerrar o loop do servidor
print("Reply {}".format(message)) # printa resultado

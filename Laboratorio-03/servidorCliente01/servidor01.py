import zmq

# Criando um contexto ZMQ
contexto = zmq.Context()
# Um contexto ZMQ cria sockets através do metodo ctx.socket.

Host = "*"
PORTA01 = "5007"
PORTA02 = "5008"

# ------- Como e onde conectar -------
p1 = "tcp://" + "Host" + ":" + PORTA01
p2 = "tcp://" + "Host" + ":" + PORTA02
#-------------------------------------

# Criando um socket de resposta do tipo REP
# Outros tipos: REQ,REP,PUB,SUB,PAIR,DEALER,ROUTER,PULL,PUSH.
s = contexto.socket(zmq.REP)

s.bind(p1)
s.bind(p2)
# bind () associa o soquete ao seu endereço local, é por isso que o lado do servidor se liga, para que os clientes possam usar esse endereço para se conectar ao servidor


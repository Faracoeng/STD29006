## Exercício 02

 Desenvolva um aplicativo cliente C e um aplicativo servidor em Java 
 que permitam a transferência de arquivos (de qualquer tamanho) pela rede. 
 O servidor deverá ser capaz deatender múltiplos clientes.  
 Ao subir o servidor deve-se informar a porta e o caminho dodiretório onde 
 estarão os arquivos que serão servidos aos clientes.   
 Ao iniciar o cliente deve-se informar o endereço IP e porta do servidor,
  bem como o nome do arquivo quedeseja transferir. 
   
   Exemplos:
   > Java Servidor 1234 /tmp/repositorio

   > ./cliente 127.0.0.1 1234 /arquivo.txt
## **Projeto prático 1:** Solução IOT

Este projeto foi baseado na implementação de Sockets e broadcast.

#### Cenário:
O projeto é dividido em duas etapas:
* Estabeler conexão de uma máquina com 4 Raspbery PI;
* Desenvolver com o conjunto de Raspberry PI multiplicações de matrizes por blocos.


**OBS:**  Todos os dispositivos desta rede obtém  endereço IP via *DHCP*.

##### **Etapa 1:**
 
###### Servidor
O aplicativo servidor deverá ser executado na Máquina do aluno. Ele
realizará um **broadcast** na rede revelando seu endereço IP para todo 
cliente que estiver "ouvindo" na porta 1234. Logo após, deve aguardar solicitações de conexão TCP 
na porta 4321 vindas de clientes que receberam o **broadcast**. Cada conexão será armazenada e só será executada 
quando quatro clientes (Raspberry) solicitarem a conexão.
 
###### Cliente
É executado em cada uma das Rasps, e tem como objetivo receber o IP do servidor via **broadcast** 
e solicitar conexão TCP para este IP na porta 4321.

##### Execução do primeiro cenário:
Para executar o primeiro cenário basta executar a classe **Servidor.java** na máquina do aluno 
acompanhado no mesmo diretório das classes de auxílio **DisparaIP.java** e **ServidorThread.java**.
>Java Servidor.java
>
>Javac Servidor 
 
Deve-se executar a classe **Cliente.java** em cada Raspberry, o sistema automaticamente 
estabelecerá as conexões entre as Rasps e a máquina do aluno. Mensagens de teste são trocadas entre
servidor e cliente, para simular o cenário 2, que seriam as trocas de matrizes.
>Java Cliente.java
>
>Javac Cliente

**OBSERVAÇÕES:** O sistema pode demorar alguns minutos para estabelecer as conexões, e cada
 aplicativo Cliente deverá ser executada **sequencialmente**, ou seja, nunca ao mesmo tempo. 


##### **Etapa 2:**

Todo cenário 1 é utilizado.

Servidor recebe como argumento de linha de comando informações sobre as matrizes a 
serem trabalhadas no sistema.
> 
>Java Servidor < numeroDeTrabalhadores > < linhasMatrizA > < colunasMatrizA > < linhasMatrizB > < colunasMatrizB > < semente >

Caso nenhum parametro seja inserido por padrão os valores assumidos serão:

> Java Servidor 4 1000 1000 1000 1000 123456         
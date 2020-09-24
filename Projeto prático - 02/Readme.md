## Relatório Projeto Prático 2 : Sistema distribuído de logs

##### Este projeto consiste em sincronizar processos Workers, e registrar todos os eventos processados em cada um em um LOG, que imprime em seu console de forma sincronizada através do uso de relogio vetorial.
##### Os processos worker, só funcionam assim que todas as suas instancias estiverem sido realizadas. Os workers trocam mensagens entre si através de sockets UDP.

### Cenário
#### Inicialmente instancia-se um processo LOG, que trabalhará como um servidor de registro do sistema
#### Este servidor estabelece conexão TCP com todos os processos workers presentes no sistema

### Regras
#### LOG
##### Ao instanciar o ProcessoLOG deve-se inserir como argumentos em linha de comando as seguintes informações:
> ip porta arquivoProcessos  
* **ip**: Endereço ip da máquina onde ProcessoLog estará rodando;
* **porta**: Porta local que será utilizada para estabelecer conexões TCP;
* **arquivoProcessos**: Um *arquivo.txt* contendo nome dos processos que participarão do sistema;
> No *arquivo.txt*, cada processo deverá conter o identificador *'p'* minúsculo seguindo do seu indice de processo, ou seja, caso queira nomear o processo 45 deverá se chamar *"p45"*;
##### Deve-se respeitar uma ordem sequencial numérica para nomear os processos, e cada linha deve representar um único processo, abaixo esta sendo ilustrado um *arquivo.txt* de um sistema contendo 5 processos:

p1

p2 

p3

p4

p5

#### Worker

###### Ao instanciar o ProcessoWorker, deve-se inserir como argumentos em linha de comando as seguintes informações:
> ip porta idUnico sementeUnica tempoMaximo tempoJitter arquivoProcessos arquivoEventos
* **ip**: Endereço ip da máquina onde ProcessoWorker estará rodando;
* **porta**: Porta local que será utilizada para receber mensagem UDP de outros workers;
* **idUnico**: Identificar único de cada processo worker, deve seguir a mesma regra de nomenclatura sugerida no *arquivo.txt* fornecido ao ProcessoLog;
* **sementeUnica**: Um número inteiro para a geração dos números pseudo-aleatórios;
* **tempoMaximo***: Um valor em milissegundos para ser usado como o tempo máximo que o processo ficará aguardando antes de processar o próximo evento;
* **tempoJitter**: Um valor em milissegundos para variação de atraso(jitter) máximo entre o envio de uma mensagem para um processo worker e o envio de uma mensagem para o ProcessoLog;
* **arquivoProcessos**: Um *arquivo.txt* contendo todos os processo envolvidos no sistema, incluindo o LOG;
* **arquivoEventos**: Um *arquivo.txt* contendo todos os eventos que um Worker deverá processar

###### Cada porta fornecida deverá conter pelo menos 2 numeros de distancia da porta de qualquer outro processo Worker.

> O arquivoProcessos deverá respeitar aseguinte estrutura:
* Cada linha representará um processo
* O formato das linha deverá ser:
> ipDoWorker-portaParaReceberUDP-idUnicoDoProcesso
###### Um exemplo de arquivoProcessos envolvendo 2 processos e o LOG esta ilustrado abaixo:
192.168.1.34-1245-p1

1992.168.1.35-1478-p2

192.168.1.36-4321-Log

> O log pode ser descrito como **log**, **LOG** ou **Log**

###### O formato do arquivoEventos deverá representar dois tipo de eventos, local 'L' ou enviar mensagem 'S'. Eles deverão ser separados por ',' da mensagem que será enviada para outro processo, como ilustrado abaixo:

L,MensagemPrimeiralinha

S,MensagemSegundalinha

L,MensagemTerceiralinha

> É importante não separar as mensagens por caracter especial como "-" ou "_"


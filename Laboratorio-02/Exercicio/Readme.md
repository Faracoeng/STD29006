## Sistema de inventário de ativos de Wifi

Desenvolva uma aplicativo cliente / servidor baseado em RMI para fazer o inventário de pontos de acesso (Access Point– AP) WiFi.  Ao cadastrar um AP é necessário informar um nome,MAC address, frequências que opera (2.4Ghz, 5Ghz ou ambas) e em qual sala do prédio esseAP está afixado (por exemplo:  lab.  redes II). O aplicativo servidor é o responsável por manter esse inventário em memória e o aplicativo cliente é responsável por fazer requisições ao servidor para cadastrar, remover ou listar pontos de acesso.  Ao listar os pontos de acesso o cliente poderá indicar critérios de seleção. Exemplos:

* Cadastrar um novo AP na sala lab SiDi e que opere em ambas as frequências
 
> java ClienteInventario < endereco servidor > add "AP1" "mac:abcdef" "freq:2.4:5" "Lab SiDi"

*  Listar todos os APs

> java ClienteInventario < endereco servidor > list

* Listar todos os APs que operam com a frequência de 5Ghz
> java ClienteInventario < endereco servidor > list freq:5
* Remover AP com o identificador AP2
> java ClienteInventario < endereco servidor > del AP2
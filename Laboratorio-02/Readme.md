## Laboratório - 02

> O objetivo deste laboratório: Apresentar como desenvolver aplicações cliente e servidor comONC RPC e com Java RMI.

### Nota
> Na máquina Cliente colocar IP do servidor no intellij em *Edit Configurations > Program arguments: IP PORTA*

#### No servidor é necessário criar o arquivo *(java.policy)* com a seguinte política de segurança na raiz do projeto:

> grant{permission java.security.AllPermission;}

> Na máquina Servidor colocar caminho no intellij em *Edit Configurations > VM OPTIONS: -Djava.security.policy=java.policy*

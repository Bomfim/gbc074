# GBC074 – Sistemas Distribuídos
___
Sistema para acompanhamento de jogos de futebol 

 
É um sistema para torcedores acompanharem o andamento da partida do seu time e visualizar resultados anteriores. 

O sistema tem dois módulos: 

-Jornalista esportivo: jornalista entra no sistema, cria informações sobre a partida em uma lista de partidas em andamento, atualiza e envia para o servidor conforme alguma alteração sobre a partida. Ao final da partida muda o status para encerrada e manda para uma lista de partidas anteriores; 

-Torcedor: o torcedor entra no sistema, escolhe se quer visualizar uma partida em andamento ou encerrada. Caso escolha em andamento pode acompanhar em tempo real o andamento do jogo com base nas informações do jornalista ou pode escolher partidas anteriores e visualizar os resultados. 

Regras: 

-Somente um jornalista pode acompanhar um determinado jogo e atualizar o servidor; 

-Vários jornalistas podem atualizar jogos simultâneos respeitando a regra anterior; 

-Vários clientes podem acompanhar o andamento de todos os jogos; 

-Resultados dos jogos devem ficam disponíveis para visualização posterior.   

 

Ferramentas: 

A aplicação será desenvolvida em Java e utilizará um banco de dados mongodb. 

Testes: 

-Funcionalidades: todas operações de CRUD envolvendo as partidas.

-Teste de concorrência: múltiplos torcedores possam acessar a mesma partida ao mesmo tempo e múltiplos jornalistas possam editar diferentes partidas ao mesmo tempo. 

-Teste de recuperação de falhas: Se o sistema falhar ou o servidor cair as informações estão disponíveis da mesma forma que o estado anterior a falha. 

___

## Modus operandi

Primeiramente clone o repositório, certifique-se de possuir a [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) com o [maven](https://maven.apache.org/download.cgi) instalado e com as váriáveis de ambiente devidamente setadas.

Na raíz do diretório clonado execute:

> mvn clean install

Depois,

    mvn package

E por último para rodar o servidor:

    java -cp '.\Server\target\Server-0.0.1-SNAPSHOT.jar;.\PubSub\target\PubSub-0.0.1-SNAPSHOT.jar' server.Main

E o cliente:
    
    java -cp .\Client\target\Client-0.0.1-SNAPSHOT.jar client.Main


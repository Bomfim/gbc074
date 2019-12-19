# GBC074 – Sistemas Distribuídos
___
Sistema para acompanhamento de jogos de futebol 

 
É um sistema para torcedores acompanharem o andamento da partida do seu time. 

O sistema tem dois módulos: 

-Jornalista esportivo: jornalista entra no sistema, cria informações sobre a partida em uma lista de partidas em andamento, atualiza e envia para o servidor conforme alguma alteração sobre a partida; 

-Torcedor: o torcedor entra no sistema, escolhe qual partida quer acompanhar e pode acompanhar em tempo real o andamento do jogo com base nas informações do jornalista. 

Regras: 

-Somente um jornalista pode acompanhar um determinado jogo e atualizar o servidor; 

-Vários clientes podem acompanhar o andamento de todos os jogos; 

 

Ferramentas: 

A aplicação será desenvolvida em Java e utilizará o padrão Publish-Subscribe. 

Testes: 

-Funcionalidades: todas operações de CRUD envolvendo as partidas.

-Teste de concorrência: múltiplos torcedores possam acessar a mesma partida ao mesmo tempo e múltiplos jornalistas possam editar diferentes partidas ao mesmo tempo. 

-Teste de recuperação de falhas: Se o sistema falhar ou o servidor cair as informações estão disponíveis da mesma forma que o estado anterior a falha. 

___

## Modus operandi

Primeiramente clone o repositório, certifique-se de possuir a [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) com o [maven](https://maven.apache.org/download.cgi) instalado e com as váriáveis de ambiente devidamente setadas.

Na raíz do diretório clonado execute:

    mvn clean install

Servidores"

mvn exec:java -Dexec.mainClass="atomics.server.MatchMachine" -Dexec.args="0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

mvn exec:java -Dexec.mainClass="atomics.server.MatchMachine" -Dexec.args="1 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

mvn exec:java -Dexec.mainClass="atomics.server.MatchMachine" -Dexec.args="2 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

Clientes:

mvn exec:java -Dexec.mainClass="atomics.client.ReporterClient" -Dexec.args="client 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

mvn exec:java -Dexec.mainClass="atomics.client.FanClient" -Dexec.args="client 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

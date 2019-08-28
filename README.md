# GBC074 – Sistemas Distribuídos
___
**Ideia**: O projeto consiste em construir um sistema de cartório com autenticação totalmente digital e distribuída. De certa forma seria uma DLT (do inglês, Distributed Ledger Technology) ou Livro de registros distribuídos. A Blockchain é um tipo de DLT e será implementada nesse caso.

![alt text](https://137938-400316-raikfcquaxqncofqfm.stackpathdns.com/wp-content/uploads/2018/01/blockchain-vs-distributed-ledger.png "Centralized Ledger vs Distributed Ledger")

**Funcionamento**: De um modo geral será divido em 3 etapas, são elas:
1. Criação de um ativo digital e armazenamento (em banco, provavelmente NoSQL) da prova digital (assinatura) para o Blockchain;
2. Transmissão do ativo digital (e-mail, compartilhamento de arquivos, etc.);
3. Validação do ativo digital usando a assinatura armazenada no Blockchain e habilitação da instituição que emitiu o ativo;

**Roteiro de Testes Unitários(RTU)**: 
* Teste de stress com múltiplos clientes (autenticando e validando documentos);
* Teste de validação com autorizações inválidas;
* Testes ACID (Atomicidade, Consistência, Isolamento, Durabilidade) do banco

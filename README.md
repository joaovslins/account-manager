# Gerenciamento de Carteira com Microservices e Docker

Este projeto é uma aplicação de gerenciamento de carteira distribuída, implementada utilizando microservices e Docker. O sistema é composto por duas aplicações principais: **Wallet** e **Transaction**.

## Descrição das Aplicações

### Wallet

A aplicação **Wallet** é responsável pela gestão das carteiras. Suas principais funcionalidades incluem:

- **Criar Carteiras**: Criação de novas carteiras.
- **Desabilitar Carteiras**: Desativar uma carteira existente.
- **Ativar Carteiras**: Reativar uma carteira que estava desativada.
- **Decrementar Saldo**: Reduzir o saldo da carteira.
- **Incrementar Saldo**: Aumentar o saldo da carteira.

### Transaction

A aplicação **Transaction** gerencia e registra todas as transações associadas às carteiras. Suas funcionalidades incluem:

- **Registrar Transações de Depósito**: Adicionar fundos a uma carteira.
- **Registrar Pagamentos**: Realizar pagamentos através da carteira.
- **Registrar Compras**: Registrar compras realizadas com a carteira.
- **Cancelar Saldo**: Cancelar transações e ajustar o saldo da carteira.
- **Registrar Estornos**: Reverter transações realizadas anteriormente.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação utilizada para o desenvolvimento das aplicações.
- **Maven 3.6.3**: Gerenciador de dependências e construção do projeto.
- **PostgreSQL**: Banco de dados utilizado para armazenar as informações.
- **Docker**: Plataforma para criação, distribuição e execução dos containers das aplicações.

## Executando o Projeto

Para executar o projeto, é necessário ter o Docker instalado em seu ambiente. Siga os passos abaixo:

1. **Compilar e Instalar as Aplicações**:

   Navegue para o diretório da aplicação **Wallet** e execute:
   ```bash
   cd wallet
   mvn clean install
   cd ..
   cd transaction
   mvn clean install
   cd .. 
   docker compose up -d

2. **Arquitetura da Aplicação**
   ![Screenshot 2024-07-25 165414](https://github.com/user-attachments/assets/10ebca98-9dd4-4115-be2a-dfc7a334abb6)



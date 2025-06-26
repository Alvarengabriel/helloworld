Aluno: Gabriel Antunes Alvarenga

# API de Clientes e Pedidos

Este é um projeto de API RESTful desenvolvido com Spring Boot que gerencia um CRUD (Criar, Ler, Atualizar, Deletar) completo para duas entidades principais: `Cliente` e `Pedido`.

A API foi projetada seguindo as melhores práticas, incluindo o uso de DTOs (Data Transfer Objects) para desacoplar a camada de API da camada de persistência, tratamento de exceções global e documentação automática com Swagger (OpenAPI).

---

## Tecnologias Utilizadas

- **Java 17**: Versão da linguagem Java utilizada.
- **Spring Boot 3.x**: Framework principal para criação da aplicação.
- **Spring Web**: Para criar os endpoints RESTful.
- **Spring Data JPA**: Para persistência de dados e comunicação com o banco.
- **Hibernate**: Implementação do JPA utilizada pelo Spring.
- **PostgreSQL**: Banco de dados relacional para armazenar os dados.
- **Maven**: Ferramenta de gerenciamento de dependências e build do projeto.
- **Springdoc OpenAPI (Swagger)**: Para documentação automática e interativa da API.

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado em sua máquina:

- **JDK 17** ou superior.
- **PostgreSQL**.

---

## Configuração do Ambiente

### 1. Banco de Dados

A aplicação precisa de um banco de dados e um usuário para se conectar. Você pode criá-los executando os seguintes comandos SQL no seu terminal `psql` ou em uma ferramenta como o pgAdmin:

```sql
-- Crie um usuário (se ainda não tiver um)
CREATE USER postgres WITH PASSWORD 'postgres';

-- Crie o banco de dados
CREATE DATABASE apispring;

-- Dê todas as permissões ao usuário no novo banco
GRANT ALL PRIVILEGES ON DATABASE apispring TO postgres;
```

### 2. Arquivo de Configuração

Verifique se o arquivo `src/main/resources/application.properties` está configurado com as credenciais corretas do seu banco de dados. A configuração padrão é:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/apispring
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## Executando a Aplicação

Você pode executar a aplicação via linha de comando

## Maven Wrapper

Abra um terminal na pasta raiz do projeto (`d:\Lasalle\helloworld`) e execute o comando:

```bash
.\mvnw.cmd spring-boot:run
```

A aplicação estará rodando em `http://localhost:8080/swagger-ui/index.html#/`.

---

## Utilizando a API com Swagger UI

A melhor forma de testar e interagir com a API é através da interface do Swagger, que documenta todos os endpoints de forma interativa.

### 1. Acesso ao Swagger UI

Com a aplicação rodando, abra o seu navegador e acesse a seguinte URL:

**http://localhost:8080/swagger-ui.html**

### 2. Passo a Passo: Exemplo de Fluxo Completo

Siga estes passos para testar o CRUD completo:

#### Passo 1: Criar um Cliente

1.  No Swagger, expanda a seção **ClienteController**.
2.  Clique no endpoint `POST /api/clientes`.
3.  Clique em **"Try it out"**.
4.  No campo `Request body`, insira o JSON para o novo cliente:
    ```json
    {
      "nome": "Gerson",
      "email": "gerson@example.com"
    }
    ```
5.  Clique em **"Execute"**. Você receberá uma resposta `201 Created`. **Anote o `id` do cliente retornado (ex: 1)**.

#### Passo 2: Criar um Pedido para o Cliente

1.  Expanda a seção **PedidoController**.
2.  Clique no endpoint `POST /api/pedidos`.
3.  Clique em **"Try it out"**.
4.  No `Request body`, insira os dados do pedido, usando o `clienteId` que você anotou no passo anterior:
    ```json
    {
      "descricao": "Compra de um teclado mecânico",
      "valorTotal": 350.0,
      "clienteId": 1
    }
    ```
5.  Clique em **"Execute"**. Você receberá uma resposta `201 Created`. **Anote o `id` do pedido retornado**.

#### Passo 3: Atualizar o Pedido

1.  Vá para o endpoint `PUT /api/pedidos/{id}`.
2.  Clique em **"Try it out"**.
3.  No campo `id`, digite o ID do pedido que você acabou de criar.
4.  No `Request body`, altere a descrição ou o valor:
    ```json
    {
      "descricao": "Compra de um teclado mecânico (com desconto)",
      "valorTotal": 320.0,
      "clienteId": 1
    }
    ```
5.  Clique em **"Execute"**. Você receberá uma resposta `200 OK` com os dados atualizados.

#### Passo 4: Deletar o Pedido e o Cliente

1.  Vá para o endpoint `DELETE /api/pedidos/{id}`.
2.  Clique em **"Try it out"**, insira o ID do pedido e clique em **"Execute"**. Você receberá uma resposta `204 No Content`.
3.  Faça o mesmo para o cliente no endpoint `DELETE /api/clientes/{id}`.

---

## Arquitetura e Componentes

A aplicação segue uma arquitetura em camadas clássica, separando as responsabilidades para facilitar a manutenção e o teste.

### 1. Controllers (`ClienteController`, `PedidoController`)

- **Responsabilidade**: A camada de entrada da API. É responsável por receber as requisições HTTP, interpretar as URLs e os parâmetros, e invocar a lógica de negócio apropriada.
- **Como funciona**:
  - `@RestController`: Marca a classe como um controller que lida com requisições REST.
  - `@RequestMapping("/api/clientes")`: Define a URL base para todos os endpoints dentro da classe.
  - `@GetMapping`, `@PostMapping`, etc.: Mapeiam os métodos HTTP (GET, POST, PUT, DELETE) para métodos Java específicos.
  - `@RequestBody`: Converte o corpo JSON da requisição em um objeto Java (um `RequestDTO`).
  - **Delegação**: O controller não contém lógica de negócio. Ele delega a execução para a camada de `Service`.
  - **Resposta**: Após receber o resultado do serviço, o controller constrói a resposta HTTP (`ResponseEntity`), definindo o status (ex: `200 OK`, `201 Created`) e convertendo as entidades de volta para `ResponseDTOs` antes de enviá-las como JSON.

### 2. Services (`ClienteService`, `PedidoService`)

- **Responsabilidade**: O "cérebro" da aplicação. Contém toda a lógica de negócio.
- **Como funciona**:
  - `@Service`: Marca a classe como um componente de serviço do Spring.
  - **Lógica de Negócio**: Implementa as regras de negócio. Por exemplo, para criar um pedido, o `PedidoService` primeiro verifica se o cliente associado existe antes de criar o pedido.
  - **Orquestração**: Um serviço pode interagir com um ou mais `Repositories` para buscar ou salvar dados no banco.
  - **Transações**: O Spring gerencia as transações automaticamente nos métodos de serviço. Se um erro ocorrer, a transação é revertida (rollback).

### 3. Repositories (`ClienteRepository`, `PedidoRepository`)

- **Responsabilidade**: A camada de acesso a dados (Data Access Layer). É a única camada que se comunica diretamente com o banco de dados.
- **Como funciona**:
  - São interfaces que estendem `JpaRepository<Entidade, TipoDoId>`.
  - **Magia do Spring Data JPA**: Ao estender `JpaRepository`, você ganha automaticamente métodos de CRUD prontos (`save()`, `findById()`, `findAll()`, `deleteById()`, etc.) sem precisar escrever nenhuma implementação ou SQL.
  - **Abstração**: O repositório abstrai a complexidade do acesso ao banco. O serviço simplesmente chama `clienteRepository.save(cliente)` sem se preocupar com os detalhes do SQL.

### 4. DTOs (Data Transfer Objects)

- **Responsabilidade**: Objetos simples que carregam dados entre as camadas, especialmente entre o cliente (navegador/outra API) e o servidor. Eles são cruciais para a segurança e o design da API.
- **Como funciona**:
  - **`RequestDTO` (`ClienteRequestDTO`, `PedidoRequestDTO`)**:
    - **Propósito**: Define a "forma" dos dados que a API **recebe**.
    - **Vantagens**: Permite que você especifique exatamente quais campos o cliente pode enviar (ex: para criar um cliente, só é necessário `nome` e `email`, não um `id` ou uma lista de pedidos). Também é onde se colocam as anotações de validação (`@NotBlank`, `@Email`).
  - **`ResponseDTO` (`ClienteResponseDTO`, `PedidoResponseDTO`)**:
    - **Propósito**: Define a "forma" dos dados que a API **envia de volta**.
    - **Vantagens**: Permite controlar quais campos são expostos ao cliente, escondendo dados sensíveis ou internos. É a solução para o **problema da recursão infinita**, pois o `ClienteResponseDTO` não inclui a lista de pedidos, quebrando o ciclo `Pedido -> Cliente -> Pedido`.

### 5. GlobalExceptionHandler

- **Responsabilidade**: Centralizar o tratamento de exceções para toda a aplicação, garantindo que os erros sejam retornados de forma consistente e profissional.
- **Como funciona**:
  - `@ControllerAdvice`: Transforma a classe em um interceptador global que "ouve" as exceções lançadas por qualquer `@Controller`.
  - `@ExceptionHandler(EntityNotFoundException.class)`: Define que o método `handleEntityNotFoundException` será executado **especificamente** quando uma `EntityNotFoundException` for lançada (seja no `ClienteService` ou no `PedidoService`).
  - **Padronização**: Em vez de a aplicação quebrar com um erro `500 Internal Server Error`, este handler captura a exceção e a transforma em uma resposta `404 Not Found` com um corpo JSON claro e informativo, melhorando a experiência de quem consome a API.
# Demo DAO JDBC

Projeto em Java para praticar acesso a banco de dados relacional com **JDBC puro**, aplicando o **padrão DAO (Data Access Object)**. O domínio modelado é simples: **vendedores (Seller)** que pertencem a **departamentos (Department)**.

## Objetivos do projeto

- Praticar a API JDBC (`Connection`, `PreparedStatement`, `ResultSet`) sem frameworks.
- Separar a regra de acesso a dados da aplicação com o padrão DAO.
- Tratar exceções de forma personalizada e fechar recursos corretamente.
- Usar *Factory* para instanciar os DAOs sem expor a implementação.

## Tecnologias

- Java (JDK 11 ou superior)
- JDBC
- MySQL 8+
- MySQL Connector/J
- Eclipse IDE (pode ser aberto em qualquer IDE Java)

## Estrutura do projeto

```
src/
├── application/
│   └── Program.java            # Classe principal com os testes das operações
├── db/
│   ├── DB.java                 # Conexão e fechamento de recursos
│   └── DbException.java        # Exceção personalizada de banco
├── model/
│   ├── entities/
│   │   ├── Department.java
│   │   └── Seller.java
│   └── dao/
│       ├── DaoFactory.java     # Fábrica de DAOs
│       ├── SellerDao.java      # Interface
│       └── impl/
│           └── SellerDaoJDBC.java   # Implementação JDBC
db.properties                   # Configuração de acesso ao banco
```

## Funcionalidades

| Operação                  | Status         |
|---------------------------|----------------|
| Buscar vendedor por id    | Implementado   |
| Inserir vendedor          | Implementado   |
| Atualizar vendedor        | Implementado   |
| Deletar vendedor por id   | Em andamento   |
| Listar vendedores         | Em andamento   |

## Como executar

### 1. Pré-requisitos

- JDK 11+ instalado
- MySQL instalado e em execução
- Git

### 2. Clonar o repositório

```bash
git clone https://github.com/SEU-USUARIO/demo-dao-jdbc.git
cd demo-dao-jdbc
```

### 3. Criar o banco de dados

Execute o script abaixo no MySQL (ajuste o nome do banco se preferir):

```sql
CREATE DATABASE coursejdbc;
USE coursejdbc;

CREATE TABLE department (
  Id INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE seller (
  Id INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(60) NOT NULL,
  Email VARCHAR(100) NOT NULL,
  BirthDate DATE NOT NULL,
  BaseSalary DOUBLE NOT NULL,
  DepartmentId INT NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (DepartmentId) REFERENCES department (Id)
);

INSERT INTO department (Name) VALUES
  ('Computers'), ('Electronics'), ('Fashion'), ('Books');

INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES
  ('Bob Brown', 'bob@gmail.com', '1998-04-21', 1000, 1),
  ('Maria Green', 'maria@gmail.com', '1979-12-31', 3500, 2),
  ('Alex Grey', 'alex@gmail.com', '1988-01-15', 2200, 1),
  ('Martha Red', 'martha@gmail.com', '1993-11-30', 3000, 4),
  ('Donald Blue', 'donald@gmail.com', '2000-01-09', 4000, 3),
  ('Alex Pink', 'bob@gmail.com', '1997-03-04', 3000, 2);
```

### 4. Configurar o acesso ao banco

Crie (ou edite) o arquivo `db.properties` na raiz do projeto:

```properties
user=SEU_USUARIO
password=SUA_SENHA
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

> Não versione suas credenciais reais. Se publicar o projeto, adicione o `db.properties` ao `.gitignore` e mantenha um arquivo `db.properties.example` com valores fictícios.

### 5. Adicionar o driver JDBC

Baixe o **MySQL Connector/J** em <https://dev.mysql.com/downloads/connector/j/> e adicione o `.jar` ao *build path* do projeto.

No Eclipse: botão direito no projeto → **Build Path** → **Add External Archives...** → selecione o `.jar`.

### 6. Rodar

Abra a classe `application.Program` e execute como **Java Application**.

Ou, pela linha de comando (a partir da raiz do projeto):

```bash
javac -cp "lib/*" -d bin $(find src -name "*.java")
java -cp "bin:lib/*" application.Program
```

> No Windows, troque `:` por `;` no classpath.

## Exemplo de uso

```java
SellerDao sellerDao = DaoFactory.createSellerDao();

System.out.println("=== TEST 1: seller findById ===");
Seller seller = sellerDao.findById(3);
System.out.println(seller);

System.out.println("=== TEST 2: seller update ===");
seller = sellerDao.findById(1);
seller.setName("Martha Waine");
sellerDao.update(seller);
System.out.println("Update completed");

DB.closeConnection();
```

## Pontos de atenção

- A conexão é aberta uma vez e **fechada apenas no final do `Program`**. Os métodos do DAO fecham somente o `PreparedStatement` e o `ResultSet` que criam.
- O erro `No operations allowed after connection closed` indica que algum método fechou a conexão antes de outra operação.

## Aprendizados

- Uso de `PreparedStatement` para evitar SQL Injection.
- Mapeamento de `ResultSet` para objetos (`instantiateSeller` e `instantiateDepartment`).
- Associação entre entidades (`Seller` → `Department`) com `INNER JOIN`.
- Gerenciamento de recursos e tratamento de exceções em JDBC.

## Autor

**Rafael Alves Neves da Silva*
[LinkedIn](https://www.linkedin.com/in/rafael-alves-neves) 

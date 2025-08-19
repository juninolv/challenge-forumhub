# ForumHub

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=Apache-Maven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

---

## Tecnologias Utilizadas

- **Java 21**
- **Maven**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Gutendex API**

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:
- [Java 21](https://adoptium.net/pt-BR/temurin/releases?version=21&os=any&arch=any)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://www.mysql.com//)

---

## Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone git@github.com:juninolv/challenge-forumhub.git
   cd challenge-forumhub
   ```

2. **Configure o banco de dados:**
- Crie um banco de dados MySQL chamado `forum-hub`.
- Crie um arquivo `src/main/resources/application-dev.properties` e adicione as propriedades:
  ```properties
  db.database=forum-hub
  db.username=mysql
  db.passwd=12345
  ```

3. **Execute a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse a aplicação:**
    - A aplicação é executada no terminal e apresenta um menu interativo.

---

## Contato
**Juninho Oliveira** – [GitHub](https://github.com/juninolv) | [LinkedIn](https://www.linkedin.com/in/juninholv/)

---
**Star este repositório** se achou útil!
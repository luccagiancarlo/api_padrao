# Setup do Projeto - API Padrão UEM

## 📋 Pré-requisitos

- Java 21
- Maven 3.x
- Acesso ao banco de dados DB2
- Git

## 🔧 Configuração Inicial

### 1. Clone o Repositório

```bash
git clone https://github.com/luccagiancarlo/api_padrao.git
cd api_padrao
```

### 2. Configure o Banco de Dados

**Copie o arquivo de exemplo:**
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

**Edite o arquivo `application.properties` com suas credenciais:**
```properties
spring.application.name=api_padrao
spring.datasource.url=jdbc:db2://SEU_HOST:50000/SEU_DATABASE
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=com.ibm.db2.jcc.DB2Driver

# Configurações JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.DB2Dialect
```

⚠️ **IMPORTANTE:** O arquivo `application.properties` está no `.gitignore` e **NÃO** deve ser commitado!

### 3. Compile o Projeto

```bash
./mvnw clean install
```

### 4. Execute a Aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 🧪 Teste a API

### Health Check
```bash
curl http://localhost:8080/api_dw/v1/health
```

### Autenticação
```bash
curl -X POST http://localhost:8080/api_dw/v1/autenticar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "seu-email@uem.br",
    "senha": "sua-senha"
  }'
```

## 📚 Documentação

- **Autenticação:** [AUTENTICACAO.md](AUTENTICACAO.md)
- **API de Usuários:** [API_USUARIOS.md](API_USUARIOS.md)
- **Arquitetura:** [CLAUDE.md](CLAUDE.md)

## 🔐 Segurança

### Arquivos com Credenciais

Os seguintes arquivos **NÃO devem** ser commitados:
- `src/main/resources/application.properties`

### Arquivos de Exemplo (podem ser commitados)

- `src/main/resources/application.properties.example`

## ⚠️ Problemas Comuns

### Erro de conexão com banco de dados

**Sintoma:**
```
Cannot create PoolableConnectionFactory
```

**Solução:**
1. Verifique se o DB2 está rodando
2. Confirme host, porta, database, usuário e senha no `application.properties`
3. Teste a conexão manualmente

### Porta 8080 já em uso

**Solução:** Adicione ao `application.properties`:
```properties
server.port=8081
```

## 🛠️ Desenvolvimento

### Estrutura do Projeto

```
src/main/java/br/uem/vestibular/api_padrao/
├── controller/          # Endpoints REST
├── dto/                # Data Transfer Objects
├── jpa/                # Entidades e Repositórios
├── service/            # Lógica de Negócio
└── utils/              # Utilitários (JWT, Security)
```

### Comandos Úteis

**Compilar sem executar testes:**
```bash
./mvnw clean compile -DskipTests
```

**Executar testes:**
```bash
./mvnw test
```

**Gerar JAR:**
```bash
./mvnw package
```

**Executar JAR:**
```bash
java -jar target/api_padrao-0.0.1-SNAPSHOT.jar
```

## 📝 Variáveis de Ambiente (Opcional)

Você pode usar variáveis de ambiente ao invés de editar o `application.properties`:

```bash
export DB_HOST=212.85.20.149
export DB_PORT=50000
export DB_NAME=producao
export DB_USER=sgv
export DB_PASS='sua-senha'

./mvnw spring-boot:run
```

E no `application.properties`:
```properties
spring.datasource.url=jdbc:db2://${DB_HOST:localhost}:${DB_PORT:50000}/${DB_NAME:database}
spring.datasource.username=${DB_USER:username}
spring.datasource.password=${DB_PASS:password}
```

## 🐳 Docker (Futuro)

Para facilitar o setup, considere criar um `docker-compose.yml` para o ambiente de desenvolvimento.

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique a documentação em `/docs`
2. Consulte os arquivos `.md` na raiz do projeto
3. Abra uma issue no GitHub

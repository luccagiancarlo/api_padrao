# API de Usuários - Documentação

## 📋 Índice
- [Autenticação](#autenticação)
- [Endpoints de Usuários](#endpoints-de-usuários)
- [Exemplos com cURL](#exemplos-com-curl)
- [Exemplos com Postman/Insomnia](#exemplos-com-postmaninsomnia)

---

## 🔐 Autenticação

Todos os endpoints de usuários **requerem autenticação** via token JWT no header `Authorization`.

### 1. Obter Token JWT

**Endpoint:** `POST /api_dw/v1/autenticar`

**Request:**
```json
{
  "email": "glucca@uem.br",
  "senha": "473510"
}
```

**Response (Sucesso):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

⚠️ **Importante:** Use este token em todos os requests subsequentes no header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 📍 Endpoints de Usuários

Base URL: `http://localhost:8080/api_dw/v1/usuarios`

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/` | Lista todos os usuários | ✅ Requerida |
| GET | `/codigo/{cdUsuario}` | Busca usuário por código | ✅ Requerida |
| GET | `/email/{email}` | Busca usuário por email | ✅ Requerida |
| POST | `/` | Cria novo usuário | ✅ Requerida |
| PUT | `/{cdUsuario}` | Atualiza usuário existente | ✅ Requerida |
| DELETE | `/{cdUsuario}` | Remove usuário | ✅ Requerida |

---

## 🔍 Detalhamento dos Endpoints

### 1. GET - Listar Todos os Usuários

**Endpoint:** `GET /api_dw/v1/usuarios`

**Headers:**
```
Authorization: Bearer {seu-token-jwt}
```

**Response (200 OK):**
```json
[
  {
    "cdUsuario": 1001,
    "enEmail": "joao@uem.br",
    "nmUsuario": "João Silva",
    "seUsuario": "senha123",
    "cdGestor": 100,
    "cdSetor": "42",
    "tpUsuario": "ADMIN",
    "flInventario": "S",
    "flRespsetor": "N",
    "nuMatricula": 2024001,
    "dePortaria": "PORT-001",
    "deCargo": "Analista de Sistemas",
    "tpDas": "DAS-3",
    "tpComissao": "CC-2"
  },
  {
    "cdUsuario": 1002,
    "enEmail": "maria@uem.br",
    "nmUsuario": "Maria Santos",
    ...
  }
]
```

---

### 2. GET - Buscar Usuário por Código

**Endpoint:** `GET /api_dw/v1/usuarios/codigo/{cdUsuario}`

**Exemplo:** `GET /api_dw/v1/usuarios/codigo/1001`

**Headers:**
```
Authorization: Bearer {seu-token-jwt}
```

**Response (200 OK):**
```json
{
  "cdUsuario": 1001,
  "enEmail": "joao@uem.br",
  "nmUsuario": "João Silva",
  "seUsuario": "senha123",
  "cdGestor": 100,
  "cdSetor": "42",
  "tpUsuario": "ADMIN",
  "flInventario": "S",
  "flRespsetor": "N",
  "nuMatricula": 2024001,
  "dePortaria": "PORT-001",
  "deCargo": "Analista de Sistemas",
  "tpDas": "DAS-3",
  "tpComissao": "CC-2"
}
```

**Response (404 Not Found):**
```json
{
  "mensagem": "Usuário não encontrado com código: 1001"
}
```

---

### 3. GET - Buscar Usuário por Email

**Endpoint:** `GET /api_dw/v1/usuarios/email/{email}`

**Exemplo:** `GET /api_dw/v1/usuarios/email/joao@uem.br`

**Headers:**
```
Authorization: Bearer {seu-token-jwt}
```

**Response (200 OK):**
```json
{
  "cdUsuario": 1001,
  "enEmail": "joao@uem.br",
  "nmUsuario": "João Silva",
  ...
}
```

**Response (404 Not Found):**
```json
{
  "mensagem": "Usuário não encontrado com email: joao@uem.br"
}
```

---

### 4. POST - Criar Novo Usuário

**Endpoint:** `POST /api_dw/v1/usuarios`

**Headers:**
```
Authorization: Bearer {seu-token-jwt}
Content-Type: application/json
```

**Request Body:**
```json
{
  "cdUsuario": 1003,
  "enEmail": "novo@uem.br",
  "nmUsuario": "Novo Usuário",
  "seUsuario": "senha123",
  "cdGestor": 100,
  "cdSetor": "42",
  "tpUsuario": "COMUM",
  "flInventario": "N",
  "flRespsetor": "N",
  "nuMatricula": 2024002,
  "dePortaria": "PORT-002",
  "deCargo": "Assistente",
  "tpDas": null,
  "tpComissao": null
}
```

**Campos Obrigatórios:**
- `cdUsuario` - Código único do usuário
- `enEmail` - Email (único)
- `nmUsuario` - Nome completo
- `seUsuario` - Senha (texto plano)

**Campos Opcionais:**
- `cdGestor`, `cdSetor`, `tpUsuario`, `flInventario`, `flRespsetor`, `nuMatricula`, `dePortaria`, `deCargo`, `tpDas`, `tpComissao`

**Response (201 Created):**
```json
{
  "mensagem": "Usuário criado com sucesso!",
  "usuario": {
    "cdUsuario": 1003,
    "enEmail": "novo@uem.br",
    "nmUsuario": "Novo Usuário",
    ...
  }
}
```

**Response (400 Bad Request - Email já existe):**
```json
{
  "mensagem": "Já existe um usuário com o email: novo@uem.br"
}
```

**Response (400 Bad Request - Código já existe):**
```json
{
  "mensagem": "Já existe um usuário com o código: 1003"
}
```

---

### 5. PUT - Atualizar Usuário Existente

**Endpoint:** `PUT /api_dw/v1/usuarios/{cdUsuario}`

**Exemplo:** `PUT /api_dw/v1/usuarios/1003`

**Headers:**
```
Authorization: Bearer {seu-token-jwt}
Content-Type: application/json
```

**Request Body (atualização parcial):**
```json
{
  "nmUsuario": "Novo Nome Atualizado",
  "deCargo": "Analista Sênior",
  "tpUsuario": "ADMIN"
}
```

⚠️ **Nota:** Você pode enviar apenas os campos que deseja atualizar. Campos não informados permanecerão inalterados.

**Response (200 OK):**
```json
{
  "mensagem": "Usuário atualizado com sucesso!",
  "usuario": {
    "cdUsuario": 1003,
    "enEmail": "novo@uem.br",
    "nmUsuario": "Novo Nome Atualizado",
    "seUsuario": "senha123",
    "cdGestor": 100,
    "cdSetor": "42",
    "tpUsuario": "ADMIN",
    "flInventario": "N",
    "flRespsetor": "N",
    "nuMatricula": 2024002,
    "dePortaria": "PORT-002",
    "deCargo": "Analista Sênior",
    "tpDas": null,
    "tpComissao": null
  }
}
```

**Response (404 Not Found):**
```json
{
  "mensagem": "Usuário não encontrado com código: 1003"
}
```

---

### 6. DELETE - Remover Usuário

**Endpoint:** `DELETE /api_dw/v1/usuarios/{cdUsuario}`

**Exemplo:** `DELETE /api_dw/v1/usuarios/1003`

**Headers:**
```
Authorization: Bearer {seu-token-jwt}
```

**Response (200 OK):**
```json
{
  "mensagem": "Usuário deletado com sucesso!",
  "cdUsuario": "1003"
}
```

**Response (404 Not Found):**
```json
{
  "mensagem": "Usuário não encontrado com código: 1003"
}
```

---

## 💻 Exemplos com cURL

### 1. Obter Token de Autenticação

```bash
curl -X POST http://localhost:8080/api_dw/v1/autenticar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "glucca@uem.br",
    "senha": "473510"
  }'
```

**Salve o token retornado em uma variável:**
```bash
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

### 2. Listar Todos os Usuários

```bash
curl -X GET http://localhost:8080/api_dw/v1/usuarios \
  -H "Authorization: Bearer $TOKEN"
```

---

### 3. Buscar Usuário por Código

```bash
curl -X GET http://localhost:8080/api_dw/v1/usuarios/codigo/1001 \
  -H "Authorization: Bearer $TOKEN"
```

---

### 4. Buscar Usuário por Email

```bash
curl -X GET "http://localhost:8080/api_dw/v1/usuarios/email/glucca@uem.br" \
  -H "Authorization: Bearer $TOKEN"
```

---

### 5. Criar Novo Usuário

```bash
curl -X POST http://localhost:8080/api_dw/v1/usuarios \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "cdUsuario": 1003,
    "enEmail": "novo@uem.br",
    "nmUsuario": "Novo Usuário",
    "seUsuario": "senha123",
    "cdSetor": "42",
    "tpUsuario": "COMUM",
    "deCargo": "Assistente"
  }'
```

---

### 6. Atualizar Usuário

```bash
curl -X PUT http://localhost:8080/api_dw/v1/usuarios/1003 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nmUsuario": "Nome Atualizado",
    "deCargo": "Analista Sênior"
  }'
```

---

### 7. Deletar Usuário

```bash
curl -X DELETE http://localhost:8080/api_dw/v1/usuarios/1003 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📮 Exemplos com Postman/Insomnia

### Configuração Inicial

1. **Criar variável de ambiente para o token:**
   - Nome: `jwt_token`
   - Valor inicial: (vazio)

2. **Após fazer login, salvar o token automaticamente:**
   - No endpoint de autenticação, adicione script de resposta:
   ```javascript
   // Postman
   var jsonData = pm.response.json();
   pm.environment.set("jwt_token", jsonData.token);
   ```

3. **Usar o token nos outros endpoints:**
   - Header: `Authorization`
   - Value: `Bearer {{jwt_token}}`

---

### Collection de Requests

#### 1. POST - Autenticar
```
POST http://localhost:8080/api_dw/v1/autenticar
Content-Type: application/json

Body (raw JSON):
{
  "email": "glucca@uem.br",
  "senha": "473510"
}
```

#### 2. GET - Listar Todos
```
GET http://localhost:8080/api_dw/v1/usuarios
Authorization: Bearer {{jwt_token}}
```

#### 3. GET - Buscar por Código
```
GET http://localhost:8080/api_dw/v1/usuarios/codigo/1001
Authorization: Bearer {{jwt_token}}
```

#### 4. GET - Buscar por Email
```
GET http://localhost:8080/api_dw/v1/usuarios/email/glucca@uem.br
Authorization: Bearer {{jwt_token}}
```

#### 5. POST - Criar Usuário
```
POST http://localhost:8080/api_dw/v1/usuarios
Authorization: Bearer {{jwt_token}}
Content-Type: application/json

Body (raw JSON):
{
  "cdUsuario": 1003,
  "enEmail": "novo@uem.br",
  "nmUsuario": "Novo Usuário",
  "seUsuario": "senha123",
  "cdSetor": "42",
  "tpUsuario": "COMUM",
  "deCargo": "Assistente"
}
```

#### 6. PUT - Atualizar Usuário
```
PUT http://localhost:8080/api_dw/v1/usuarios/1003
Authorization: Bearer {{jwt_token}}
Content-Type: application/json

Body (raw JSON):
{
  "nmUsuario": "Nome Atualizado",
  "deCargo": "Analista Sênior"
}
```

#### 7. DELETE - Remover Usuário
```
DELETE http://localhost:8080/api_dw/v1/usuarios/1003
Authorization: Bearer {{jwt_token}}
```

---

## 🔒 Segurança

### Tratamento de Erros

**401 Unauthorized - Token inválido ou expirado:**
```json
{
  "mensagem": "Token inválido."
}
```

**401 Unauthorized - Usuário não autenticado:**
```json
{
  "mensagem": "Usuário não autenticado."
}
```

**403 Forbidden - Sem token:**
```
Forbidden
```

**500 Internal Server Error:**
```json
{
  "mensagem": "Erro ao buscar usuário: [detalhes do erro]"
}
```

---

## ⚠️ Observações Importantes

1. **Senhas em Texto Plano:**
   - Este projeto usa senhas em texto plano para fins didáticos
   - Em produção, SEMPRE use criptografia (BCrypt, Argon2, etc.)

2. **Token JWT:**
   - Válido por 10 horas após o login
   - Após expirar, faça login novamente para obter novo token

3. **Validação:**
   - Email deve ser único
   - Código do usuário (cdUsuario) deve ser único
   - Campos obrigatórios devem ser fornecidos na criação

4. **Atualização Parcial:**
   - No PUT, você pode enviar apenas os campos que deseja atualizar
   - Campos não enviados mantêm seus valores atuais

5. **Deleção:**
   - A deleção é permanente
   - Não há confirmação adicional

---

## 🧪 Fluxo de Teste Completo

```bash
# 1. Fazer login e obter token
TOKEN=$(curl -s -X POST http://localhost:8080/api_dw/v1/autenticar \
  -H "Content-Type: application/json" \
  -d '{"email": "glucca@uem.br", "senha": "473510"}' \
  | jq -r '.token')

echo "Token obtido: $TOKEN"

# 2. Criar novo usuário
curl -X POST http://localhost:8080/api_dw/v1/usuarios \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "cdUsuario": 9999,
    "enEmail": "teste@uem.br",
    "nmUsuario": "Usuário de Teste",
    "seUsuario": "senha123",
    "tpUsuario": "TESTE"
  }'

# 3. Buscar usuário criado
curl -X GET http://localhost:8080/api_dw/v1/usuarios/codigo/9999 \
  -H "Authorization: Bearer $TOKEN"

# 4. Atualizar usuário
curl -X PUT http://localhost:8080/api_dw/v1/usuarios/9999 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"nmUsuario": "Nome Atualizado"}'

# 5. Deletar usuário
curl -X DELETE http://localhost:8080/api_dw/v1/usuarios/9999 \
  -H "Authorization: Bearer $TOKEN"

# 6. Verificar que foi deletado (deve retornar 404)
curl -X GET http://localhost:8080/api_dw/v1/usuarios/codigo/9999 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📚 Referências

- Documentação de Autenticação: `AUTENTICACAO.md`
- Documentação Geral do Projeto: `CLAUDE.md`
- Código Fonte: `src/main/java/br/uem/vestibular/api_padrao/controller/UsuarioController.java`

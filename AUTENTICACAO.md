# Documentação Didática - Módulo de Autenticação Spring Boot

## 📚 Índice
1. [Visão Geral](#visão-geral)
2. [Estrutura do Banco de Dados](#estrutura-do-banco-de-dados)
3. [Componentes do Sistema](#componentes-do-sistema)
4. [Fluxo de Autenticação Completo](#fluxo-de-autenticação-completo)
5. [Conceitos do Spring Security](#conceitos-do-spring-security)
6. [Exemplos Práticos](#exemplos-práticos)

---

## 🎯 Visão Geral

Este projeto implementa **autenticação JWT (JSON Web Token)** no Spring Boot com **senhas em texto plano** (sem criptografia).

⚠️ **IMPORTANTE**: Este projeto usa senhas em texto plano para fins didáticos. Em produção, SEMPRE use criptografia (BCrypt, Argon2, etc.).

**Tipos de autenticação:**
1. **Autenticação Web** (`/api_dw/v1/autenticar`) - Retorna apenas o token JWT
2. **Autenticação App** (`/api_dw/v1/autenticar_app`) - Retorna token + dados do usuário + permissões
3. **Autenticação DW** (`/api_dw/v1/autenticar_dw`) - Específica para Data Warehouse

**Características principais:**
- Stateless (sem sessão no servidor)
- JWT com expiração de 10 horas
- Senhas em texto plano (comparação direta)
- Integração com API externa (Lucca Software)
- Usuário admin hard-coded para testes

---

## 🗄️ Estrutura do Banco de Dados

### View: `SGV.SGV_USUARIO`

Esta é a **única view** utilizada para autenticação no sistema.

```sql
CREATE VIEW SGV.SGV_USUARIO (
    CD_USUARIO    INTEGER      NOT NULL PRIMARY KEY,  -- Código único do usuário
    EN_EMAIL      VARCHAR(50)  NOT NULL,              -- Email (usado como username)
    NM_USUARIO    VARCHAR(50)  NOT NULL,              -- Nome do usuário
    SE_USUARIO    VARCHAR(100) NOT NULL,              -- Senha em TEXTO PLANO
    CD_GESTOR     SMALLINT,                           -- Código do gestor
    CD_SETOR      VARCHAR(15),                        -- Código do setor
    TP_USUARIO    VARCHAR(50),                        -- Tipo de usuário
    FL_INVENTARIO CHARACTER(1),                       -- Flag inventário (S/N)
    FL_RESPSETOR  CHARACTER(1),                       -- Flag responsável setor (S/N)
    NU_MATRICULA  INTEGER,                            -- Número de matrícula
    DE_PORTARIA   VARCHAR(20),                        -- Descrição da portaria
    DE_CARGO      VARCHAR(200),                       -- Descrição do cargo
    TP_DAS        VARCHAR(10),                        -- Tipo DAS
    TP_COMISSAO   VARCHAR(10)                         -- Tipo de comissão
);
```

#### Campos Detalhados:

| Campo | Tipo | Descrição | Exemplo | Usado na Autenticação |
|-------|------|-----------|---------|----------------------|
| `CD_USUARIO` | INTEGER | **PK**: Código único do usuário | 1001 | ✅ Identificação |
| `EN_EMAIL` | VARCHAR(50) | Email do usuário (username para login) | "joao@uem.br" | ✅ Login |
| `NM_USUARIO` | VARCHAR(50) | Nome completo do usuário | "João Silva" | ✅ Retorno |
| `SE_USUARIO` | VARCHAR(100) | **Senha em texto plano** (sem hash) | "senha123" | ✅ Validação |
| `CD_GESTOR` | SMALLINT | Código do gestor do usuário | 100 | ❌ |
| `CD_SETOR` | VARCHAR(15) | Código do setor | "42" | ✅ Retorno App |
| `TP_USUARIO` | VARCHAR(50) | Tipo/perfil do usuário | "ADMIN" | ✅ Retorno |
| `FL_INVENTARIO` | CHAR(1) | Permissão de inventário | "S" | ❌ |
| `FL_RESPSETOR` | CHAR(1) | Flag de responsável do setor | "N" | ❌ |
| `NU_MATRICULA` | INTEGER | Número de matrícula | 2024001 | ❌ |
| `DE_PORTARIA` | VARCHAR(20) | Descrição da portaria | "PORT-001" | ❌ |
| `DE_CARGO` | VARCHAR(200) | Cargo do usuário | "Analista de Sistemas" | ✅ Retorno |
| `TP_DAS` | VARCHAR(10) | Tipo DAS | "DAS-3" | ❌ |
| `TP_COMISSAO` | VARCHAR(10) | Tipo de comissão | "CC-2" | ❌ |

#### 📋 Exemplo de Registro:

```sql
INSERT INTO SGV.SGV_USUARIO (
    CD_USUARIO, EN_EMAIL, NM_USUARIO, SE_USUARIO,
    CD_SETOR, TP_USUARIO, DE_CARGO
) VALUES (
    1001,
    'joao@uem.br',
    'João Silva',
    'senha123',              -- ⚠️ TEXTO PLANO!
    '42',
    'ADMIN',
    'Analista de Sistemas'
);
```

#### 🔍 Consultas Utilizadas no Sistema:

**1. Buscar usuário completo por email** (`UsuarioRepository.java:13`)
```sql
SELECT *
FROM SGV.SGV_USUARIO
WHERE EN_EMAIL = 'joao@uem.br';
```

**2. Buscar senha por email** (`UsuarioRepository.java:17`)
```sql
SELECT SE_USUARIO
FROM SGV.SGV_USUARIO
WHERE EN_EMAIL = 'joao@uem.br';
```

**3. Validar credenciais** (`UsuarioRepository.java:21`)
```sql
SELECT COUNT(*)
FROM SGV.SGV_USUARIO
WHERE EN_EMAIL = 'joao@uem.br'
  AND SE_USUARIO = 'senha123';  -- Comparação direta!
```

**4. Buscar dados completos para retorno** (`UsuarioAdminRepository.java:22`)
```sql
SELECT CD_USUARIO, EN_EMAIL, NM_USUARIO, CD_SETOR, DE_CARGO, TP_USUARIO
FROM SGV.SGV_USUARIO
WHERE EN_EMAIL = 'joao@uem.br';
```

---

## 🧩 Componentes do Sistema

### 1. Entidades JPA

#### 📄 `Usuario.java` (Entidade)
**Localização:** `jpa/Usuario.java`

```java
@Entity
@Table(name = "SGV_USUARIO", schema = "SGV")
public class Usuario {

    @Id
    @Column(name = "CD_USUARIO")
    private Integer cdUsuario;

    @Column(name = "EN_EMAIL", nullable = false, length = 50)
    private String enEmail;

    @Column(name = "NM_USUARIO", nullable = false, length = 50)
    private String nmUsuario;

    @Column(name = "SE_USUARIO", nullable = false, length = 100)
    private String seUsuario;  // Senha em texto plano

    @Column(name = "CD_SETOR", length = 15)
    private String cdSetor;

    @Column(name = "TP_USUARIO", length = 50)
    private String tpUsuario;

    @Column(name = "DE_CARGO", length = 200)
    private String deCargo;

    // ... outros campos e getters/setters
}
```

**Mapeamento de Nomenclatura:**

| Campo Java | Coluna DB2 | Significado |
|------------|------------|-------------|
| `cdUsuario` | `CD_USUARIO` | Código do Usuário |
| `enEmail` | `EN_EMAIL` | Email (EN = Endereço Eletrônico) |
| `nmUsuario` | `NM_USUARIO` | Nome do Usuário |
| `seUsuario` | `SE_USUARIO` | Senha do Usuário |
| `cdSetor` | `CD_SETOR` | Código do Setor |
| `tpUsuario` | `TP_USUARIO` | Tipo de Usuário |
| `deCargo` | `DE_CARGO` | Descrição do Cargo |

---

### 2. Repositórios (Acesso ao Banco)

#### 📄 `UsuarioRepository.java`
**Localização:** `jpa/UsuarioRepository.java`

```java
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    // Busca usuário completo por email
    @Query(value = "SELECT * FROM SGV.SGV_USUARIO WHERE EN_EMAIL = ?1", nativeQuery = true)
    Optional<Usuario> findByEmail(String email);

    // Busca apenas a senha (texto plano) pelo email
    @Query(value = "SELECT SE_USUARIO FROM SGV.SGV_USUARIO WHERE EN_EMAIL = ?1", nativeQuery = true)
    String findPasswordByEmail(String email);

    // Valida credenciais (comparação direta de senha em texto plano)
    @Query(value = "SELECT COUNT(*) FROM SGV.SGV_USUARIO WHERE EN_EMAIL = ?1 AND SE_USUARIO = ?2", nativeQuery = true)
    int countByEmailAndSenha(String email, String senha);
}
```

**Responsabilidades:**
- Buscar usuário completo por email
- Consultar senha em texto plano do usuário
- Validar credenciais com comparação direta

#### 📄 `UsuarioAdminRepository.java`
**Localização:** `jpa/UsuarioAdminRepository.java`

```java
@Repository
public class UsuarioAdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioAdmin buscarEmail(String email) {
        String sql = "SELECT CD_USUARIO, EN_EMAIL, NM_USUARIO, CD_SETOR, DE_CARGO, TP_USUARIO " +
                     "FROM SGV.SGV_USUARIO " +
                     "WHERE EN_EMAIL = :email";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("email", email);  // ✅ Query parametrizada (sem SQL Injection)

        List<Object[]> resultList = query.getResultList();
        UsuarioAdmin usu = new UsuarioAdmin();

        if (!resultList.isEmpty()) {
            Object[] row = resultList.get(0);
            usu.setCdUsuario(Integer.parseInt(row[0].toString()));
            usu.setEmail(row[1].toString());
            usu.setNome(row[2].toString());
            usu.setCdSetor(row[3] != null ? row[3].toString() : "");
            usu.setDeCargo(row[4] != null ? row[4].toString() : "");
            usu.setTpUsuario(row[5] != null ? row[5].toString() : "");
        }

        return usu;
    }

    public UsuarioAdmin buscarPorCodigo(Integer cdUsuario) {
        // Similar ao método acima, mas busca por CD_USUARIO
    }
}
```

**Responsabilidades:**
- Buscar informações completas do usuário para retorno na API
- Usado na autenticação App para popular o objeto de resposta
- ✅ Usa queries parametrizadas (seguro contra SQL Injection)

---

### 3. DTOs (Objetos de Transferência)

#### 📄 `AuthenticationRequest.java` (Request Web)
```java
public class AuthenticationRequest {
    private String email;
    private String senha;
}
```
**Uso:** Recebe credenciais na autenticação web (`/autenticar`)

#### 📄 `AuthenticationAppRequest.java` (Request App)
```java
public class AuthenticationAppRequest {
    private String en_email;    // Email (nomenclatura legacy)
    private String de_senha;    // Senha (nomenclatura legacy)
}
```
**Uso:** Recebe credenciais na autenticação app (`/autenticar_app`)

#### 📄 `UsuarioAdmin.java` (DTO)
```java
public class UsuarioAdmin {
    private Integer cdUsuario;   // Código do usuário
    private String email;        // Email
    private String nome;         // Nome completo
    private String cdSetor;      // Código do setor
    private String deCargo;      // Descrição do cargo
    private String tpUsuario;    // Tipo de usuário
}
```
**Uso:** Transferência de dados do usuário entre camadas

#### 📄 `RetornoLogin.java` (Response App)
```java
public class RetornoLogin {
    private String lt_login;         // Login do usuário
    private String en_email;         // Email do usuário
    private String nm_pessoa;        // Nome completo
    private String de_mensagem;      // Mensagem de sucesso/erro
    private String lt_token;         // JWT Token
    private String fl_facial;        // Permissão: reconhecimento facial (S/N)
    private String fl_sede;          // Permissão: acesso à sede (S/N)
    private String fl_coletar;       // Permissão: coletar dados (S/N)
    private String fl_transmitir;    // Permissão: transmitir dados (S/N)
    private String cd_evento;        // Código do evento autorizado
}
```
**Uso:** Retorna dados completos na autenticação app

---

### 4. Serviços (Lógica de Negócio)

#### 📄 `AuthenticationService.java`
**Localização:** `service/AuthenticationService.java`

Este é o **coração da autenticação**. Implementa três métodos:

##### Método 1: `authenticate()` - Autenticação Web
**Linha:** `AuthenticationService.java:31`

```java
public String authenticate(String email, String senha) {
    // Autenticação com senha em texto plano (sem BCrypt)

    // CASO 1: Usuário admin hard-coded
    if (email.equals("admlog@institutoaocp.org.br")) {
        if (senha.equals("177900")) {
            return jwtUtil.generateToken(email);
        } else {
            return "Credenciais inválidas ou usuário inativo.";
        }
    }

    // CASO 2: Usuário normal do banco de dados
    else {
        // 1. Busca a senha em texto plano do banco
        String storedPassword = usuarioRepository.findPasswordByEmail(email);

        // 2. Compara a senha informada DIRETAMENTE (sem hash)
        if (storedPassword != null && storedPassword.equals(senha)) {
            // 3. Gera e retorna o JWT token
            return jwtUtil.generateToken(email);
        } else {
            return "Credenciais inválidas ou usuário inativo.";
        }
    }
}
```

**Fluxo:**
1. Verifica se é o admin hard-coded
2. Se não, busca a senha em texto plano no banco pelo email
3. Compara a senha fornecida DIRETAMENTE (sem hash)
4. Se válido, gera um token JWT com o email como subject
5. Retorna o token ou mensagem de erro

**⚠️ Diferença com BCrypt:**
- **SEM BCrypt:** `storedPassword.equals(senha)` - comparação direta
- **COM BCrypt:** `passwordEncoder.matches(senha, storedHash)` - compara com hash

##### Método 2: `authenticateLucca()` - API Externa
**Linha:** `AuthenticationService.java:54`

```java
public String authenticateLucca(String email, String senha) {
    String apiUrl = "https://luccasoftware.com.br/api/iaocp_auth";

    // 1. Monta requisição POST com JSON
    JSONObject requestBody = new JSONObject();
    requestBody.put("en_email", email);
    requestBody.put("lt_password", senha);

    // 2. Envia para API externa
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    // ... envia dados

    // 3. Processa resposta JSON
    JSONObject jsonResponse = new JSONObject(response.toString());

    // 4. Retorna o código do setor (cd_setor) em caso de sucesso
    if (jsonResponse.has("token")) {
        return jsonResponse.getString("cd_setor");
    } else {
        return "Erro: " + jsonResponse.getString("error");
    }
}
```

**Função:** Valida credenciais em uma API externa e retorna o código do setor/evento.

##### Método 3: `authenticateApp()` - Autenticação App Completa
**Linha:** `AuthenticationService.java:106`

```java
public RetornoLogin authenticateApp(String email, String senha) {

    RetornoLogin r = new RetornoLogin();
    // Inicializa com valores padrão (erro)
    r.setDe_mensagem("Credenciais inválidas ou usuário inativo.");
    r.setLt_token("");
    // ... outras flags como "N"

    // CASO 1: Admin hard-coded
    if (email.equals("admlog@institutoaocp.org.br")) {
        if (senha.equals("177900")) {
            String token = jwtUtil.generateToken(email);
            r.setLt_login("Logistica");
            r.setEn_email("logistica@institutoaocp.org.br");
            r.setNm_pessoa("Logistica");
            r.setDe_mensagem("OK");
            r.setLt_token(token);
            r.setFl_facial("S");
            r.setFl_coletar("S");
            r.setFl_sede("S");
            r.setFl_transmitir("S");
            r.setCd_evento("0");
            return r;
        }
    }

    // CASO 2: Autenticação via API externa
    else {
        String cd_evento = authenticateLucca(email, senha);
        if (!cd_evento.contains("Erro")) {
            String token = jwtUtil.generateToken(email);
            r.setLt_login("Logistica");
            r.setEn_email(email);
            r.setDe_mensagem("OK");
            r.setLt_token(token);
            r.setFl_facial("S");
            r.setFl_coletar("S");
            r.setFl_sede("S");
            r.setFl_transmitir("S");
            r.setCd_evento(cd_evento);
            return r;
        }
    }

    return r;  // Retorna objeto com erro
}
```

**Código comentado alternativo** (usando banco local):
```java
// ALTERNATIVA: Autenticação usando banco local (sem API externa)
String storedPassword = usuarioRepository.findPasswordByEmail(email);
if (storedPassword != null && storedPassword.equals(senha)) {
    UsuarioAdmin usu = usuarioAdminRepository.buscarEmail(email);
    String token = jwtUtil.generateToken(email);
    r.setLt_login(usu.getNome());
    r.setEn_email(email);
    r.setNm_pessoa(usu.getNome());
    r.setDe_mensagem("OK");
    r.setLt_token(token);
    r.setFl_facial("S");
    r.setFl_coletar("S");
    r.setFl_sede("S");
    r.setFl_transmitir("S");
    r.setCd_evento(usu.getCdSetor() != null ? usu.getCdSetor() : "0");
    return r;
}
```

#### 📄 `MyUserDetailsService.java`
**Localização:** `service/MyUserDetailsService.java`

```java
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário do banco de dados pelo email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        }

        Usuario usuario = usuarioOpt.get();

        // IMPORTANTE: Como a senha NÃO está criptografada no banco,
        // usamos {noop} prefix para indicar ao Spring Security que não há encoding
        // Isso permite comparação direta de texto plano
        return new User(
            usuario.getEnEmail(),
            "{noop}" + usuario.getSeUsuario(),  // {noop} = no operation (sem criptografia)
            new ArrayList<>()  // Authorities/Roles (vazio por enquanto)
        );
    }
}
```

**Função:** Implementa a interface `UserDetailsService` do Spring Security.

**⚠️ Conceito Importante: `{noop}` Password Encoder**

O Spring Security usa **DelegatingPasswordEncoder** que detecta o tipo de encoding pelo prefixo:
- `{bcrypt}$2a$10$...` - Senha com BCrypt
- `{noop}senha123` - Senha em texto plano (no operation)
- `{pbkdf2}...` - Senha com PBKDF2
- `{scrypt}...` - Senha com SCrypt

Como nossas senhas estão em texto plano, usamos `{noop}` para desabilitar a criptografia.

---

### 5. Utilitários (JWT e Security)

#### 📄 `JwtUtil.java`
**Localização:** `utils/JwtUtil.java`

```java
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secretooqueeuquiser12345678901234567890";

    // Gera a chave de assinatura HMAC-SHA256
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Extrai o username (subject) do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrai a data de expiração
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Verifica se o token está expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // GERA UM NOVO TOKEN JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)                                          // Email do usuário
                .setIssuedAt(new Date(System.currentTimeMillis()))            // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // +10 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)          // Assina com HMAC-SHA256
                .compact();
    }

    // VALIDA UM TOKEN
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
```

**Estrutura do JWT gerado:**

```
Header:
{
  "alg": "HS256",
  "typ": "JWT"
}

Payload:
{
  "sub": "joao@uem.br",            // Username (email)
  "iat": 1700000000,               // Issued At (timestamp)
  "exp": 1700036000                // Expiration (timestamp + 10h)
}

Signature:
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret_key
)
```

**Token completo (exemplo):**
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvQHVlbS5iciIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDM2MDAwfQ.Xj8K5dF2mN9pL3qR7sT1vU4wX5yZ8aB6cD0eF2gH3iJ
```

---

#### 📄 `JwtAuthenticationFilter.java`
**Localização:** `utils/JwtAuthenticationFilter.java`

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // 1. EXTRAI O HEADER "Authorization"
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. VERIFICA SE O HEADER EXISTE E COMEÇA COM "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Remove "Bearer "
            username = jwtUtil.extractUsername(jwt);  // Extrai email do token
        }

        // 3. SE O USERNAME FOI EXTRAÍDO E NÃO HÁ AUTENTICAÇÃO NO CONTEXTO
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. CARREGA OS DETALHES DO USUÁRIO DO BANCO
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 5. VALIDA O TOKEN
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {

                // 6. CRIA O OBJETO DE AUTENTICAÇÃO
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,  // Credenciais (não precisamos mais)
                    userDetails.getAuthorities()  // Roles/Permissões
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. DEFINE A AUTENTICAÇÃO NO CONTEXTO DO SPRING SECURITY
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8. CONTINUA A CADEIA DE FILTROS
        chain.doFilter(request, response);
    }
}
```

**Função:** Intercepta **TODAS** as requisições HTTP e:
1. Extrai o token JWT do header `Authorization`
2. Valida o token
3. Busca o usuário do banco (via `MyUserDetailsService`)
4. Se válido, autentica o usuário no Spring Security
5. Permite que a requisição continue

**Quando NÃO executa:**
- Se não houver header `Authorization`
- Se o header não começar com "Bearer "
- Se o token for inválido ou expirado

---

#### 📄 `SecurityConfig.java`
**Localização:** `utils/SecurityConfig.java`

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    // ENCODER DE SENHAS (mantido para compatibilidade, mas não usado)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CONFIGURAÇÃO DA CADEIA DE FILTROS DE SEGURANÇA
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita CSRF (não necessário para APIs REST stateless)
            .csrf(csrf -> csrf.disable())

            // CONFIGURAÇÃO DE AUTORIZAÇÃO DE ENDPOINTS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api_dw/v1/autenticar").permitAll()               // Público
                .requestMatchers("/api_dw/v1/autenticar_app").permitAll()           // Público
                .requestMatchers("/api_dw/v1/autenticar_dw").permitAll()            // Público
                .requestMatchers("/api_dw/v1/presigned-url").permitAll()            // Público (S3)
                .requestMatchers("/api_dw/v1/presigned-url-download").permitAll()   // Público (S3)
                .requestMatchers("/api_dw/v1/tamanho-arquivo-s3").permitAll()       // Público (S3)
                .requestMatchers("/api_dw/v1/upload-multipart").permitAll()         // Público (S3)
                .anyRequest().authenticated()                                        // Todos outros: requer autenticação
            )

            // GERENCIAMENTO DE SESSÃO (Stateless = sem sessão)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        // ADICIONA O FILTRO JWT ANTES DO FILTRO DE AUTENTICAÇÃO PADRÃO
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // AUTHENTICATION MANAGER (necessário para autenticação programática)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

**Responsabilidades:**
1. **Define endpoints públicos** (não precisam de token)
2. **Define endpoints protegidos** (precisam de token JWT)
3. **Configura sessão stateless** (sem cookies de sessão)
4. **Adiciona o filtro JWT** na cadeia de filtros
5. **Mantém PasswordEncoder** (por compatibilidade, mas não é usado)

---

## 🔄 Fluxo de Autenticação Completo

### Fluxo 1: LOGIN (Gerando o Token)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         FLUXO DE LOGIN                                  │
└─────────────────────────────────────────────────────────────────────────┘

1. Cliente envia POST /api_dw/v1/autenticar
   Body: { "email": "joao@uem.br", "senha": "senha123" }

   ↓

2. AuthenticationController recebe a requisição
   - NÃO passa pelo JwtAuthenticationFilter (endpoint público)

   ↓

3. AuthenticationController.autenticar() chama AuthenticationService.authenticate()

   ↓

4. AuthenticationService.authenticate() executa:

   4a. Verifica se é admin hard-coded?
       ├─ SIM → Valida senha "177900" → Gera token
       └─ NÃO → Continua para 4b

   4b. Busca senha em texto plano no banco:
       usuarioRepository.findPasswordByEmail("joao@uem.br")

       SQL: SELECT SE_USUARIO
            FROM SGV.SGV_USUARIO
            WHERE EN_EMAIL = 'joao@uem.br'

       Retorna: "senha123"

   4c. Compara senha fornecida DIRETAMENTE:
       "senha123".equals("senha123")  // true

       ⚠️ SEM BCRYPT! Comparação de strings normais!

       ├─ VÁLIDO → Continua para 4d
       └─ INVÁLIDO → Retorna erro

   4d. Gera token JWT:
       jwtUtil.generateToken("joao@uem.br")
       Retorna: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

   ↓

5. Controller retorna o token para o cliente

6. Cliente armazena o token (localStorage, sessionStorage, etc.)
```

**SQL Executada:**
```sql
-- Passo 4b
SELECT SE_USUARIO
FROM SGV.SGV_USUARIO
WHERE EN_EMAIL = 'joao@uem.br';

-- Resultado: "senha123" (texto plano)
```

**Resultado:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvQHVlbS5iciIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDM2MDAwfQ.Xj8K5dF2mN9pL3qR7sT1vU4wX5yZ8aB6cD0eF2gH3iJ"
}
```

---

### Fluxo 2: ACESSO A ENDPOINT PROTEGIDO (Validando o Token)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                   FLUXO DE ACESSO A RECURSO PROTEGIDO                   │
└─────────────────────────────────────────────────────────────────────────┘

1. Cliente envia GET /api_dw/v1/retornarLocais/prefixo
   Header: Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

   ↓

2. JwtAuthenticationFilter intercepta a requisição

   2a. Extrai o header "Authorization"
       authorizationHeader = "Bearer eyJhbGciOi..."

   2b. Verifica se começa com "Bearer "
       ├─ SIM → Remove "Bearer " e fica com o token puro
       └─ NÃO → Ignora e passa para o próximo filtro

   2c. Extrai o username (email) do token:
       username = jwtUtil.extractUsername(token)
       Resultado: "joao@uem.br"

   ↓

3. Carrega detalhes do usuário DO BANCO:
   userDetails = userDetailsService.loadUserByUsername("joao@uem.br")

   3a. MyUserDetailsService.loadUserByUsername() executa:

       SQL: SELECT *
            FROM SGV.SGV_USUARIO
            WHERE EN_EMAIL = 'joao@uem.br'

       Retorna: Usuario{
           cdUsuario: 1001,
           enEmail: "joao@uem.br",
           seUsuario: "senha123",  // Senha em texto plano
           nmUsuario: "João Silva",
           ...
       }

   3b. Cria UserDetails com senha prefixada com {noop}:
       return new User(
           "joao@uem.br",
           "{noop}senha123",  // {noop} = sem criptografia
           new ArrayList<>()
       );

   ↓

4. Valida o token:
   jwtUtil.validateToken(token, "joao@uem.br")

   4a. Extrai username do token novamente
   4b. Compara com o username fornecido
   4c. Verifica se o token NÃO está expirado
       exp = extractExpiration(token)
       isExpired = exp.before(new Date())

   ├─ VÁLIDO → Continua para passo 5
   └─ INVÁLIDO → Ignora e passa para próximo filtro (requisição será bloqueada depois)

   ↓

5. Cria objeto de autenticação:
   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
       userDetails,           // Principal (usuário autenticado)
       null,                  // Credentials (não mais necessárias)
       authorities            // Roles/Permissões
   );

   ↓

6. Define autenticação no contexto do Spring Security:
   SecurityContextHolder.getContext().setAuthentication(authToken);

   ↓

7. Passa para o próximo filtro (chain.doFilter())

   ↓

8. SecurityFilterChain verifica autorização:
   - Endpoint é protegido (.anyRequest().authenticated())
   - Usuário está autenticado?
     └─ SecurityContext contém autenticação? SIM → Permite acesso

   ↓

9. LocalController.retornarLocais() é executado
   - Código pode acessar usuário autenticado:
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     String email = auth.getName();  // "joao@uem.br"

   ↓

10. Controller retorna os dados para o cliente
```

**SQL Executada:**
```sql
-- Passo 3a
SELECT *
FROM SGV.SGV_USUARIO
WHERE EN_EMAIL = 'joao@uem.br';
```

**Decodificação do JWT:**
```
Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvQHVlbS5iciIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDM2MDAwfQ.signature

Header (decodificado):
{
  "alg": "HS256",
  "typ": "JWT"
}

Payload (decodificado):
{
  "sub": "joao@uem.br",          ← Username extraído aqui
  "iat": 1700000000,             ← Criado em: 2023-11-14 22:13:20
  "exp": 1700036000              ← Expira em: 2023-11-15 08:13:20 (10h depois)
}
```

---

### Fluxo 3: AUTENTICAÇÃO APP (Com API Externa)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    FLUXO DE AUTENTICAÇÃO APP                            │
└─────────────────────────────────────────────────────────────────────────┘

1. Cliente envia POST /api_dw/v1/autenticar_app
   Body: { "en_email": "joao@uem.br", "de_senha": "senha123" }

   ↓

2. AuthenticationController.autenticarApp() recebe a requisição

   ↓

3. Chama AuthenticationService.authenticateApp()

   3a. Verifica se é admin hard-coded?
       ├─ SIM → Monta RetornoLogin completo com todas flags "S"
       └─ NÃO → Continua para 3b

   3b. Chama API externa Lucca Software:
       authenticateLucca("joao@uem.br", "senha123")

       ┌─────────────────────────────────────────────────────────┐
       │  SUBFLUXO: API EXTERNA                                  │
       │                                                         │
       │  POST https://luccasoftware.com.br/api/iaocp_auth      │
       │  Body: {                                               │
       │    "en_email": "joao@uem.br",                         │
       │    "lt_password": "senha123"                          │
       │  }                                                    │
       │                                                       │
       │  Response (sucesso):                                  │
       │  {                                                    │
       │    "token": "abc123...",                             │
       │    "cd_setor": "42"        ← Código retornado        │
       │  }                                                   │
       │                                                      │
       │  Response (erro):                                    │
       │  {                                                   │
       │    "error": "Credenciais inválidas"                 │
       │  }                                                  │
       └─────────────────────────────────────────────────────┘

   3c. Se autenticação externa foi bem-sucedida:
       - Gera token JWT local: jwtUtil.generateToken(email)
       - Monta objeto RetornoLogin com:
         * Token JWT
         * Dados do usuário
         * Todas flags de permissão como "S"
         * cd_evento = código retornado pela API externa

   ↓

4. Controller retorna objeto completo para o cliente
```

**Nenhuma SQL executada** (autentica apenas via API externa)

**Resposta (sucesso):**
```json
{
  "lt_login": "Logistica",
  "en_email": "joao@uem.br",
  "nm_pessoa": "Logistica",
  "de_mensagem": "OK",
  "lt_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "fl_facial": "S",
  "fl_sede": "S",
  "fl_coletar": "S",
  "fl_transmitir": "S",
  "cd_evento": "42"
}
```

**Resposta (erro):**
```json
{
  "lt_login": "",
  "en_email": "",
  "nm_pessoa": "",
  "de_mensagem": "Credenciais inválidas ou usuário inativo.",
  "lt_token": "",
  "fl_facial": "N",
  "fl_sede": "N",
  "fl_coletar": "N",
  "fl_transmitir": "N",
  "cd_evento": "0"
}
```

---

## 🎓 Conceitos do Spring Security

### 1. **Authentication vs Authorization**

| Conceito | Significado | Exemplo |
|----------|-------------|---------|
| **Authentication** | *Quem você é?* | Login com email e senha |
| **Authorization** | *O que você pode fazer?* | Usuário admin pode deletar, usuário comum não |

**No projeto:**
- **Authentication:** Feita pelo JWT (identifica o usuário pelo email no token)
- **Authorization:** Não implementada (todos usuários autenticados têm as mesmas permissões)

### 2. **Stateless vs Stateful**

| Tipo | Descrição | Como funciona |
|------|-----------|---------------|
| **Stateful** | Servidor mantém sessão | Servidor guarda que você está logado (HTTP Session) |
| **Stateless** | Servidor NÃO mantém sessão | Cada requisição é independente, token contém tudo |

**Vantagens do Stateless (JWT):**
- Escalável (pode ter múltiplos servidores)
- Não usa memória do servidor
- Funciona bem para APIs REST

**Configuração no projeto:**
```java
.sessionManagement(session -> session
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
);
```

### 3. **Senha em Texto Plano vs Hash**

#### ⚠️ Texto Plano (Projeto Atual)

**Como funciona:**
```java
// Ao criar usuário
String senhaTexto = "senha123";
usuario.setSeUsuario(senhaTexto);  // Armazena DIRETO no banco

// No banco
SE_USUARIO: "senha123"  // ❌ VISÍVEL!

// Ao fazer login
String senhaFornecida = "senha123";
String senhaBanco = "senha123";
boolean senhaCorreta = senhaFornecida.equals(senhaBanco);  // Comparação direta
```

**Problemas:**
- ❌ Se o banco vazar, senhas ficam expostas
- ❌ Administradores do banco têm acesso
- ❌ Violação de privacidade e segurança
- ❌ Não atende regulamentações (LGPD, GDPR)

#### ✅ BCrypt (Recomendado para Produção)

**Como funcionaria:**
```java
PasswordEncoder encoder = new BCryptPasswordEncoder();

// Ao criar usuário
String senhaTexto = "senha123";
String hash = encoder.encode(senhaTexto);
// Resultado: "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy"
usuario.setSeUsuario(hash);  // Armazena o HASH

// No banco
SE_USUARIO: "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy"  // ✅ HASH!

// Ao fazer login
boolean senhaCorreta = encoder.matches("senha123", hash);  // true
boolean senhaErrada = encoder.matches("senha456", hash);   // false
```

**Vantagens:**
- ✅ Irreversível (não tem "decrypt")
- ✅ Adiciona "salt" (aleatoriedade) automático
- ✅ Lento de propósito (dificulta ataques de força bruta)
- ✅ Mesmo texto gera hashes diferentes

**Estrutura do hash BCrypt:**
```
$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
│  │ │  │                                                        │
│  │ │  └─ Salt (22 caracteres)                                 │
│  │ └─ Cost factor (10 = 2^10 = 1024 iterações)                │
│  └─ Versão do algoritmo                                       │
└─ Identificador BCrypt                                         └─ Hash (31 caracteres)
```

### 4. **Password Encoder: `{noop}` Prefix**

O Spring Security 5+ usa **DelegatingPasswordEncoder** que detecta o tipo de encoding pelo prefixo:

```java
// Diferentes tipos de encoding
"{bcrypt}$2a$10$..."      // BCrypt
"{noop}senha123"          // Sem criptografia (no operation)
"{pbkdf2}..."             // PBKDF2
"{scrypt}..."             // SCrypt
"{sha256}..."             // SHA-256
```

**No projeto:**
```java
return new User(
    "joao@uem.br",
    "{noop}senha123",  // {noop} = desabilita criptografia
    new ArrayList<>()
);
```

**Por que usar `{noop}`?**
- Senhas já estão em texto plano no banco
- Spring Security precisa saber que não deve fazer hash
- Permite comparação direta

### 5. **JWT (JSON Web Token)**

**Estrutura:**
```
header.payload.signature
```

**Características:**
- Auto-contido (contém dados do usuário)
- Assinado (não pode ser alterado)
- Base64 URL encoded (pode ser enviado em URLs)

**Não é criptografado!** Qualquer um pode decodificar e ler o payload. Portanto:
- ❌ NÃO coloque senhas no JWT
- ❌ NÃO coloque dados sensíveis
- ✅ Coloque apenas identificador (email, ID)

**Assinatura garante integridade:**
```java
// Se alguém modificar o payload manualmente:
// Payload original: { "sub": "joao@uem.br", "exp": 1700036000 }
// Payload alterado: { "sub": "admin@uem.br", "exp": 1700036000 }

// A assinatura NÃO vai bater!
// jwtUtil.validateToken() retornará FALSE
```

### 6. **Filter Chain (Cadeia de Filtros)**

Spring Security funciona como uma **cadeia de filtros** que interceptam requisições HTTP:

```
Requisição HTTP
      ↓
┌─────────────────────────────────┐
│ JwtAuthenticationFilter         │ ← Nosso filtro customizado
│ (valida JWT e autentica)        │
└─────────────────────────────────┘
      ↓
┌─────────────────────────────────┐
│ UsernamePasswordAuthenticationFilter │
│ (login com username/password)   │
└─────────────────────────────────┘
      ↓
┌─────────────────────────────────┐
│ AuthorizationFilter             │
│ (verifica permissões)           │
└─────────────────────────────────┘
      ↓
  Controller
```

**No projeto:**
```java
http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
```
Significa: "Execute JwtAuthenticationFilter ANTES de UsernamePasswordAuthenticationFilter"

### 7. **Security Context**

É o "armazenamento temporário" da autenticação durante a requisição:

```java
// JwtAuthenticationFilter armazena a autenticação
SecurityContextHolder.getContext().setAuthentication(authToken);

// Controller pode recuperar a autenticação
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
String email = auth.getName();  // Email do usuário autenticado
```

**Importante:**
- Válido apenas durante a requisição atual
- Cada thread tem seu próprio SecurityContext
- Limpo automaticamente no final da requisição

---

## 📝 Exemplos Práticos

### Exemplo 1: Criar um novo usuário no banco

```java
@RestController
@RequestMapping("/api_dw/v1")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestBody UsuarioRequest request) {

        // 1. Cria a entidade
        Usuario usuario = new Usuario();
        usuario.setCdUsuario(request.getCdUsuario());
        usuario.setEnEmail(request.getEmail());
        usuario.setNmUsuario(request.getNome());
        usuario.setSeUsuario(request.getSenha());  // ⚠️ TEXTO PLANO!
        usuario.setCdSetor(request.getCdSetor());
        usuario.setTpUsuario("COMUM");

        // 2. Salva no banco
        usuarioRepository.save(usuario);

        return "Usuário criado com sucesso!";
    }
}
```

**SQL gerada pelo JPA:**
```sql
INSERT INTO SGV.SGV_USUARIO (
    CD_USUARIO, EN_EMAIL, NM_USUARIO, SE_USUARIO, CD_SETOR, TP_USUARIO
) VALUES (
    1001,
    'joao@uem.br',
    'João Silva',
    'senha123',  -- ⚠️ TEXTO PLANO!
    '42',
    'COMUM'
);
```

---

### Exemplo 2: Testar autenticação com cURL

**Login (obter token):**
```bash
curl -X POST http://localhost:8080/api_dw/v1/autenticar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@uem.br",
    "senha": "senha123"
  }'
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvQHVlbS5iciIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDM2MDAwfQ.Xj8K5dF2mN9pL3qR7sT1vU4wX5yZ8aB6cD0eF2gH3iJ"
}
```

**Acessar endpoint protegido:**
```bash
curl -X GET http://localhost:8080/api_dw/v1/retornarLocais/prefixo \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

### Exemplo 3: Obter usuário autenticado no Controller

```java
@RestController
@RequestMapping("/api_dw/v1")
public class LocalController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/meus-dados")
    public Map<String, Object> meusDados() {

        // Obtém a autenticação do contexto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extrai o email (username)
        String email = authentication.getName();

        // Busca dados completos do usuário no banco
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        Map<String, Object> dados = new HashMap<>();
        dados.put("cdUsuario", usuario.getCdUsuario());
        dados.put("email", usuario.getEnEmail());
        dados.put("nome", usuario.getNmUsuario());
        dados.put("setor", usuario.getCdSetor());
        dados.put("cargo", usuario.getDeCargo());
        dados.put("tipo", usuario.getTpUsuario());

        return dados;
    }
}
```

**Resposta:**
```json
{
  "cdUsuario": 1001,
  "email": "joao@uem.br",
  "nome": "João Silva",
  "setor": "42",
  "cargo": "Analista de Sistemas",
  "tipo": "ADMIN"
}
```

---

### Exemplo 4: Decodificar JWT manualmente (JavaScript)

```javascript
// Função para decodificar JWT (apenas visualização, NÃO valida assinatura!)
function decodificarJWT(token) {
    const partes = token.split('.');

    const header = JSON.parse(atob(partes[0]));
    const payload = JSON.parse(atob(partes[1]));

    console.log('Header:', header);
    console.log('Payload:', payload);

    // Verifica se expirou
    const agora = Date.now() / 1000;  // Em segundos
    if (payload.exp < agora) {
        console.log('Token EXPIRADO!');
    } else {
        const tempoRestante = (payload.exp - agora) / 3600;  // Em horas
        console.log(`Token válido por mais ${tempoRestante.toFixed(2)} horas`);
    }
}

const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
decodificarJWT(token);
```

**Saída:**
```
Header: { alg: 'HS256', typ: 'JWT' }
Payload: { sub: 'joao@uem.br', iat: 1700000000, exp: 1700036000 }
Token válido por mais 8.45 horas
```

---

### Exemplo 5: Migrar para BCrypt (Produção)

Se você quiser **migrar para BCrypt no futuro**, siga estes passos:

#### Passo 1: Criar script de migração

```java
@Service
public class MigracaoSenhasService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void migrarSenhasParaBCrypt() {
        // 1. Busca todos os usuários
        Query query = entityManager.createNativeQuery(
            "SELECT CD_USUARIO, SE_USUARIO FROM SGV.SGV_USUARIO",
            Object[].class
        );
        List<Object[]> usuarios = query.getResultList();

        // 2. Para cada usuário
        for (Object[] row : usuarios) {
            Integer cdUsuario = (Integer) row[0];
            String senhaTextoPlano = (String) row[1];

            // 3. Gera hash BCrypt
            String hashBCrypt = passwordEncoder.encode(senhaTextoPlano);

            // 4. Atualiza no banco
            Query updateQuery = entityManager.createNativeQuery(
                "UPDATE SGV.SGV_USUARIO SET SE_USUARIO = ?1 WHERE CD_USUARIO = ?2"
            );
            updateQuery.setParameter(1, hashBCrypt);
            updateQuery.setParameter(2, cdUsuario);
            updateQuery.executeUpdate();

            System.out.println("Usuário " + cdUsuario + " migrado");
        }

        System.out.println("Migração concluída!");
    }
}
```

#### Passo 2: Atualizar AuthenticationService

```java
public String authenticate(String email, String senha) {
    if (email.equals("admlog@institutoaocp.org.br")) {
        if (senha.equals("177900")) {
            return jwtUtil.generateToken(email);
        } else {
            return "Credenciais inválidas ou usuário inativo.";
        }
    } else {
        // Busca o HASH do banco
        String storedHash = usuarioRepository.findPasswordByEmail(email);

        // Compara com BCrypt
        if (storedHash != null && passwordEncoder.matches(senha, storedHash)) {
            return jwtUtil.generateToken(email);
        } else {
            return "Credenciais inválidas ou usuário inativo.";
        }
    }
}
```

#### Passo 3: Atualizar MyUserDetailsService

```java
@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

    if (usuarioOpt.isEmpty()) {
        throw new UsernameNotFoundException("Usuário não encontrado: " + email);
    }

    Usuario usuario = usuarioOpt.get();

    // REMOVE o {noop}, senha já está com hash BCrypt
    return new User(
        usuario.getEnEmail(),
        usuario.getSeUsuario(),  // Hash BCrypt (sem prefixo)
        new ArrayList<>()
    );
}
```

---

## ⚠️ Segurança e Boas Práticas

### ❌ Problemas Atuais

1. **Senhas em texto plano**
   - **Risco:** Se o banco vazar, todas as senhas ficam expostas
   - **Solução:** Migrar para BCrypt (exemplo acima)

2. **Admin hard-coded**
   - **Risco:** Credenciais no código-fonte
   - **Solução:** Mover para variáveis de ambiente ou banco

3. **Secret Key hard-coded**
   - **Risco:** Se o código vazar, tokens podem ser forjados
   - **Solução:** Mover para `application.properties`

4. **Sem autorização (roles)**
   - **Risco:** Todos usuários têm as mesmas permissões
   - **Solução:** Implementar roles (ADMIN, USER, etc.)

5. **Sem tratamento de erros padronizado**
   - **Risco:** Retorna Strings genéricas
   - **Solução:** Usar `@ExceptionHandler` e DTOs de erro

### ✅ Melhorias Recomendadas

#### 1. Externalizar configurações

**application.properties:**
```properties
# JWT
jwt.secret=${JWT_SECRET:defaultSecretKey12345678901234567890}
jwt.expiration=36000000

# Admin
admin.email=${ADMIN_EMAIL:admlog@institutoaocp.org.br}
admin.password=${ADMIN_PASSWORD:177900}
```

**JwtUtil.java:**
```java
@Value("${jwt.secret}")
private String secretKey;

@Value("${jwt.expiration}")
private long expiration;
```

#### 2. Implementar Roles

**Adicionar coluna no banco:**
```sql
ALTER TABLE SGV.SGV_USUARIO
ADD COLUMN FL_ROLE VARCHAR(20) DEFAULT 'USER';
```

**MyUserDetailsService.java:**
```java
List<GrantedAuthority> authorities = new ArrayList<>();
authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getTpUsuario()));

return new User(
    usuario.getEnEmail(),
    "{noop}" + usuario.getSeUsuario(),
    authorities  // Roles adicionados
);
```

**SecurityConfig.java:**
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api_dw/v1/admin/**").hasRole("ADMIN")
    .requestMatchers("/api_dw/v1/user/**").hasAnyRole("USER", "ADMIN")
    .anyRequest().authenticated()
)
```

#### 3. Tratamento de erros

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            "USUARIO_NAO_ENCONTRADO",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(
            "CREDENCIAIS_INVALIDAS",
            "Email ou senha incorretos",
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
```

---

## 🎯 Resumo para Estudo

**Componentes essenciais:**
1. **Usuario** (JPA Entity) - Tabela `SGV.SGV_USUARIO`
2. **UsuarioRepository** - Acesso aos dados (queries nativas)
3. **AuthenticationService** - Lógica de autenticação (comparação direta)
4. **JwtUtil** - Geração e validação de tokens
5. **JwtAuthenticationFilter** - Intercepta requisições
6. **SecurityConfig** - Configuração global
7. **MyUserDetailsService** - Carrega usuários com `{noop}`

**Fluxo simplificado:**
```
Login → AuthenticationService → Valida senha (comparação direta) → Gera JWT → Retorna token
                                       ↓
                              Busca senha texto plano no banco
                              (tabela SGV_USUARIO)

Requisição → JwtAuthenticationFilter → Valida JWT → Autentica no SecurityContext → Controller
```

**Principais aprendizados:**
- JWT é stateless (não precisa de sessão)
- `{noop}` desabilita criptografia no Spring Security
- Senhas em texto plano são inseguras (usar apenas para aprendizado)
- Filtros do Spring Security interceptam requisições
- SecurityContext guarda a autenticação durante a requisição
- Endpoints públicos não passam por validação de token

**⚠️ IMPORTANTE PARA PRODUÇÃO:**
- Sempre use BCrypt ou outro algoritmo forte
- Externalize configurações sensíveis
- Implemente autorização com roles
- Adicione logging e auditoria
- Use HTTPS em produção

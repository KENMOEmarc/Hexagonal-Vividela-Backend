# 🧺 Vividela Backend — Modular Hexagonal Architecture

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9%2B-C71A36?logo=apachemaven&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-Hexagonal%20%7C%20Modular-blueviolet)
![License](https://img.shields.io/badge/License-Private-lightgrey)

**Spring Boot** backend for inventory management (products, stock batches, movements) with **JWT** authentication, split into **autonomous business modules** (`auth`, `product`, `shared`), each following **hexagonal architecture (ports & adapters)**. This version is the "package-by-feature" evolution of the simple hexagonal template: each module contains its own `domain`, `application`, `adapter`, and `config`. 🧩

## 🧱 Technology stack

| Layer | Technology | Version | Role |
|--------|-------------|---------|------|
| ⚙️ Backend | Spring Boot | 3.5.10 | REST server, auto-configuration |
| 🔐 Security | Spring Security + JWT (JJWT) | 0.12.6 | Stateless authentication, roles |
| 🗃️ ORM | Spring Data JPA / Hibernate | — | Object-relational mapping |
| 🐬 Database | MySQL | 8.0 | Persistence (users, products, stock) |
| 🔑 Password hashing | BCrypt | strength 10 | Password protection |
| ✅ Validation | Jakarta Validation | — | Validation of incoming DTOs |
| 🤖 AI | Spring AI + Google GenAI (Gemini) | 1.1.6 | Customer review sentiment analysis *(configured)* |
| 📄 PDF | OpenPDF | 1.3.39 | Ticket/receipt generation *(configured)* |
| ✉️ Email | Spring Mail (JavaMailSender) | — | SMTP notifications *(configured)* |
| 📱 SMS | Twilio | — | SMS notifications *(configured)* |
| 🧹 Boilerplate | Lombok | 1.18.38 | Getter/setter/constructor generation |
| 🌱 Build | Maven | 3.9+ | Build and dependency management |

> ℹ️ As in the simple version, **Gemini**, **OpenPDF**, **Mail**, and **Twilio** are declared (dependencies + `application.yml`) but not yet wired into the business logic — they are part of the **Vividela** functional roadmap.

## 📂 Project structure (package-by-feature)

```
Hexagonal-Vividela-Backend/
├── pom.xml
├── docker-compose.yml         ← MySQL + Adminer
├── .env                       ← Environment variables (not committed)
└── src/main/java/ken/vivid/
    │
    ├── auth/                          ← 🔑 AUTHENTICATION & USERS MODULE
    │   ├── domain/
    │   │   ├── model/                 ← User, AuthResult, enums/Role
    │   │   └── exception/             ← InvalidCredentials, PasswordMismatch, UserAlreadyExists
    │   ├── application/
    │   │   ├── port/in/               ← LoginUseCase, RegisterUseCase, LogoutUseCase,
    │   │   │                             UpdateUserUseCase, ChangePasswordUseCase, DeleteUserUseCase…
    │   │   ├── port/out/              ← LoadUser, SaveUser, DeleteUser, PasswordEncoder,
    │   │   │                             TokenGenerator, TokenBlacklist
    │   │   └── service/               ← AuthService, UserService
    │   ├── adapter/
    │   │   ├── in/web/                ← AuthController, UserController
    │   │   │   ├── dto/ · payloads/ · security/ · config/  (SecurityConfig)
    │   │   └── out/
    │   │       ├── persistence/       ← UserJpaEntity, UserJpaRepository, UserMapper,
    │   │       │                          UserPersistenceAdapter
    │   │       ├── security/          ← BCryptPasswordAdapter
    │   │       ├── token/             ← JwtTokenAdapter
    │   │       └── blacklist/         ← RevokedTokenJpaEntity/Repository,
    │   │                                 MySqlTokenBlacklistAdapter, RevokedTokenCleanupJob
    │   └── config/                    ← AuthUseCaseConfig (module bean injection)
    │
    ├── product/                       ← 📦 PRODUCTS & STOCK MODULE
    │   ├── domain/
    │   │   ├── model/                 ← Product, Stock, StockMovement, ProductRegistration
    │   │   │                             enums/ (MeasurementUnit, MovementType, RegistrationType)
    │   │   └── exception/             ← InsufficientStockException
    │   ├── application/
    │   │   ├── port/in/product/       ← CreateProduct, UpdateProduct, DeleteProduct, GetProduct
    │   │   ├── port/in/stock/         ← RegisterStockEntry, AdjustStock, ConsumeStock, ListStockProduct
    │   │   ├── port/out/product/      ← LoadProduct, SaveProduct, DeleteProduct, SaveProductRegistration
    │   │   ├── port/out/stock/        ← LoadStock, SaveStock, SaveStockMovement
    │   │   └── service/               ← ProductService, StockService, StockAllocationPolicy
    │   ├── adapter/
    │   │   ├── in/web/                ← ProductController, StockController + dto/ · payloads/
    │   │   └── out/persistence/
    │   │       ├── product/           ← ProductJpaEntity/Repository, ProductMapper, ProductPersistenceAdapter
    │   │       │   └── registration/  ← ProductRegistrationJpaEntity/Repository/Mapper/Adapter
    │   │       └── stock/             ← StockJpaEntity/Repository, StockMapper, StockPersistenceAdapter
    │   │           └── movement/      ← StockMovementJpaEntity/Repository/Mapper/Adapter
    │   └── config/                    ← ProductUseCaseConfig (module bean injection)
    │
    ├── shared/                        ← 🔗 SHARED MODULE (common to all modules)
    │   ├── domain/exception/          ← DomainException, ResourceNotFoundException,
    │   │                                 DuplicateResourceException, InvalidRequestException,
    │   │                                 InvalidStateTransitionException
    │   └── adapter/
    │       ├── web/                   ← ApiResponse, GlobalExceptionHandler
    │       └── config/                ← CorsConfig
    │
    └── VividelaApplication.java       ← Spring Boot entry point
```

Each module (`auth`, `product`) is a **full-fledged hexagon**: its `domain` depends on nothing, its `application` orchestrates use cases through ports, and its `adapter` connects the web layer and persistence. The `shared` module contains only what is genuinely cross-cutting (standard API response, error handling, CORS). 🎯

## ✅ Prerequisites

- ☕ **Java 21+** — `java --version`
- 📦 **Maven 3.9+** — `mvn --version`
- 🐬 **MySQL 8+** — server running
- 🐳 **Docker & Docker Compose** *(optional, for MySQL + Adminer)*

## 🚀 Quick start

### 1️⃣ Environment variables

Create a `.env` file at the root (see the provided `.env` file as a template) with at least:

```bash
# --- Datasource ---
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/vividela_db
SPRING_DATASOURCE_USERNAME=vivid_user
SPRING_DATASOURCE_PASSWORD=vivid_password

# --- Docker / MySQL ---
MYSQL_ROOT_PASSWORD=root_password
MYSQL_DATABASE=vividela_db
MYSQL_USER=vivid_user
MYSQL_PASSWORD=vivid_password

# --- JWT ---
JWT_SECRET=a_random_key_of_at_least_32_characters
JWT_EXPIRATION=3600000
JWT_BLACKLIST_CLEANUP_FIXED_DELAY_MS=3600000

# --- Miscellaneous (optional if unused) ---
MAIL_FROM=contact@vividela.com
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```

### 2️⃣ MySQL database (via Docker)

```bash
docker-compose up -d
# → MySQL available at localhost:3307
# → Adminer (DB administration UI) at http://localhost:8081
```

### 3️⃣ Start the backend

```bash
mvn spring-boot:run
# → Available at http://localhost:8080/api
```

## 🔌 API endpoints

All routes are prefixed with `/api` (Spring context path). Responses follow the standard `ApiResponse<T>` envelope (`success`, `message`, `data`, `errors`, `timestamp`) defined in the `shared` module.

### 🔑 Authentication — `/auth` *( `auth` module)*

| Method | URL | Auth | Roles | Description |
|---------|-----|------|-------|--------------|
| POST | `/auth/register` | ❌ Public | — | Registration (`CUSTOMER` role assigned automatically) |
| POST | `/auth/login` | ❌ Public | — | Login, returns a JWT |
| POST | `/auth/store` | ✅ JWT | `ADMIN`, `MANAGER` | Create an account with a specific role (staff) |
| POST | `/auth/logout` | ✅ JWT | All | Logout (token revocation/blacklisting) |

### 👤 Users — `/users` *( `auth` module)*

| Method | URL | Auth | Roles | Description |
|---------|-----|------|-------|--------------|
| GET | `/users/me` | ✅ JWT | All | Profile of the authenticated user |
| PUT | `/users/{id}` | ✅ JWT | All | Update their profile |
| PUT | `/users/{id}/password` | ✅ JWT | Self only | Change their password (no admin override) |
| DELETE | `/users/{id}` | ✅ JWT | `ADMIN` | Delete a user |

### 📦 Products — `/products` *( `product` module)*

| Method | URL | Auth | Roles | Description |
|---------|-----|------|-------|--------------|
| GET | `/products` | ✅ JWT | All | List all products |
| GET | `/products/{id}` | ✅ JWT | All | Retrieve a product by ID |
| POST | `/products` | ✅ JWT | `ADMIN`, `MANAGER`, `EMPLOYEE` | Create a product |
| PUT | `/products/{id}` | ✅ JWT | `ADMIN`, `MANAGER`, `EMPLOYEE` | Update a product |
| DELETE | `/products/{id}` | ✅ JWT | `ADMIN` | Delete a product |

### 📊 Stock — `/stock` *( `product` module)*

| Method | URL | Auth | Roles | Description |
|---------|-----|------|-------|--------------|
| GET | `/stock/low` | ✅ JWT | All | Products below their alert threshold |
| GET | `/stock/product/{productId}` | ✅ JWT | All | Current stock (batch closest to expiration) |
| GET | `/stock/product/{productId}/batches` | ✅ JWT | All | All available batches, sorted by expiration |
| POST | `/stock/batches` | ✅ JWT | `ADMIN`, `MANAGER`, `EMPLOYEE` | Register a new stock batch |
| PUT | `/stock/batches/{batchId}` | ✅ JWT | `ADMIN`, `MANAGER`, `EMPLOYEE` | Adjust a batch's quantity |
| POST | `/stock/consume` | ✅ JWT | `ADMIN`, `MANAGER`, `EMPLOYEE` | Consume stock (FEFO — first expired, first out) |

## 🏗️ Modular hexagonal architecture

```
┌───────────────────────────────┐        ┌───────────────────────────────┐
│        🔑 AUTH MODULE          │        │       📦 PRODUCT MODULE        │
│                                │        │                                │
│  🔵 adapter/in/web             │        │  🔵 adapter/in/web             │
│   AuthController               │        │   ProductController            │
│   UserController                ─┐      │   StockController             ┐│
│                                │  │      │                                ││
│  🟡 application/service        │  │      │  🟡 application/service       ││
│   AuthService · UserService     │uses    │   ProductService              ││uses
│                                │  │GetCurrentUserUseCase                 ││
│  🟢 domain/model               │  │      │  🟢 domain/model               ││
│   User · AuthResult · Role      │◄─┘      │   Product · Stock · StockMovement
│                                │        │   ProductRegistration          │
│  🔵 adapter/out                │        │                                │
│   UserPersistenceAdapter        │        │  🔵 adapter/out/persistence   │
│   BCryptPasswordAdapter         │        │   ProductPersistenceAdapter   │
│   JwtTokenAdapter               │        │   StockPersistenceAdapter     │
│   MySqlTokenBlacklistAdapter    │        │   StockMovementPersistenceAdapter
└───────────────┬────────────────┘        └───────────────┬────────────────┘
               │                                          │
               └───────────────────┬──────────────────────┘
                                   │
                    ┌──────────────▼──────────────┐
                    │      🔗 SHARED MODULE         │
                    │  ApiResponse · GlobalExceptionHandler
                    │  CorsConfig · DomainException(s)
                    └──────────────┬──────────────┘
                                   │
                         ┌─────────▼─────────┐
                         │    🐬 MySQL 8.0     │
                         └────────────────────┘
```

The `product` module **depends** on `auth` (through `GetCurrentUserUseCase`, to track the user acting on stock), while `shared` depends on neither — this is the only permitted dependency direction between modules. 🎯

## 🛡️ Security

- 🔒 Passwords hashed with **BCrypt** (strength = 10), never stored in plain text
- 🎫 **JWT HS256** tokens signed with a secret key of at least 256 bits
- 🚫 **Revoked token blacklist** persisted in MySQL (`RevokedTokenJpaEntity`), with an automatic cleanup job for expired tokens (`RevokedTokenCleanupJob`)
- 🌐 **CORS** centralized in the `shared` module (`CorsConfig`), accepting only whitelisted origins
- 🧑‍🤝‍🧑 **Role-based access control** through `@PreAuthorize` (`ADMIN`, `MANAGER`, `EMPLOYEE`, `CUSTOMER`) and global rules in `auth/adapter/in/web/config/SecurityConfig`
- ✅ Input data validation with **Jakarta Validation** on all payloads
- 🔐 **Stateless** session (no server-side session; everything relies on the JWT)
- 🧯 Centralized error handling through `GlobalExceptionHandler` (`shared` module), with generic anti-enumeration messages

## 🧪 Quality & testing

> ⚠️ Unlike the “Simple Hexagonal” version, **no test dependencies** (JUnit 5, Mockito, Testcontainers, ArchUnit) are declared in this version's `pom.xml`, and `src/test` is empty. The test suite still needs to be reintroduced on this new modular structure — this is the main remaining task before reaching parity with the simple version.

## 🗺️ Roadmap

- ✅ Split into business modules (`auth`, `product`, `shared`) with a complete hexagon per module
- ✅ Introduce dedicated mappers to isolate JPA entities from the domain
- ⏳ Reintroduce the test suite (unit tests, services, adapters, ArchUnit) on the new structure
- ⏳ Actually wire the integrations that are already configured: 🤖 Gemini (review analysis), 🧾 PDF (tickets/receipts), ✉️ Email and 📱 SMS (notifications with retry), 🎁 loyalty program, 🎫 deposit tickets with expiration
- ⏳ Possible extraction of the `auth` and `product` modules into independent services if the workload justifies it

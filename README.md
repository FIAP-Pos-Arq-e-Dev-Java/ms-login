# Pós Tech FIAP - Tech Challenge

## Architecture and Java Development

### WeFood - Restaurant Management System

---

## Overview

The **Tech Challenge** is a hands-on project from FIAP’s postgraduate course in Java Architecture and Development, aiming to create a restaurant management system, developed in phases.

> **Main goal:**
> Allow multiple restaurants to use a single, efficient system, reducing costs and offering a great experience for restaurants and customers alike.

---

## Phase 1 Objective

Develop a robust and complete backend using **Spring Boot**, focused on user management. The implemented operations include:

- User creation
- User data updating
- User deletion
- Login validation

The project is Docker-ready, uses Docker Compose for orchestration, and is integrated with a relational database (PostgreSQL or H2).

---

## Features

### User Management

- **Registration fields:**
  - User:
      - Name (`String`)
      - Email (`String`)
      - Username (`String`)
      - Password (`String`)
      - Role (`Enum`)
      - Last update date (`Date`)
      - Address (`Address`)
        - Street (`String`)
        - Number (`String`)
        - Complement (`String`)
        - City (`String`)
        - State (`String`)

- **Password change**: Users can change their passwordEncoder.
- **Login validation**: Checks user credentials.
- **User deletion**: Remove registered users.

### User Roles

- Restaurant Owner
- Customer
- Admin

---

## Architecture

The backend is developed in **Spring Boot**, follows **SOLID** principles, and uses **Hexagonal Architecture** and **Clean Architecture**.
*Business rules are isolated from external dependencies.*

## Modular Design

> Communication between modules is done via interfaces, so each module can be developed and tested independently.

## Key Modules

- <b>infrastructure/config:</b>
    - <b>SwaggerConfig</b> - API documentation configuration (Swagger/OpenAPI)
    - <b>AdminUserInitializer</b> - Initializes admin user(s) at startup

- <b>adapters/inbound/controller:</b>
    - <b>AuthController</b> - Handles POST /login (JWT authentication)
    - <b>UserController</b> - Handles GET, POST, PUT, DELETE /users endpoints for user management with access control

- <b>domain/user:</b>
    - <b>User</b> - Main user domain model/entity
    - <b>Id, Name, Email, Username, Password</b> - Value objects or user fields
    - <b>Role</b> - User role enum (ADMIN, CUSTOMER, etc)
    - <b>Address</b> - Value object/entity for user addresses

- <b>dto:</b>
    - <b>UserDtoRequest</b> - Used for creating/updating a user (client → server)
    - <b>UserDtoResponse</b> - Used in user API responses (server → client)
    - <b>AddressDto</b> - Used as part of the address payload

- <b>infrastructure/exceptions:</b>
    - <b>ApiError</b> - Standard API error response body
    - <b>GlobalExceptionHandler</b> - Handles and maps exceptions to HTTP responses
    - <b>UserAlreadyExistsException, UserNotFoundException</b> - Specific domain exceptions

- <b>utils/mapper:</b>
    - <b>UserMapper</b> - Maps between User/UserDto and entity/domain
    - <b>AddressMapper</b> - Maps between Address/AddressDto and entity/domain

- <b>adapters/outbound/repositories/entities:</b>
    - <b>JpaUserEntity, JpaAddressEntity</b> - JPA entity mappings for persistence
- <b>adapters/outbound/repositories:</b>
    - <b>JpaUserRepository</b> - Spring Data repository interface
    - <b>UserRepositoryImpl</b> - Implementation for domain persistence

- <b>adapters/outbound/password:</b>
    - <b>BcryptPasswordEncoderEncoderAdapter, PasswordEncoder</b> - Password hashing/encoding abstractions

- <b>security:</b>
    - <b>SecurityConfig</b> - Overall security configuration (roles, HTTP security)
    - <b>JwtAuthenticationFilter</b> - Parses JWT from requests
    - <b>JwtUtil</b> - Signs, validates, and extracts claims from JWTs
    - <b>MyUserDetails</b> - Custom user principal for Spring Security
    - <b>MyUserDetailsService</b> - Loads users for authentication
    - <b>SecurityExceptionHandlerConfig</b> - Security-specific exception handling
    - <b>SecurityUtil</b> - Helper methods for current authentication

- <b>application/usecases:</b>
    - <b>UserUseCase</b> - Use case/service interface
    - <b>UserUsecaseImpl</b> - Main business service implementation

---

## 🛠️ Technology Stack

- **Java 21**
- **Spring Boot** (core app)
- **Spring Data JPA** (data access)
- **Spring Security** (auth/authz)
- **H2** / **PostgreSQL** (databases)
- **Docker** / **Docker Compose**
- **MapStruct** (object mapping)
- **Swagger** (API docs)
- **Mockito** (mocking)
- **JUnit 5** (tests)
- **Postman** (manual API testing)

---

## 🔧 Configurations

- **Default Admin User:**
    - `login`: `admin`
    - `passwordEncoder`: Value from `admin.passwordEncoder` in `application.properties`

- **JWT Secret:**
    - Value from `jwt.secret` in `application.properties`

- **Database:**
  - Env`SPRING_PROFILES_ACTIVE=postgres` to use PostgreSQL
  - Env`SPRING_PROFILES_ACTIVE=h2` to use in memory H2
---

## 🚀 Running the Project

### 1. Clone the Repository

```bash
git clone https://github.com/dpcamargo/fiap-wefood.git
```

### 2. Go to the Project Directory

```bash
cd wefood
```

### 3. Start WeFood

```bash
docker-compose -f docker-compose-h2.yaml up -d
````
or
```
docker-compose -f docker-compose-postgres.yaml up -d
```

### 4. Import Collection

```bash
Import Postman Collection from /postman/WeFood.json
```

---

## 📁 Project Structure

```plaintext
wefood/
├── Dockerfile             
├── docker-compose-h2.yaml       
├── docker-compose-postgres.yaml
├── docs/
│   └── ..
├── postman/
│   └── WeFood.json
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── br/com/fiap/wefood/
    │   │       ├── adapters/
    │   │       │   ├── inbound/controller/
    │   │       │   │   ├── AuthController.java
    │   │       │   │   └── UserController.java
    │   │       │   └── outbound/
    │   │       │       ├── password/
    │   │       │       └── repositories/
    │   │       │           ├── entities/
    │   │       │           │   ├── JpaAddressEntity.java
    │   │       │           │   └── JpaUserEntity.java
    │   │       │           ├── JpaUserRepository.java
    │   │       │           └── UserRepositoryImpl.java
    │   │       ├── application/usecases/
    │   │       │   ├── UserUseCase.java
    │   │       │   └── UserUsecaseImpl.java
    │   │       ├── domain/user/
    │   │       │   ├── Address.java
    │   │       │   ├── Role.java
    │   │       │   ├── User.java
    │   │       │   └── UserRepository.java
    │   │       ├── dto/
    │   │       │   ├── AddressDto.java
    │   │       │   ├── UserDtoRequest.java
    │   │       │   └── UserDtoResponse.java
    │   │       ├── infrastructure/
    │   │       │   ├── config/
    │   │       │   │   ├── AdminUserInitializer.java
    │   │       │   │   └── SwaggerConfig.java
    │   │       │   └── exceptions/
    │   │       │       ├── ApiError.java
    │   │       │       ├── GlobalExceptionHandler.java
    │   │       │       ├── UserAlreadyExistsException.java
    │   │       │       └── UserNotFoundException.java
    │   │       ├── security/
    │   │       │   ├── JwtAuthenticationFilter.java
    │   │       │   ├── JwtUtil.java
    │   │       │   ├── MyUserDetails.java
    │   │       │   ├── MyUserDetailsService.java
    │   │       │   ├── SecurityConfig.java
    │   │       │   ├── SecurityExceptionHandlerConfig.java
    │   │       │   └── SecurityUtil.java
    │   │       ├── utils/mapper/
    │   │       │   ├── AddressMapper.java
    │   │       │   └── UserMapper.java
    │   │       └── WefoodApplication.java
    │   └── resources/
    │       ├── application.properties
    │       ├── application-h2.properties
    │       ├── application-postgres.properties
    │       ├── static/
    │       └── templates/
    └── test/java/br/com/fiap/wefood/
```

---

## Endpoints

- Postman collection: `/postman/wefood.json`
- Application: [http://localhost:8080](http://localhost:8080)
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI Docs: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

### User Controller

#### GET /users

*   **Summary:** Get all users. <b>Administrators only.</b>
*   **Description:** Returns a <b>paginated list</b> of all users.
*   **Operation ID:** `getUsers`
*   **Tags:** `user-controller`
*   **Query Parameters:**
    *   `page` (optional, integer) - Page index, default 0.
    *   `size` (optional, integer) - Page size, default 10.
*   **Responses:**
    *   `200 OK`: Users retrieved successfully. Returns an array of <b>UserDtoResponse</b> objects.

#### PUT /users

*   **Summary:** Update user content. <b>Only admin can change other users. User can change itself, except for role.</b>
*   **Description:** Updates user data.
*   **Operation ID:** `updateUser`
*   **Tags:** `user-controller`
*   **Request Body:** `application/json` with schema <b>UserDtoRequest</b>
*   **Responses:**
    *   `200 OK`: User updated successfully. Returns a <b>UserDtoResponse</b> object.

#### POST /users

*   **Summary:** Create a new user.<b> Anyone can create a CUSTOMER user. Admin can create any role.</b>
*   **Description:** Creates a new user.
*   **Operation ID:** `createUser`
*   **Tags:** `user-controller`
*   **Request Body:** `application/json` with schema <b>UserDtoRequest</b>
*   **Responses:**
    *   `201 Created`: User created successfully. Returns a <b>UserDtoResponse</b> object.

#### GET /users/{user_id}

*   **Summary:** Get user by ID. <b>User can only get their own data. Admin can get any user.</b>
*   **Description:** Returns a user by their <b>UUID</b>.
*   **Operation ID:** `getUser`
*   **Tags:** `user-controller`
*   **Parameters:**
    *   `user_id` (path, required): <b>string (UUID)</b>
*   **Responses:**
    *   `200 OK`: User retrieved successfully. Returns a <b>UserDtoResponse</b> object.

#### DELETE /users/{user_id}

*   **Summary:** Deletes a user. <b>Only admin can delete other users. User can delete themselves.</b>
*   **Description:** Soft deletes a user (by unique identifier).
*   **Operation ID:** `deleteUser`
*   **Tags:** `user-controller`
*   **Parameters:**
    *   `user_id` (path, required): <b>string (UUID)</b>
*   **Responses:**
    *   `204 No Content`: User deleted successfully.

### Auth Controller

#### POST /login

*   **Summary:** Logs in a user.
*   **Operation ID:** `login`
*   **Tags:** `auth-controller`
*   **Request Body:** `application/json` with schema <b>LoginRequestDTO</b>
*   **Responses:**
    *   `200 OK`: Returns a <b>LoginResponse</b> object.

## Schemas

### UserDtoRequest

``` json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "string",
      "format": "uuid"
    },
    "name": {
      "type": "string"
    },
    "email": {
      "type": "string"
    },
    "username": {
      "type": "string"
    },
    "password": {
      "type": "string"
    },
    "role": {
      "type": "string"
    },
    "address": {
      "$ref": "#/components/schemas/AddressDto"
    }
  }
}
```

### UserDtoResponse

```
{
  "type": "object",
  "properties": {
    "id": {
      "type": "string",
      "format": "uuid"
    },
    "name": {
      "type": "string"
    },
    "email": {
      "type": "string"
    },
    "username": {
      "type": "string"
    },
    "role": {
      "type": "string"
    },
    "createdAt": {
      "type": "string",
      "format": "date-time"
    },
    "modifiedAt": {
      "type": "string",
      "format": "date-time"
    },
    "address": {
      "$ref": "#/components/schemas/AddressDto"
    }
  }
}
```

### AddressDto

``` json
{
  "type": "object",
  "properties": {
    "id": { "type": "string", "format": "uuid" },
    "street": { "type": "string" },
    "number": { "type": "string" },
    "complement": { "type": "string" },
    "city": { "type": "string" },
    "state": { "type": "string" }
  }
}
```

### LoginRequestDTO

``` json
{
  "type": "object",
  "properties": {
    "username": { "type": "string" },
    "password": { "type": "string" }
  }
}
```
### LoginResponse

```json
{
  "type": "object",
  "properties": {
    "token": { "type": "string" },
    "username": { "type": "string" },
    "userId": { "type": "string" },
    "expiresAt": { "type": "string" }
  }
}
```

# 🥡 <b>WeFood - Postman API Collection Documentation</b>

This API supports typical <b>user management operations</b> (login, create user, update, delete, etc.) for two roles: <code>ADMIN</code> and <code>USER</code>. The list below explains how each call works, what you have to provide, and what you'll get back.

-------------------------------------------------------------
## 🤖 SECTION 1: AS ADMIN 

### 1. [ADMIN] Login
- Endpoint: POST /login
- Description: Authenticates as admin user, returning a JWT token in the response.
- Request Body Example:
  {
  "username": "admin",
  "password": "Password1@"
  }
- Usage: Use this call first. The token from the response will be stored in a Postman variable <code>jwt_admin</code> to be used in the following admin requests.
- Response:
  {
  "token": "(JWT_HERE)",
  "username": "admin",
  "expiresAt": "...",
  "userId": "(UUID)"
  }

### 2. [ADMIN] Create Customer
- Endpoint: POST /users
- Auth: Bearer token {{jwt_admin}}
- Description: Admin creates a new CUSTOMER user.
- Request Body Example:
  {
  "name": "John Doe",
  "email": "john@doe.com.br",
  "username": "johnny",
  "password": "Password1@",
  "role": "CUSTOMER",
  "address": {
  "street": "Baker Street",
  "number": "221B",
  "complement": "",
  "city": "London",
  "state": "BL"
  }
  }
- Result: Returns the created user (with id and address).

### 3. [ADMIN] Create Restaurant Owner
- Endpoint: POST /users
- Auth: Bearer token {{jwt_admin}}
- Description: Admin creates a new RESTAURANT_OWNER user.
- Request: Like above, but role is RESTAURANT_OWNER.
- Result: Returns the created restaurant owner user.

### 4. [ADMIN] Create Admin
- Endpoint: POST /users
- Auth: Bearer token {{jwt_admin}}
- Description: Admin creates another admin.
- Request: Like above, but role is ADMIN.
- Result: Returns new admin.

### 5. [ADMIN] Get All Users
- Endpoint: GET /users?page=0&size=10
- Auth: Bearer token {{jwt_admin}}
- Description: Admin retrieves a paginated list of all users.
- Params:
  page: Zero-based page index (default 0);
  size: Page size (default 10).
- Result: Array of user info (paginated).

### 6. [ADMIN] Get User From Id
- Endpoint: GET /users/:id
- Auth: Bearer token {{jwt_admin}}
- Description: Admin retrieves a user by specific UUID id.
- Path: Replace :id with user's UUID.
- Result: The user object for matching id.

### 7. [USER] Update User
- Endpoint: PUT /users
- Auth: Bearer token {{jwt_admin}}
- Description: Admin updates an existing user.
- Request Body Example:
  {
  "id": "4fd90a00-27ea-4c05-afd6-ba3e9d5cc33e",
  "name": "John DoeX",
  "email": "john@doe.com.br",
  "username": "johnny",
  "password": "Password1@",
  "role": "CUSTOMER",
  "address": {
  "street": "Baker Street",
  "number": "221B",
  "complement": "",
  "city": "London",
  "state": "BL"
  }
  }
- Result: Returns the updated user info.

### 8. [USER] Delete User
- Endpoint: DELETE /users/:id
- Auth: Bearer token {{jwt_admin}}
- Description: Admin deletes a user by UUID.
- Body: Often not needed for DELETE (UUID is in path).
- Result: 204 No Content if successful.

-------------------------------------------------------------
## 👨🏻‍💼 SECTION 2: AS USER

### 1. [USER] Create User
- Endpoint: POST /users
- Auth: None
- Description: Anyone can register a new CUSTOMER.
- Request Body Example:
  {
  "name": "Jane",
  "email": "jane@uol.com.br",
  "username": "jane",
  "password": "Password1@",
  "role": "CUSTOMER",
  "address": {
  "street": "Baker Street",
  "number": "221C",
  "complement": "Apt 2",
  "city": "London",
  "state": "BL"
  }
  }
- Result: Returns the created user object.

### 2. [USER] Login
- Endpoint: POST /login
- Auth: None
- Description: Logs in as a regular user, returns a JWT as response.
- Request:
  {
  "username": "jane",
  "password": "Password1@"
  }
- Result: Response with JWT token (stored as jwt_user).

### 3. [USER] Get All Users (ERROR)
- Endpoint: GET /users
- Auth: Bearer token {{jwt_user}}
- Description: Not allowed for regular users. Will return an error (403 Forbidden).

### 4. [USER] Get User From Id
- Endpoint: GET /users/:id
- Auth: Bearer token {{jwt_user}}
- Description: Gets a user's own info (OK only if UUID matches the logged-in user; 403 for others).
- Result: Returns user info.

### 5. [USER] Update User
- Endpoint: PUT /users
- Auth: Bearer token {{jwt_user}}
- Description: User updates their own info.
- Request: Must include id and all relevant user information.
- Result: Returns updated user info.

### 6. [USER] Delete User
- Endpoint: DELETE /users/:id
- Auth: Bearer token {{jwt_user}}
- Description: User deletes themselves.
- Result: 204 No Content if successful.

-------------------------------------------------------------
## ⚠️ SECTION 3: ERROR (Generic) 

- Any call with ERROR in the name simulates unauthorized/invalid usage.
- Expect 401/403/409, depending on the scenario.

-------------------------------------------------------------
## 🔡 DATA FORMAT SUMMARY

- Create/Update User: All user info + nested address.
- Login: Username and password.
- Token: Returned on login, used as Bearer in Authorization header for other requests.

-------------------------------------------------------------
## 🔀 TYPICAL FLOW

### Admin:
1. Login as admin (/login)
2. Use jwt_admin to create users, get all users, update, delete.

### User:
1. Register (/users)
2. Login (/login) → get jwt_user
3. Get/update/delete own account using token

-------------------------------------------------------------
## 💬 COMMON RESPONSE BODIES

- Success:
  User object: id, name, email, username, role, address:{...}
- Login success:
  token, username, expiresAt, userId
- Error:
  status, error, message, path

-------------------------------------------------------------
## ❗ Tips

- Always use the correct Bearer token (jwt_admin or jwt_user) when calling protected endpoints.
- User endpoints (except registration and login) require JWT.
- Admin can do everything; user can only manage themselves.
- For update or delete, always use correct id in the path and body.

## 💡 TODO

### 📖 Documentation

- Document **JWT structure** (header, claims, signature).

### 🧪 Testing & Quality

- Include a **code coverage badge/instructions**.
- Add **sample integration tests** (especially for authentication/user management).

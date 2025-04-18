# ParCours API Documentation

This documentation describes how to interact with the ParCours API using the provided DTOs.

## Table of Contents
### AUTH
1. [RegisterRequestDTO](#registerrequestdto)
2. [LoginRequestDTO](#loginrequestdto)

### ENTITIES
1. [SessionDTO](#sessiondto)
2. [UserDTO](#userdto)
3. [SubjectDTO](#subjectdto)

---

## AUTH

### 1. RegisterRequestDTO

#### Description
Request to register a new user.  
Accessible only by an administrator.

#### Structure
```json
{
    "first_name": String,
    "last_name": String,
    "password": String
}
```

#### Example
```json
{
    "first_name": "John",
    "last_name": "Doe",
    "password": "password123"
}
```

---

### 2. LoginRequestDTO

#### Description
Request to log in to the API.
Accessible by everyone.

#### Structure
```json
{
    "username": String,
    "password": String
}
```

#### Example
```json
{
    "username": "johndoe",
    "password": "password123"
}
```

---

## ENTITIES

### 1. SessionDTO

#### Description
Represents a session with its main information.

#### Structure
```json
{
    "id": Long,
    "label": String,
    "beginsAt": "YYYY-MM-DDTHH:MM:SS",
    "endsAt": "YYYY-MM-DDTHH:MM:SS",
    "users": [UserDTO],
    "subjects": [SubjectDTO]
}
```

#### Example
```json
{
    "id": 1,
    "label": "Grammar lesson",
    "beginsAt": "2025-03-23T14:00:00",
    "endsAt": "2025-03-23T15:00:00",
    "users": [],
    "subjects": []
}
```

---

### 2. UserDTO

#### Description
Represents a user with their main information.

#### Structure
```json
{
    "id": Long,
    "first_name": String,
    "last_name": String,
    "username": String,
    "roles": [Role],
    "sessions": [Session]
}
```

#### Example
```json
{
    "id": 1,
    "first_name": "John",
    "last_name": "Doe",
    "username": "johndoe",
    "roles": ["USER"],
    "sessions": []
}
```

---

### 3. SubjectDTO

#### Description
Represents a subject associated with a session.

#### Structure
```json
{
    "id": Long,
    "label": String
}
```

#### Example
```json
{
    "id": 1,
    "label": "Mathematics"
}
```

---

## Notes
- The `id` fields are automatically generated by the system.
- Dates must follow the ISO 8601 format.
- Relationships between entities (e.g., `users` in `SessionDTO`) are represented as nested objects.
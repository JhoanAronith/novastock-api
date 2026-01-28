# NovaStock API 🛒

Backend REST API para la gestión de productos, inventario y pedidos de un e-commerce.

🚧 **Proyecto en desarrollo (Work in Progress)**  
Este proyecto se encuentra actualmente en fase de construcción y mejora continua.  
Las funcionalidades se implementan de forma incremental siguiendo buenas prácticas de arquitectura y calidad de software.

---

## 📌 Objetivo del Proyecto

Construir el **core backend de un e-commerce**, enfocado en:

- Escalabilidad
- Seguridad
- Integridad de datos
- Buenas prácticas profesionales en Java y Spring Boot

No incluye frontend. El enfoque es **100% backend**.

---

## 🧱 Arquitectura

El proyecto sigue una **arquitectura en capas (Clean Architecture)**:

- **Controller:** Manejo de peticiones HTTP
- **Service:** Lógica de negocio
- **Repository:** Acceso a datos (JPA)
- **Entity:** Modelo de dominio
- **DTO:** Transferencia de datos hacia/desde la API

Regla clave:
> ❌ Nunca se exponen entidades JPA directamente en los controllers.

---

## ⚙️ Stack Tecnológico

| Categoría | Tecnología                  |
|--------|-----------------------------|
| Lenguaje | Java 17+                    |
| Framework | Spring Boot 3               |
| Persistencia | Spring Data JPA (Hibernate) |
| Base de Datos | PostgreSQL                  |
| Migraciones | Flyway                      |
| Seguridad | Spring Security + JWT       |
| Mapeo | MapStruct                   |
| Documentación | Swagger / OpenAPI           |
| Contenedores | Docker                      |

---

## 📦 Funcionalidades (Roadmap)

### ✔️ Implementadas / En desarrollo
- Configuración base del proyecto
- Docker + PostgreSQL
- Migraciones con Flyway
- CRUD de Productos (en progreso)

### 🔜 Próximas
- Gestión de Inventario
- Procesamiento de Pedidos
- Autenticación y Autorización con JWT
- Auditoría (created_at, updated_at)
- Pruebas unitarias e integración

---

## 🗄️ Modelo de Datos Inicial

- **Users**
- **Products**
- **Orders**
- **Order_Items**

---

## 🔐 Seguridad

- Autenticación stateless con JWT
- Contraseñas cifradas con BCrypt
- Roles:
    - `ADMIN`: gestión de productos e inventario
    - `CUSTOMER`: consulta y pedidos

---

## 🐳 Levantar el proyecto con Docker

```bash
docker-compose up -d

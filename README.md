# NovaStock API 🛒

**Backend REST API de nivel profesional** para la gestión integral de inventarios y e-commerce.

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![JUnit 5](https://img.shields.io/badge/Tests-JUnit%205%20%26%20Mockito-red?style=flat-square&logo=junit5)](https://junit.org/junit5/)
[![PostgreSQL](https://img.shields.io/badge/DB-PostgreSQL-blue?style=flat-square&logo=postgresql)](https://www.postgresql.org/)

NovaStock es una solución robusta para el núcleo de un e-commerce, diseñada bajo estándares modernos de desarrollo, con un fuerte enfoque en la **seguridad**, la **integridad del inventario** y la **calidad del código** validada mediante pruebas automatizadas.

---

## 📌 Funcionalidades Destacadas

* **Gestión de Inventario Inteligente:** Lógica de negocio avanzada para el ajuste de stock, validaciones de disponibilidad y restauración automática de productos en cancelaciones.
* **Seguridad con JWT:** Autenticación stateless y autorización basada en roles (`ADMIN` y `CLIENT`) utilizando Spring Security.
* **Gestión de Pedidos:** Flujo completo desde la creación de la orden hasta la actualización de estados y gestión de ítems.
* **Documentación Interactiva:** Contrato de API documentado y listo para probar con Swagger/OpenAPI.
* **Persistencia y Migraciones:** Base de datos relacional PostgreSQL con control de versiones de esquema mediante Flyway.



---

## 🧱 Arquitectura y Calidad

El proyecto se rige por una **Arquitectura en Capas (Clean Architecture)**, asegurando un bajo acoplamiento y alta cohesión.

### 🧪 Estrategia de Testing (Unit Testing)
Se implementó una suite de pruebas exhaustiva que garantiza la fiabilidad del sistema:
* **Mockito & JUnit 5:** Simulación de dependencias para aislar la lógica de los servicios.
* **Cobertura Crítica:** Pruebas unitarias en `ProductService`, `OrderService`, `CategoryService` y `AuthService`.
* **Validación de Reglas de Negocio:** Tests específicos para stock insuficiente, nombres duplicados y manejo de excepciones personalizadas (`ResourceNotFoundException`, `AlreadyExistsException`).



---

## ⚙️ Stack Tecnológico

| Categoría | Tecnología |
| :--- | :--- |
| **Lenguaje** | Java 21 |
| **Framework Principal** | Spring Boot 3.5 |
| **Seguridad** | Spring Security + JWT + BCrypt |
| **Persistencia** | Spring Data JPA / Hibernate |
| **Base de Datos** | PostgreSQL |
| **Migraciones** | Flyway |
| **Testing** | JUnit 5, Mockito, AssertJ |
| **Documentación** | Swagger / OpenAPI 3 |

---

## 🛠️ Instalación y Ejecución

### Requisitos
* Docker y Docker Compose
* JDK 21
* Maven

### Pasos
1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/novastock-api.git](https://github.com/tu-usuario/novastock-api.git)
    cd novastock-api
    ```

2.  **Levantar infraestructura (Base de Datos):**
    ```bash
    docker-compose up -d
    ```

3.  **Ejecutar Pruebas Unitarias:**
    ```bash
    mvn test
    ```

4.  **Iniciar la Aplicación:**
    ```bash
    mvn spring-boot:run
    ```

---

## 🚀 Documentación de la API
Una vez iniciada la aplicación, explora los endpoints de forma interactiva en:
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)



---

## 📂 Estructura del Proyecto
```text
src/main/java/com/jhoan/novastock/
├── config/         # Seguridad JWT y OpenAPI
├── controllers/    # Endpoints REST
├── dtos/           # Request/Response Data Transfer Objects
├── entities/       # Modelos de Dominio (JPA)
├── exceptions/     # Custom Exceptions y Global Handler
├── repositories/   # Abstracción de Datos
└── services/       # Lógica de Negocio e Implementaciones
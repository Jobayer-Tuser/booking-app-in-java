# Booking App

A robust Spring Boot application for managing bookings, orders, and products with integrated payment processing and user authentication.

## 🛠️ Tech Stack

- **Java**: 25
- **Framework**: Spring Boot 3.x
- **Database**: MySQL
- **Migration**: Flyway
- **Authentication**: JWT (JSON Web Tokens)
- **Payment Processing**: Stripe
- **Querying**: QueryDSL
- **Documentation**: SpringDoc (Swagger UI)

## ✨ Features

- **User Authentication**: Secure login, registration, and token refresh mechanisms using JWT.
- **Product Management**: Browse and search products.
- **Cart & Checkout**: Manage shopping carts and process checkouts.
- **Order Management**: Create and view orders.
- **Payment Integration**: Secure payments via Stripe.
- **File Uploads**: Handle file attachments.
- **Role-Based Access**: Authorization for different user roles.
- **Database Migrations**: Automated schema management with Flyway.

## 🚀 Getting Started

### Prerequisites

- **Java JDK 25** or higher
- **MySQL** Server
- **Maven** (optional, wrapper included)

### Access Points
| URL | Description |
|-----|-------------|
| http://localhost:8080/admin/dashboard | Admin Panel (Thymeleaf) |
| http://localhost:5173 | React Customer App |
| http://localhost:8080/swagger-ui.html | API Documentation |
| http://localhost:8080/h2-console | Database Console (username: sa, no password) |

### ⚙️ Environment Configuration

The application requires specific environment variables to run. You can set them in your IDE or environment, or create a `.env` file (if supported by your setup, though `application.yaml` reads from env vars directly).

| Variable | Description |
|----------|-------------|
| `DB_NAME` | MySQL Database Name |
| `DB_USERNAME` | Database Username |
| `DB_PASSWORD` | Database Password |
| `DB_DRIVER` | Database Driver (e.g., `com.mysql.cj.jdbc.Driver`) |
| `JWT_SECRET` | Secret key for signing JWTs |
| `STRIPE_SANDBOX_SECRET_KEY` | Stripe Secret Key for testing |
| `STRIPE_WEBHOOK_SECRET_KEY` | Stripe Webhook Secret Key |

### 🏃‍♂️ Running the Application

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd booking
   ```

2. **Configure Database**:
   Ensure your MySQL server is running and the database specified in `DB_NAME` exists.

3. **Build and Run**:
   Use the Maven wrapper to run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or build it first:
   ```bash
   ./mvnw clean install
   java -jar target/booking-0.0.1-SNAPSHOT.jar
   ```

The application will start on port `9000` (default per configuration).

## 📖 API Documentation

The application integrates with SpringDoc to provide interactive API documentation.

- **Swagger UI**: [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html)
- **OpenAPI Docs**: [http://localhost:9000/v3/api-docs](http://localhost:9000/v3/api-docs)

### REST API Reference

| Category | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **Auth** | `POST` | `/api/auth/login` | Authenticate user and get token |
| | `POST` | `/api/auth/token-refresh` | Refresh access token |
| | `GET` | `/api/auth/validated/me` | Get current authenticated user |
| **Users** | `GET` | `/api/users` | List all users |
| | `GET` | `/api/users/{id}` | Get user details |
| | `POST` | `/api/users/register` | Register a new user |
| | `PATCH` | `/api/users/{id}` | Update user details |
| | `GET` | `/api/users/cursor` | Get users with cursor pagination |
| | `GET` | `/api/users/sort` | Get users with sorting |
| **Orders** | `GET` | `/api/orders` | List all orders |
| **Carts** | `POST` | `/api/carts` | Create a new cart |
| | `GET` | `/api/carts/{cartId}` | Get cart details |
| | `POST` | `/api/carts/{cartId}/items` | Add item to cart |
| | `PATCH` | `/api/carts/{cartId}/items/{productId}` | Update cart item quantity |
| | `DELETE` | `/api/carts/{cartId}/items/{productId}` | Remove item from cart |
| | `DELETE` | `/api/carts/{cartId}/items` | Clear entire cart |
| **Products** | `GET` | `/api/products` | List all products |
| | `POST` | `/api/products` | Create a new product |
| | `PATCH` | `/api/products/{productId}` | Update a product |
| | `DELETE` | `/api/products/{productId}/delete` | Delete a product |
| **Checkout** | `POST` | `/api/checkout` | Process checkout |
| | `POST` | `/api/checkout/webhook` | Stripe webhook endpoint |
| **Properties** | `POST` | `/api/properties` | Create a new property |
| | `GET` | `/api/properties/search` | Search for properties |
| | `GET` | `/api/properties/{propertyId}` | Get property details |
| | `GET` | `/api/properties/fetch/{propertyId}` | Get property summary (QueryDSL) |
| **Roles** | `POST` | `/api/roles` | Create a new role |
| **Misc** | `POST` | `/api/multitask/file-upload` | Upload an image file |
| | `GET` | `/api/multitask/view-image/{file_name}` | View uploaded image |
## 📁 Project Structure
```
booking/      # Spring Boot 4.0 application
│   ├── src/main/java/com/owasp/ecommerce/
|        |--- config                # Security, CORS, Data initialization
|        |--- execptions            # Global exception handler
|        |--- console
|        |--- validation
|        |--- utils
|        |--- auth                    # JWT, authentication
|        |   |--- AuthController.java
|        |   |--- AuthService.java
|        |   |--- SecurityConfig.java
|        |   |--- Jwt.java
|        |   |--- JwtConfig.java
|        |   |--- JwtService.java
|        |   |--- JwtAuthFilter.java
|        |--- users                    # User domain and related class
|        |   |--- User.java
|        |   |--- UserRepository.java
|        |   |--- UserController.java
|        |   |--- UserService.java
|        |   |--- UserServiceImpl.java
|        |   |--- UserMapper.java
|        |   |--- UserDto.java
|        |--- carts                    # Cart domain
|        |   |--- Cart.java
|        |   |--- CartController.java
|        |   |--- CartService.java
|        |   |--- CartServiceImpl.java
|        |   |--- CartRepositoy.java
|----------- src/main/resources
             |--- templates
             |--- application.yml
```

## 🗄️ Database
### Java-Based Migrations
The project uses a custom `Schema` helper within standard Flyway Java migrations to simplify DDL operations.

**Example Migration (`V17__CreateTableForExample.java`):**
```java
public class V17__CreateTableForExample extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws SQLException, IOException {
        Schema.create("examples", table -> {
            table.id();
            table.foreignId("user_id").constrained("users").onUpdateCascade().onDeleteRestrict();
            table.string("email").unique();
            table.enumeration("roles", "Admin", "users", "Editor").defaultValue("users");
            table.timestamps();
        }, context);
    }
}
```

## 🌱 Data Seeding

Data seeding is handled via a flexible Factory and Seeder pattern, making it easy to generate mock data.

### Factories
Factories define the blueprint for your entities using `net.datafaker`.

**Example Factory (`UserFactory.java`):**
```java
@Component
@RequiredArgsConstructor
public class UserFactory extends Factory<User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User definition() {
        return User.builder()
            .name(faker.name().name())
            .email(faker.internet().emailAddress())
            .password(passwordEncoder.encode("SuperSecretPass"))
            .build();
    }
}
```

### Seeders
Seeders orchestrate the data creation using Factories.

**Example Seeder Registration (`DatabaseSeeder.java`):**
```java
public void seed() {
    this.call(
            RolesSeeder.class,
            UserSeeder.class,
            ApartmentTypeSeeder.class
            // ... other seeders
    );
}
```
**Endpoint**: `GET /api/users/cursor?cursor={last_id}&pageSize={size}`


## ⚡️ Advanced Features

### Cursor Pagination
The application implements efficient cursor-based pagination for large datasets, as demonstrated in the User service.

**Logic Pattern (`UserServiceImpl.java`):**
```java
public CursorPageResponse<User> cursorPaginationPattern(Long cursor, int pageSize) {
    Pageable pageable = PageRequest.of(0, pageSize);
    List<User> users = userRepository.cursorPaginationPattern(cursor, pageable);
    boolean hasNext = users.size() == pageSize;

    Long nextCursor = hasNext
            ? users.getLast().getId()
            : null;
    return new CursorPageResponse<>(users, pageSize, nextCursor, hasNext);
}
```
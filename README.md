# Order Management System

A Spring Boot based e-commerce order management system with key features including user management, order processing, inventory management, and payment processing using Stripe.

## Features

- User Management
  - User registration
  - Email uniqueness validation
  
- Product Management
  - Create and manage products
  - Track inventory levels
  
- Order Management
  - Create orders
  - Track order status
  - Manage order items
  
- Payment Processing
  - Stripe integration for payments
  - Payment intent creation
  
- Error Handling
  - Global exception handling
  - Proper error responses
  
## Technical Stack

- Java 11
- Spring Boot 2.7.0
- Spring Data JPA
- H2 Database
- Stripe API
- Lombok
- Maven

## API Endpoints

### User Management
- POST /api/users/register - Register a new user

### Product Management
- GET /api/products - Get all products
- GET /api/products/{id} - Get a specific product
- POST /api/products - Create a new product
- PUT /api/products/{id} - Update a product

### Order Management
- POST /api/orders - Create a new order
- GET /api/orders/{orderId} - Get order details

## Getting Started

1. Clone the repository
2. Set your Stripe API key in application.properties:
   ```
   stripe.api.key=add your api key here
   ```
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Database

The application uses H2 in-memory database. The H2 console is enabled and can be accessed at:
http://localhost:8080/h2-console

Database Configuration:
- URL: jdbc:h2:mem:ordermanagement
- Username: sa
- Password: (empty)

## Error Handling

The application includes comprehensive error handling:
- Invalid requests return 400 Bad Request
- Resource not found returns 404 Not Found
- Server errors return 500 Internal Server Error
- Custom exceptions for business logic (InsufficientStockException, PaymentFailedException)
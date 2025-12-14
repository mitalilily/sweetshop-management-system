# sweetshop-management-system
A basic Sweet Shop management System project
=======
# 🍬 Sugar Rush - Sweet Shop Management System

A full-stack web application for managing an Indian sweets shop, built with **Spring Boot** (backend) and **React** (frontend), following **Test-Driven Development (TDD)** principles.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![React](https://img.shields.io/badge/React-18.2.0-blue)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green)
![TypeScript](https://img.shields.io/badge/TypeScript-5.0-blue)
![JWT](https://img.shields.io/badge/JWT-Auth-orange)

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Setup Instructions](#-setup-instructions)
- [API Endpoints](#-api-endpoints)
- [Screenshots](#-screenshots)
- [Development Approach](#-development-approach)
- [My AI Usage](#-my-ai-usage)
- [Contributing](#-contributing)
- [License](#-license)

## 🎯 Project Overview

**Sugar Rush** is a comprehensive sweet shop management system that allows users to browse and purchase authentic Indian sweets, while administrators can manage inventory, add new products, and restock items. The application demonstrates modern full-stack development practices including:

- **Test-Driven Development (TDD)** with Red-Green-Refactor cycle
- **RESTful API** design with JWT authentication
- **Role-based access control** (User vs Admin)
- **Modern React** frontend with TypeScript
- **MongoDB** database integration
- **Clean code** principles and SOLID design patterns

## ✨ Features

### 👤 User Features

- **User Registration & Login** with JWT authentication
- **Browse Sweets** with beautiful card-based layout
- **Search Functionality** to find sweets by name
- **Category Filters** (Wedding, Diwali, Holi, Festival, Birthday, Premium Gift)
- **Purchase Sweets** with real-time inventory updates
- **Quantity Display** with out-of-stock indicators

### 🔐 Admin Features

- **Admin Dashboard** with comprehensive management tools
- **Add New Sweets** with category and pricing
- **Edit Existing Sweets** to update details
- **Delete Sweets** from inventory
- **Restock Inventory** to increase quantities
- **View All Products** in organized table format
- **Switch to User View** to see customer perspective

### 🔒 Security Features

- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control
- Protected API endpoints
- CORS configuration for frontend integration

## 🛠 Technology Stack

### Backend

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - Authentication & Authorization
- **Spring Data MongoDB** - Database integration
- **JWT (jjwt 0.12.3)** - Token-based authentication
- **Maven** - Dependency management
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework

### Frontend

- **React 18.2.0**
- **TypeScript** - Type safety
- **React Router** - Navigation
- **Axios** - HTTP client
- **CSS3** - Standalone stylesheets

### Database

- **MongoDB Atlas** - Cloud database
- **Embedded MongoDB** - For testing

## 📁 Project Structure

```
Java Sweet Shop Management System/
├── server/                          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sweetshop/
│   │   │   │   ├── config/          # Configuration classes
│   │   │   │   ├── controller/      # REST controllers
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── model/           # Entity models
│   │   │   │   ├── repository/      # Data repositories
│   │   │   │   ├── security/        # Security filters
│   │   │   │   └── service/         # Business logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/                    # Test files (TDD)
│   ├── pom.xml
│   └── mvnw                         # Maven wrapper
│
└── client/                          # React Frontend
    ├── src/
    │   ├── components/              # React components
    │   │   ├── Login.tsx & Login.css
    │   │   ├── Register.tsx & Register.css
    │   │   ├── UserDashboard.tsx & UserDashboard.css
    │   │   └── AdminDashboard.tsx & AdminDashboard.css
    │   ├── context/                 # React Context
    │   │   └── AuthContext.tsx
    │   ├── services/                 # API services
    │   │   └── api.ts
    │   └── App.tsx                   # Main app component
    ├── package.json
    └── public/
```

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17** or higher
- **Node.js** (v16 or higher) and **npm**
- **MongoDB Atlas** account (or local MongoDB instance)
- **Git** for version control
- **Maven** (optional - Maven wrapper included)

## 🚀 Setup Instructions

### Backend Setup

1. **Navigate to the server directory:**

   ```bash
   cd server
   ```

2. **Configure MongoDB Connection:**

   Open `src/main/resources/application.properties` and update the MongoDB connection string:

   ```properties
   spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.xxxxx.mongodb.net/?appName=Cluster0
   spring.data.mongodb.database=sweetshop_db
   ```

3. **Build the project:**

   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw clean install

   # Or using Maven directly
   mvn clean install
   ```

4. **Run the application:**

   ```bash
   # Using Maven wrapper
   ./mvnw spring-boot:run

   # Or using Maven
   mvn spring-boot:run
   ```

   The backend server will start on `http://localhost:8080`

5. **Run Tests (TDD verification):**
   ```bash
   ./mvnw test
   ```

### Frontend Setup

1. **Navigate to the client directory:**

   ```bash
   cd client
   ```

2. **Install dependencies:**

   ```bash
   npm install
   ```

3. **Start the development server:**

   ```bash
   npm start
   ```

   The frontend will start on `http://localhost:3000` and automatically open in your browser.

4. **Build for production:**
   ```bash
   npm run build
   ```

## 🔑 Default Admin Credentials

On first startup, a default admin user is automatically created:

- **Username:** `admin`
- **Password:** `admin123`
- **Email:** `admin@sweetshop.com`

**⚠️ Important:** Change these credentials in production!

## 📡 API Endpoints

### Authentication (Public)

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token
- `POST /api/auth/register/admin` - Register admin (requires secret key)

### Sweets (Protected - Requires JWT)

- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/{id}` - Get sweet by ID
- `POST /api/sweets` - Create new sweet (authenticated)
- `PUT /api/sweets/{id}` - Update sweet (authenticated)
- `DELETE /api/sweets/{id}` - Delete sweet (Admin only)
- `GET /api/sweets/search` - Search sweets (name, category, price range)

### Inventory (Protected - Requires JWT)

- `POST /api/sweets/{id}/purchase` - Purchase sweet (decreases quantity)
- `POST /api/sweets/{id}/restock` - Restock sweet (Admin only, increases quantity)

### Example API Usage

**Register a User:**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
```

**Login:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'
```

**Get All Sweets (with JWT token):**

```bash
curl -X GET http://localhost:8080/api/sweets \
  -H "Authorization: Bearer <your-jwt-token>"
```

## 📸 Screenshots

### User Dashboard

<img width="3723" height="1982" alt="image" src="https://github.com/user-attachments/assets/122007b8-eb31-40e6-a3f9-f6b5db7af30a" />
_The user dashboard showing available sweets with search and filter functionality_

### Admin Dashboard

<img width="3734" height="2004" alt="image" src="https://github.com/user-attachments/assets/df72524d-c06c-43f6-82f7-919c1803e101" />
_The admin dashboard with comprehensive sweet management tools_

### Login Page

<img width="1959" height="1885" alt="image" src="https://github.com/user-attachments/assets/cd9795ab-0101-4bcb-a7e6-e26068f77751" />
_User login interface with clean, modern design_

### Register Page

<img width="1723" height="1855" alt="image" src="https://github.com/user-attachments/assets/9aefdb21-0f6c-4841-b69f-23004fce9e60" />
_User registration form with validation_

### Admin Add Sweet

<img width="3690" height="1935" alt="image" src="https://github.com/user-attachments/assets/8033246f-87fe-40d8-84ab-3b22d00d3a61" />
_Admin adding a new sweet to the inventory_


## 🧪 Development Approach

This project follows **Test-Driven Development (TDD)** with a clear Red-Green-Refactor cycle:

### TDD Cycle Example

1. **🔴 Red Phase:** Write failing tests first

   ```java
   @Test
   void testCreateSweet() {
       // Test written before implementation
   }
   ```

2. **🟢 Green Phase:** Implement minimal code to pass tests

   ```java
   public Sweet createSweet(Sweet sweet) {
       return sweetRepository.save(sweet);
   }
   ```

3. **🔵 Refactor Phase:** Improve code while keeping tests green

### Commit History

The git commit history demonstrates the TDD approach:

- `test: add User entity and repository tests (TDD - Red phase)`
- `feat: implement User entity and repository (TDD - Green phase)`
- `test: add authentication service and controller tests (TDD - Red phase)`
- `feat: implement authentication with JWT (TDD - Green phase)`
- And so on...

### Test Coverage

- ✅ Unit tests for all services
- ✅ Integration tests for repositories
- ✅ Controller tests with MockMvc
- ✅ Test configuration using embedded MongoDB

## 🤖 My AI Usage

### AI Tools Used

- **GitHub Copilot** - Code suggestions and autocompletion

### How AI Was Utilized

#### 1. **Project Setup & Architecture**

- AI assisted in setting up the Spring Boot project structure
- Generated Maven configuration with appropriate dependencies

#### 2. **TDD Implementation**

- Generated test scenarios for edge cases and error handling
- Assisted in maintaining Red-Green-Refactor cycle throughout development

#### 3. **Code Generation**

- Generated boilerplate code for entities, repositories, and services

#### 4. **Database Migration**

- AI helped migrate from PostgreSQL to MongoDB

#### 5. **Security Implementation**

- Generated security filter chain configuration

#### 6. **Frontend Development**

- Assisted in API integration and state management

#### 7. **Documentation & Git Commits**

- AI helped structure comprehensive README
- Organized commits to reflect TDD development stages

### Learning Outcomes

While AI significantly accelerated development, the following were learned and implemented:

- Understanding of TDD workflow and best practices
- Spring Boot security configuration
- MongoDB integration with Spring Data
- React Context API for state management
- JWT token handling and validation
- Role-based access control implementation

## 🧪 Running Tests

### Backend Tests

```bash
cd server
./mvnw test
```

### Frontend Tests

```bash
cd client
npm test
```

## 🐛 Troubleshooting

### Backend Issues

**MongoDB Connection Error:**

- Verify MongoDB connection string in `application.properties`
- Ensure MongoDB Atlas IP whitelist includes your IP
- Check network connectivity

**Port 8080 Already in Use:**

- Change port in `application.properties`: `server.port=8081`
- Or stop the process using port 8080

**Tests Failing:**

- Ensure embedded MongoDB dependency is correctly configured
- Check test properties in `application-test.properties`

### Frontend Issues

**CORS Errors:**

- Verify backend CORS configuration allows `http://localhost:3000`
- Check backend server is running

**API Connection Errors:**

- Ensure backend is running on `http://localhost:8080`
- Check browser console for detailed error messages
- Verify JWT token is being stored in localStorage

## 📝 Environment Variables

### Backend (`application.properties`)

```properties
# MongoDB
spring.data.mongodb.uri=mongodb+srv://...
spring.data.mongodb.database=sweetshop_db

# JWT
jwt.secret=your-secret-key-here
jwt.expiration=86400000

# Admin Registration
admin.secret=SWEETSHOP_ADMIN_SECRET_2024
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Follow TDD approach (write tests first)
4. Commit your changes (`git commit -m 'feat: add AmazingFeature'`)
5. Push to the branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

## 📄 License

This project is part of a TDD Kata exercise for educational purposes.

## 👨‍💻 Author

**Mitali Negi**

- Project: Sweet Shop Management System

## 🙏 Acknowledgments

- Spring Boot team for excellent framework
- React team for amazing frontend library
- MongoDB for robust database solution
- All open-source contributors

---

**Built with ❤️ using TDD principles**
>>>>>>> 65e4bde (docs: add comprehensive README with setup instructions)

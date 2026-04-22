# Fitness Class & Personal Training Booking System

A full-stack scheduling and availability platform designed for fitness instructors and small gyms. Clients can reserve spots in group classes or book 1-on-1 training sessions, while the system enforces instructor availability, class capacities, recurring schedules, and conflict-free reservations.

## 🏋️ Features

- **Group Class Management**: Create and manage group fitness classes with capacity limits
- **Personal Training Sessions**: Schedule one-on-one training appointments
- **Instructor Availability**: Track and enforce instructor availability and working hours
- **Recurring Schedules**: Support for recurring classes and training sessions using iCalendar standards
- **Conflict Prevention**: Automatic validation to prevent double-booking and scheduling conflicts
- **User Authentication**: Secure JWT-based authentication and authorization
- **Interactive Calendar**: FullCalendar integration for visual schedule management
- **Real-time Updates**: Dynamic booking system with instant feedback

## 🛠️ Tech Stack

### Backend

- **Framework**: Spring Boot 4.0.0
- **Language**: Java 21
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT (JSON Web Tokens)
- **ORM**: Spring Data JPA
- **Migration**: Flyway
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Calendar Support**: iCal4j for recurring schedules
- **Build Tool**: Maven

### Frontend

- **Framework**: Angular 21.1.0
- **Language**: TypeScript 5.9.2
- **Styling**: Tailwind CSS 4.1.12
- **Calendar UI**: FullCalendar 6.1.20
- **State Management**: RxJS 7.8.0
- **Package Manager**: npm 10.9.2

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 21 or higher
- Node.js (with npm 10.9.2+)
- PostgreSQL
- Maven (or use the included Maven wrapper)
- Docker (optional, for containerized database)

## Getting Started

### Backend Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/devcom33/Fitness-Booking-System.git
   cd Fitness-Booking-System
   ```

2. **Configure the database**

   Using Docker Compose (recommended):

   ```bash
   cd backend
   docker-compose up -d
   ```

   Or manually configure PostgreSQL and update `backend/config/application.properties` with your database credentials.

3. **Build and run the backend**

   ```bash
   cd backend
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

   The backend API will be available at `http://localhost:8080`

4. **Access API Documentation**

   Once the backend is running, visit:

   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Frontend Setup

1. **Navigate to the frontend directory**

   ```bash
   cd frontend
   ```

2. **Install dependencies**

   ```bash
   npm install
   ```

3. **Start the development server**

   ```bash
   npm start
   ```

   The frontend application will be available at `http://localhost:4200`

## 🔧 Development

### Backend Commands

```bash
# Run tests
./mvnw test

# Build without tests
./mvnw clean install -DskipTests

# Run in development mode
./mvnw spring-boot:run
```

### Frontend Commands

```bash
# Start development server
npm start

# Build for production
npm run build

# Run tests
npm test

# Watch mode for development
npm run watch
```

## Project Structure

```
.
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/org/heymouad/
│   │   │   └── resources/
│   │   └── test/
│   ├── config/
│   ├── docker-compose.yml
│   └── pom.xml
└── frontend/
    ├── src/
    │   ├── app/
    │   └── assets/
    ├── angular.json
    ├── package.json
    └── tsconfig.json
```

## Security

- JWT-based authentication for secure API access
- Spring Security configuration for endpoint protection
- Password encryption and secure credential management
- Role-based access control (RBAC) for different user types

## Calendar Features

The system uses iCal4j for handling complex scheduling scenarios:

- Recurring events (daily, weekly, monthly)
- Exception dates for recurring schedules
- RRULE support for advanced recurrence patterns
- iCalendar format import/export

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is currently unlicensed. Please contact the repository owner for usage permissions.

## Authors

- **devcom33** - [GitHub Profile](https://github.com/devcom33)

## 🐛 Known Issues

- Please check the [Issues](https://github.com/devcom33/Fitness-Class-Personal-Training-Booking-System/issues) page for current known issues

## Support

For support, please open an issue in the GitHub repository or contact the maintainers.
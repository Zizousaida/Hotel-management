# Hotel Management System

Simple JavaFX application for hotel management.

## Structure
- `model/` - Core classes (Room, Booking, Customer, etc.)
- `controller/` - Business logic (HotelController)
- `view/` - JavaFX GUI windows
- `Main.java` - Application entry point

## How to Run

### Using Maven:
```bash
mvn clean javafx:run
```

### Default Login Credentials:
- Receptionist: `receptionist` / `recep123`
- Housekeeping: `housekeeper` / `house123`
- Manager: `manager` / `manager123`

## Features
- Login system with role-based access
- Room booking and management
- Check-in/Check-out
- Bill generation
- Room status updates
- Revenue tracking


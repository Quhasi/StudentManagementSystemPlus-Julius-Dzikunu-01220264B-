# Student Management System Plus

Professional JavaFX desktop application for managing student records.

## Features
- CRUD operations for students
- Advanced filtering and searching
- GPA analytics and reporting
- CSV Import/Export
- SQLite database storage
- Unit testing with Maven

## Setup Instructions
1. Install JDK 21.
2. Open the project in VS Code.
3. Run `mvn clean install` in the terminal.
4. **To Run**: Right-click `com.sms.Launcher.java` and select **Run Java**. 
   *Note: We use a Launcher class to bypass JavaFX runtime component errors.*

## Architecture
- **UI**: JavaFX (FXML + Controllers)
- **Domain**: Core models (Student, Status)
- **Service**: Business logic and validation
- **Repository**: SQLite implementation via JDBC
- **Util**: Logging, DB management, CSV helpers

# Notification Saver - Kotlin Multiplatform Application

## Overview
Notification Saver is a Kotlin Multiplatform (KMP) application designed to listen to incoming notifications and store them in a local Room database on both Android and iOS platforms. The application categorizes notifications into parent and child entries using a foreign key (`senderName`). It provides functionalities to display, update, read, and delete notifications using a paging mechanism.

## Table of Contents
1. [Project Structure](#project-structure)
2. [Features](#features)
3. [Setup and Installation](#setup-and-installation)
4. [Modules](#modules)
5. [Dependencies](#dependencies)
6. [Usage](#usage)
7. [Contributing](#contributing)
8. [License](#license)

## Project Structure

The project follows a modular and layered architecture divided into core, data, domain, and presentation layers:

### **1. Core Layer**
- Contains core utilities, shared preferences, and platform-specific implementations.
- Folder: `core`
  - `dataState`: Manages the data state of the application.
  - `di`: Dependency injection setup using Koin.
  - `local`: Contains the Room database setup and other local data handling files.

### **2. Data Layer**
- This layer contains the database entities, Data Access Objects (DAOs), and repositories.
- Folder: `data`
  - `dao`: Contains DAOs for accessing the Room database, including `NotificationDao` and `SubNotificationDao`.
  - `entities`: Defines the database entities such as `NotificationEntity` and `SubNotificationEntity` used to store notifications.
  - `repo`: Repository implementations for managing the flow of data between the database and the application.

### **3. Domain Layer**
- Responsible for the application’s business logic and data transformation.
- Folder: `domain`
  - `model`: Contains the data models used in the application.
  - `mapper`: Includes mappers to convert between entity and model objects.
  - `repo`: Interface for repository abstraction to separate data sources from the rest of the application.

### **4. Presentation Layer**
- Manages the UI and ViewModel logic using Jetpack Compose for Android and SwiftUI for iOS.
- Folder: `presentation`
  - `features`: Divided into submodules for each feature, such as `home`, `notifications`, and `settings`.
  - `navigation`: Handles app navigation.
  - `ui`: UI components and layouts for different screens.
  - `viewModel`: Contains ViewModels responsible for handling the UI logic.

## Features
- **Kotlin Multiplatform Support:** Shared codebase for both Android and iOS platforms.
- **Notification Listener:** Listens to incoming notifications and saves them in a local Room database.
- **Data Storage:** Uses Room database with foreign key (`senderName`) to manage parent-child relationships between notifications.
- **Paging Support:** Implements paging to display notifications efficiently in a list.
- **Update and Delete:** Supports marking notifications as read and deleting them from the database.
- **Clean Architecture:** Utilizes a multi-module structure with clear separation of concerns.

## Setup and Installation

### Prerequisites
- Android Studio (latest version) for Android development.
- Xcode for iOS development.
- Kotlin Multiplatform Mobile plugin for KMP project support.

### Steps to Run the Project
1. Clone the repository.
2. Open the project in Android Studio or IntelliJ IDEA.
3. Build the project using `./gradlew build`.
4. Run the Android app:
  - Use Android Studio to build and run on an Android emulator or physical device.
5. For iOS:
  - Open the `iosMain` folder in Xcode and build it for iOS devices or simulators.

## Modules

### Shared Module
Contains the shared logic between Android and iOS platforms, including:
- Room database setup.
- Business logic (domain layer).
- Shared utilities.

### Android Module (`androidMain`)
- Contains Android-specific implementations, UI components using Jetpack Compose, and notification listener service to capture notifications.

### iOS Module (`iosMain`)
- Contains iOS-specific implementations using SwiftUI for the UI and local storage setup.

## Dependencies

### Shared Dependencies
- **JetBrains Compose:** For building native UIs using Jetpack Compose.
- **Koin:** For dependency injection.
- **Room:** For local database management.
- **Paging Compose:** For implementing paging in the list of notifications.
- **Kotlinx Serialization:** For data serialization.

### Android-specific Dependencies
- `androidx.activity:activity-compose`: Provides Android-specific activity support.
- `androidx.compose.ui:ui-tooling`: For UI development and debugging.
- `androidx.lifecycle`: For lifecycle-aware components.

### Adding Dependencies
To add a dependency, modify the `build.gradle.kts` file in the respective module.

## Usage

### Notification Listener
- The app listens for incoming notifications on the device.
- Notifications are saved in the local Room database with `senderName` as a foreign key, creating a parent-child relationship between notifications.

### Room Database Integration
- `NotificationEntity` and `SubNotificationEntity` are used to store parent and child notifications in the Room database.
- The `NotificationDao` and `SubNotificationDao` provide methods for inserting, querying, updating, and deleting notifications.

### Paging Support
- The app implements paging to display notifications efficiently, allowing smooth scrolling through a large list of notifications.

### Mark as Read and Delete
- The user can mark notifications as read or delete them through the UI.
- Changes are reflected in the local Room database.

## Contributing
Contributions are welcome! Please follow the standard guidelines for submitting a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

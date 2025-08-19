# WhatToDo 📱

A modern Android task management application built with Kotlin, Room database, and Material Design components.

### Overview
WhatToDo is a clean and intuitive task management app that helps users organize their daily activities efficiently. Built with modern Android development practices, it features a responsive UI, local data persistence, and smooth user interactions.

### Features ✨
- **Task Management**: Create, edit, and delete tasks with ease
- **Date & Time Tracking**: Set due dates and times for your tasks
- **Swipe to Delete**: Intuitive swipe gestures for quick task removal
- **Task Completion**: Mark tasks as done with a simple checkbox
- **Modern UI**: Material Design 3 components for a beautiful user experience
- **Offline Storage**: Local Room database for data persistence
- **Responsive Design**: Optimized for various screen sizes

### Tech Stack 🛠️
- **Language**: Kotlin
- **Database**: Room Database with SQLite
- **UI Components**: Material Design, RecyclerView, ViewBinding
- **Async Operations**: Kotlin Coroutines
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)

### Dependencies 📦
- AndroidX Core KTX
- Room Database (Runtime, Compiler, KTX)
- Material Design Components
- Navigation Components
- Kotlin Coroutines
- ViewBinding & DataBinding

### Getting Started 🚀

#### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 24+
- Kotlin 1.8+

#### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/WhatToDo.git
   ```

2. Open the project in Android Studio

3. Sync Gradle files and build the project

4. Run the app on an emulator or physical device

#### Build Configuration
The app uses Gradle with Kotlin DSL. Key configurations:
- `compileSdk`: 34
- `minSdk`: 24
- `targetSdk`: 34
- ViewBinding and DataBinding enabled

### Key Components 🔧

#### Task Entity
The core data model representing a task with:
- Unique ID (auto-generated)
- Task name
- Due date and time
- Completion status

#### Room Database
- Local SQLite database using Room persistence library
- Automatic schema management
- Efficient data operations with DAO pattern

### Usage 💡
1. **Adding Tasks**: Tap the floating action button to open the task creation dialog
2. **Editing Tasks**: Tap on any task to edit its details
3. **Completing Tasks**: Check the checkbox to mark tasks as done
4. **Deleting Tasks**: Swipe left or right on a task to delete it

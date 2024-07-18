# Step Counter Service Sample Code

## OverView
- This Android app tracks the user's step count using a background service. The step data is stored in a Room database and displayed in the app's UI.
- This is sample code studying android Service class component.

## Features
- Count step using Step Detector sensor
- Step count number update by foreground service
- Permission management when app start
- Button with stop service

## Built with
- Android Gradle Plugin version: 8.4.1
- Kotlin version: 1.9.22
- Java version: 17
- Android SDK(min 23, target 31)
- Android Jetpack

## Installing
1. Clone the repository:
    ```sh
    git clone https://github.com/reallucky84/step-counter-app.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Build and run the project on an emulator or physical device.

## Code Structure
```
├── application
   └── StepCountApplication.kt : Application class.
├── view
   ├── MainActivity.kt : The main activity that checks permission.
   └── FirstFragment.kt : The fragment that displays the step count.
├── view   
   └── StepViewModel.kt : The ViewModel for managing UI-related data.   
├── database
   ├── StepDao.kt : The Room DAO for accessing the step records.
   ├── StepDatabase.kt : The Room database.
   └── StepDatabaseWrapper.kt : Wrapper class to use StepDatabase
├── di.module
   ├── StepDatabaseModule.kt : The Hilt module to inject classes
   └── StepViewModelModule.kt : The Hilt module to inject classes
├── repository
   └── StepRepository.kt : The repository for accessing data operations.
├── service
   └── StepCounterService.kt : The service that runs in the background to count steps.      
```

## Usage
1. Launch the app on your Android device.
2. The app will start counting your steps using the background service.
3. The step count is displayed on the main screen.
4. The app automatically saves the step count along with the current timestamp in the Room database.


## Futher work

- Need to optimize databse insert and step count calculate logic. Add logic gathering and organizing step data per day.
- Improve Android Service lifecycle handling strategy.

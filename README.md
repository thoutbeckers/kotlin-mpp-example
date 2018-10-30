[![Build Status](https://travis-ci.org/thoutbeckers/kotlin-mpp-example.svg?branch=firebase)](https://travis-ci.org/thoutbeckers/kotlin-mpp-example)

# Multiplatform sample

This example shows how to use Kotlin/Native in the multiplatform world, in combination with Firebase.

## Important note

Use a suitable Java version for running the Gradle tasks (only JDK8 is required). There are known problems with JDK11 and Gradle 4.7

## Firestore
It is currently only working for Firestore, and only the Android part of Firebase, even though this shows how to use common interfaces.

This example uses a simple "Spark" plan Firebase as a default. You can replace `google-services.json` with your own.

You can set the following rules in Firestore:

```
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{user} {
      allow write:
         if exists(/databases/$(database)/documents/moods/$(request.resource.data.mood))
         && user.matches("(?:🧐|🤓|😎|🤪|👽|🤖)")
      allow read
      allow list
    }
    match /moods/{mood} {
      allow read, list
    }
  }
}
```


## iOS

To compile the project from Xcode or Appcode just open `iosApp/iosApp.xcodeproj` and run the application.
The [swift tests](iosApp/iosAppTests/iosAppTests.swift) also can be executed from Xcode/Appcode

This works because there is a custom shellscript build phase in the MPP framework that rebuilds the framework.

To compile a framework for ios simulator from the command line yourself execute:

```bash
./gradlew :mpp:build
```

To compile the framework for a device use the `device` project property:

```bash
./gradlew :mpp:build -Pdevice=true
```

To run kotlin tests (including the [common ones](mpp/src/commonTest/kotlin/CalculatorTest.kt)) after they're compiled by Kotlin/Native:

```bash
./gradlew :mpp:iosTest
```

If you do not have the Android SDK installed, you can define an environment variable which will skip the Android specific parts of the build:

```bash
MPP_NO_ANDROID_SDK=true ./gradlew iosTest
```

You can also add this variable to the custom shell script in the Framework build in XCode.

## Android

The application can be built and executed on a device or emulator using Android Studio (tested with 3.2).

One can also compile the application and run tests from the command line:

```bash
./gradlew :mpp:build
```

If you're not on macos, you can run only the android part:

```bash
./gradlew mpp:assembleDebug
```

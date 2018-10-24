# Multiplatform sample
This example shows how to use Kotlin/Native in the multiplatform world.

This sample based on the [Jetbrains example](https://github.com/JetBrains/kotlin-mpp-example).

It is different in some ways:
- It has a "common" dependency in the commonMain source set, the networking library ktor. This is then used by the iOS/Native and Android project by their respective implementation
- This includes use of co-routines
- It does not use an "androidLib" template, since this has bugs
  
## iOS

To compile the project from Xcode or Appcode just open `iosApp/iosApp.xcodeproj` and run the application.
The [swift tests](iosApp/iosAppTests/iosAppTests.swift) also can be executed from Xcode/Appcode

This works because there is a custom shellscript build phase in the MPP framework that rebuilds the framework.

To compile a framework for ios simulator from the command line yourself execute:

```
  > ./gradlew :mpp:build
```

To compile the framework for a device use the `device` project property:

```
  > ./gradlew :mpp:build -Pdevice=true
```

To run kotlin tests (including the [common ones](mpp/src/commonTest/kotlin/CalculatorTest.kt)) after they're compiled by Kotlin/Native:

```
  > ./gradlew iosTest
```

## Android

The application can be built and executed on a device or emulator using Android Studio (tested with 3.2).

One can also compile the application and run tests from the command line:

```
   > ./gradlew :mpp:build
```

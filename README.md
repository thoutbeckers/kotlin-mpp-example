[![Build Status](https://travis-ci.org/thoutbeckers/kotlin-mpp-example.svg?branch=master)](https://travis-ci.org/thoutbeckers/kotlin-mpp-example)

# Multiplatform sample
This example shows how to use Kotlin/Native in the multiplatform world.

There is also a Firebase based example available in the [firebase](https://github.com/thoutbeckers/kotlin-mpp-example/tree/firebase) branch.

This sample based on the [Jetbrains example](https://github.com/JetBrains/kotlin-mpp-example).

It is different in some ways:
- It has a "common" dependency in the commonMain source set, the networking library ktor. This is then used by the iOS/Native and Android project by their respective implementation
- This includes use of co-routines
- It does not use an "androidLib" template, since this has bugs


# How to develop

## Important note
Use a suitable Java version for running the Gradle tasks (only JDK8 is required). There are known problems with JDK11 and Gradle 4.7
  
## iOS

To compile the project from Xcode or Appcode just open `iosApp/iosApp.xcodeproj` and run the application.
The [swift tests](iosApp/iosAppTests/iosAppTests.swift) also can be executed from Xcode/Appcode

This works because there is a custom shellscript build phase in the MPP framework that rebuilds the framework.

To compile a framework for ios simulator from the command line yourself execute:

```bash
./gradlew :mpp:build
```
To compile the framework for a device (`arm64`) use the `device` project property:

```bash
./gradlew :mpp:build -Pdevice=true
```

It's possible to build `arm32` as well, but this is currently not set up in the relevant Gradle task.

To run kotlin tests (including the [common ones](mpp/src/commonTest/kotlin/CalculatorTest.kt)) after they're compiled by Kotlin/Native:

```bash
./gradlew :mpp:iosTest
```

If you do not have the Android SDK installed, you can define an environment variable which will skip the Android specific parts of the build:

```bash
MPP_NO_ANDROID_SDK=true ./gradlew iosTest
```

Or see the `firebase` branch, for an alternative `build.gradle` that automatically detects if the Android SDK is installed or not.

You can also add this variable to the custom shell script in the Framework build in XCode.

## Android

The application can be built and executed on a device or emulator using Android Studio (tested with 3.2.1).

You can also compile the application and run tests from the command line:

```bash
./gradlew :mpp:build
```

If you're not on macos, you can run only the android part:

```bash
./gradlew mpp:assembleDebug
```

## Using IDEs

You can use Android Studio for editing the Kotlin and Kotlin/Native code. This works best if you have the actual Android SDK installed.

Make sure you update the Kotlin plugin to 1.3 (Android Studio will prompt you about this on startup). If you have an error at the end of syncing the project this is the likely cause.

This should give full code completion for all the different Kotlin sourcesets, including for iOS platform frameworks from the `iosMain` sourceset. If this doesn't work you might have to reimport the project.

XCode can be used as normal, and will tell you when there is an error compiling Kotlin files, but XCode itself does not have decent support for editing Kotlin files. It also does not support stepping through or breakpoints in the Kotlin code. You can use Appcode to debug Kotlin and Swift together, but as of now Appcode does not understand Gradle dependencies so it lacks code completion for editing Kotlin files.

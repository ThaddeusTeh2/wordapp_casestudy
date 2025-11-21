# Wordapp (Android)

**Overview**

- A simple Android app showcasing a clean, modern stack: Room for local persistence, AndroidX Navigation (with Safe Args) for screen flow, ViewModel + LiveData for state, and ViewBinding for type‑safe views.
- Package: `com.dx.wordapp` • Min SDK: 30 • Target SDK: 36.

**Tech Stack**

- Core: Kotlin, AndroidX, Material Components, ConstraintLayout
- Architecture: `ViewModel` + `LiveData`, Navigation Component (Safe Args), single-activity (`MainActivity`) with fragments
- Persistence: Room (`com.dx.wordapp.data.db.MyDatabase`), schema exported to `app/schemas/...`
- Utilities: Fragment KTX, Activity KTX, ViewBinding

**Requirements**

- Android Studio (latest stable recommended)
- Android SDK 36 installed
- JDK 11+ (if tooling errors occur, switch to JDK 17 in Android Studio settings)

**Project Layout**

- Module: `:app`
- Application class: `com.dx.wordapp.MyApp`
- Entry point: `com.dx.wordapp.MainActivity`
- Manifest: `app/src/main/AndroidManifest.xml`
- Database schemas: `app/schemas/com.dx.wordapp.data.db.MyDatabase/`
- Version catalog: `gradle/libs.versions.toml`

**Quick Start**

1. Open in Android Studio and let it sync, or use CLI.
2. Select a device/emulator (Android 11+, API 30+).
3. Run.

CLI build and install (fish shell):

```fish
./gradlew clean assembleDebug
./gradlew installDebug
```

Run tests:

```fish
# JVM unit tests
./gradlew testDebugUnitTest

# Instrumented tests (requires emulator/device)
./gradlew connectedDebugAndroidTest
```

Static checks:

```fish
./gradlew lint
```

**Navigation**

- Uses AndroidX Navigation with Safe Args.
- Add destinations in the navigation graph; arguments become type‑safe via generated directions.

**Persistence (Room)**

- Room runtime/ktx with KAPT compiler.
- Schemas are exported to `app/schemas/` (see Gradle arg `room.schemaLocation`) to track migrations.

**Build Config**

- AGP: defined via version catalog
- Kotlin: `2.0.21`, JVM target `11`
- ViewBinding: enabled in `build.gradle.kts` (`buildFeatures.viewBinding = true`)

**Dependency Management**

- Centralized in `gradle/libs.versions.toml`. To bump a library:
  - Change the version under `[versions]` and sync.

**Common Tasks**

```fish
# Assemble release (no shrinker by default)
./gradlew assembleRelease

# Print dependency insight (example)
./gradlew app:dependencies --configuration debugRuntimeClasspath
```

**Troubleshooting**

- Sync/build issues: try `./gradlew --stop && ./gradlew clean` and re‑sync.
- Android Studio hiccups: File > Invalidate Caches / Restart.
- KAPT/Room errors: delete `app/build/` and rebuild; verify KAPT is enabled and versions align in `libs.versions.toml`.

**Notes**

- Application ID: `com.dx.wordapp`
- Target/compile SDK: 36 • Min SDK: 30
- This module uses a single-activity pattern with fragments and Navigation.

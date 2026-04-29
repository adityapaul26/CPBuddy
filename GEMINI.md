# CPBuddy - Competitive Programming Companion

CPBuddy is an Android application designed for competitive programmers to track upcoming contests across various platforms (Codeforces, LeetCode, etc.) and view user profiles. It features a modern "Glassmorphism" UI built with Jetpack Compose.

## Architecture & Technologies

- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **UI Framework:** Jetpack Compose (Material 3)
- **Navigation:** Jetpack Navigation Compose
- **Networking:** Retrofit 2 with Coroutines and Gson
- **Image Loading:** Coil
- **State Management:** Generic `UiState` wrapper for reactive updates (Loading, Success, Error).
- **API:**
    - `https://competeapi.vercel.app/` for contest data.
    - `https://competeapi.vercel.app/user/codeforces/{handle}/` for Codeforces user info.

## Project Structure

- `app/src/main/java/com/example/cpbuddy/`:
    - `MainActivity.kt`: Entry point, hosting the navigation and connecting to the ViewModel.
    - `MainViewModel.kt`: Central logic and state management.
    - `CodeforcesApi.kt` & `CompeteApi.kt`: Retrofit interfaces.
    - `Contest.kt` & `User.kt`: Data models.
    - `ui/screens/`: Composable screens reactive to ViewModel state.
    - `ui/components/`: Shared UI components like `GlassBox` and `ImmersiveBackground`.
    - `ui/theme/`: Material 3 theme definitions.

## Building and Running

### Prerequisites
- Android Studio (Ladybug or newer recommended)
- JDK 11 or higher
- Android SDK (Compile API 36)

### Key Commands
- **Build:** `./gradlew assembleDebug`
- **Install on Device:** `./gradlew installDebug`
- **Run Tests:** `./gradlew test` (Unit tests) or `./gradlew connectedAndroidTest` (Instrumentation tests)
- **Clean:** `./gradlew clean`

## Development Conventions

- **Architecture:** Always use `MainViewModel` to fetch data. Do not perform networking directly in Composable functions.
- **UI State:** Wrap data fetching in `UiState` to ensure the UI handles Loading and Error states gracefully.
- **Theming:** Avoid hardcoded hex colors. Use `MaterialTheme.colorScheme` tokens (e.g., `primary`, `onSurface`, `surfaceVariant`) to ensure consistency and accessibility.
- **Visuals:** Use the `GlassBox` component for cards to maintain the signature aesthetic.
- **Icons:** Use `androidx.compose.material.icons` where possible.

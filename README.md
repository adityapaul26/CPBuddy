# CPBuddy 🚀

CPBuddy is a modern, high-performance Android application built for the competitive programming community. It helps developers and students track upcoming contests across major platforms and analyze Codeforces profiles with a beautiful "Glassmorphic" interface.

## ✨ Features

-   **Contest Tracking**: Real-time updates for upcoming contests from **Codeforces**, **CodeChef**, and **LeetCode**.
-   **Next Contest Spotlight**: A dedicated "Featured" card for the very next contest with a live countdown timer.
-   **Direct Registration**: One-tap "Register" buttons that take you directly to the contest page.
-   **Profile Search**: Deep-dive into any Codeforces user's stats by searching their handle.
-   **Visual Analytics**: View user ratings, ranks (with color-coded indicators), contribution, and more.
-   **Immersive UI**: A signature "Glassmorphism" aesthetic with blurred backgrounds and sleek transitions.

## 🛠 Tech Stack

-   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern declarative UI.
-   **Architecture**: MVVM (Model-View-ViewModel) for clean separation of concerns.
-   **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & Flow.
-   **Networking**: [Retrofit 2](https://square.github.io/retrofit/) with GSON converter for API integration.
-   **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for fast, asynchronous image loading.
-   **Design System**: Material 3 (M3) with custom glassmorphism components.
-   **Navigation**: Jetpack Navigation Compose.

## 🏗 Project Structure

```text
com.example.cpbuddy/
├── data/               # (Conceptual) Data models and API interfaces
│   ├── Contest.kt      # Data model for platform contests
│   ├── User.kt         # Data model for Codeforces user profiles
│   ├── CodeforcesApi.kt# Retrofit interface for User data
│   └── CompeteApi.kt   # Retrofit interface for Contest data
├── ui/                 # UI Layer
│   ├── components/     # Reusable UI elements (GlassBox, ImmersiveBackground)
│   ├── screens/        # Feature-specific screens (ContestsScreen, ProfileScreen)
│   └── theme/          # Material 3 theme, colors, and typography
├── MainViewModel.kt    # Core business logic and state management
└── MainActivity.kt     # Entry point and Navigation host
```

## 🚀 Getting Started

### Prerequisites
-   Android Studio (Ladybug or newer recommended)
-   JDK 17
-   Android SDK (Targeting API 36)

### Installation
1.  **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/CPBuddy.git
    ```
2.  **Open in Android Studio**:
    -   Select "Open" and navigate to the project folder.
3.  **Sync Gradle**:
    -   Android Studio will automatically prompt a Gradle sync. Ensure you have an active internet connection.
4.  **Run the app**:
    -   Connect an Android device or start an emulator and click the "Run" button.

## 🎨 Design Philosophy: Glassmorphism

CPBuddy uses a custom-built `GlassBox` component to achieve its signature look. This is achieved by:
1.  Using semi-transparent surface colors.
2.  Applying thin, high-contrast borders for depth.
3.  Layering these elements over an `ImmersiveBackground` that provides a vibrant, blurred backdrop.

## 📈 Future Roadmap
-   [ ] **Push Notifications**: Get reminded 15 minutes before a contest starts.
-   [ ] **Contest Reminders**: Add contests directly to your Google Calendar.
-   [ ] **Multi-Platform Support**: Add profiles for LeetCode and CodeChef.
-   [ ] **Offline Mode**: Cache contest data using Room for offline viewing.

---
*Developed with ❤️ for the CP Community.*

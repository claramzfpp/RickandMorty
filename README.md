# Rick and Morty

An Android app for browsing Rick and Morty characters, built with Kotlin and Jetpack Compose. This is a rebuild of an app I originally made in Swift/UIKit during my internship — the goal was to learn Kotlin/Android by re-implementing the same idea "the Android way," rather than a direct port.

## Features

- **Discover** — paginated grid of characters from the [Rick and Morty API](https://rickandmortyapi.com/), with loading states for pagination and images.
- **Search** — search characters by name.
- **Character details** — full character info, with a favorite toggle.
- **Favorites** — a dedicated tab listing favorited characters, persisted locally so they survive app restarts.

## Tech stack

- **Kotlin** + **Jetpack Compose** for UI
- **Coroutines / Flow** for async and reactive state
- **Retrofit** + **kotlinx.serialization** + **OkHttp** for networking
- **Room** for local persistence of favorites
- **Hilt** for dependency injection
- **Navigation Compose** for screen navigation
- **Coil** for image loading

## Architecture

The app follows Clean Architecture, split into three layers:

```
data/        DTOs, Retrofit API, Room (DAO/entity), repository implementations
domain/      Domain models and repository interfaces
ui/          Compose screens, ViewModels, and UI state per feature
```

Each feature under `ui/` (character list, character detail, favorites) has its own `ViewModel` and `UiState`, and depends only on the `domain` repository interfaces — never on `data` layer types directly. DI wiring for network, database, and repositories lives in `di/`.

## Getting started

1. Clone the repo and open it in Android Studio.
2. Let Gradle sync (Kotlin 2.2, AGP 9.1, compileSdk 37, minSdk 24).
3. Run the `app` configuration on an emulator or device.

No API keys or `.env` setup are required — the Rick and Morty API is public.

## Project history

- [#1 — networking layer](https://github.com/claramzfpp/RickandMorty/pull/1)
- [#2 — discover tab + character details](https://github.com/claramzfpp/RickandMorty/pull/2)
- [#3 — favorites tab + persistence](https://github.com/claramzfpp/RickandMorty/pull/3)

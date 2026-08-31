

# Rick and Morty

An Android app for browsing Rick and Morty characters, built with Kotlin and Jetpack Compose. This is a rebuild of an app I originally made in Swift/UIKit during my internship — the goal was to learn Kotlin/Android by re-implementing the same idea "the Android way," rather than a direct port.

## Features

- **Discover** — paginated grid of characters and locations from the [Rick and Morty API](https://rickandmortyapi.com/), plus a season carousel, with loading states for pagination and images.
- **Search** — search characters by name.
- **Character details** — full character info, with a favorite toggle.
- **Location details** — location info (name, type, dimension).
- **Episodes by season** — tapping a season lists its episodes (name and air date), sourced from real API data; seasons without episode data yet are dimmed and disabled.
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

Each feature under `ui/` (character list, character detail, location detail, season episode list, favorites) has its own `ViewModel` and `UiState`, and depends only on the `domain` repository interfaces — never on `data` layer types directly. DI wiring for network, database, and repositories lives in `di/`.

## Getting started

1. Clone the repo and open it in Android Studio.
2. Let Gradle sync (Kotlin 2.2, AGP 9.1, compileSdk 37, minSdk 24).
3. Run the `app` configuration on an emulator or device.

No API keys or `.env` setup are required — the Rick and Morty API is public.

## Project history

- [#1 — Added networking layer](https://github.com/claramzfpp/RickandMorty/pull/1)
- [#2 — Created discover screen and character details screen](https://github.com/claramzfpp/RickandMorty/pull/2)
- [#3 — Created favorites screen and implemented data persistence (Room)](https://github.com/claramzfpp/RickandMorty/pull/3)
- [#4 - Updated discover view with new carousels (season and location)](https://github.com/claramzfpp/RickandMorty/pull/4)
- [#5 - Updated character and locations UI details](https://github.com/claramzfpp/RickandMorty/pull/5)
- [#6 - Created season details - IA-first](https://github.com/claramzfpp/RickandMorty/pull/6)

<img width="484" height="902" alt="Screenshot 2026-08-31 at 18 59 11" src="https://github.com/user-attachments/assets/97c1442b-9ca2-4399-8172-c8c67e5f2109" />

<img width="490" height="899" alt="Screenshot 2026-08-31 at 18 59 21" src="https://github.com/user-attachments/assets/a8724f6d-11f4-4a60-947c-5f8f2710eef9" />

<img width="487" height="897" alt="Screenshot 2026-08-31 at 18 59 32" src="https://github.com/user-attachments/assets/7de3fdae-9aae-4007-9b34-f274985c43ac" />

<img width="495" height="904" alt="Screenshot 2026-08-31 at 18 59 44" src="https://github.com/user-attachments/assets/2e03fbae-279b-4658-9e56-9ac72db3aa5b" />

<img width="488" height="903" alt="Screenshot 2026-08-31 at 18 59 53" src="https://github.com/user-attachments/assets/f1f85525-8a5d-405c-9589-a61f76225dfa" />







# Android app foundation

This directory is the Android application layer for the Amharic Study Bible.

## Planned runtime

- Kotlin + Jetpack Compose UI
- Android Room/SQLite for the bundled offline database
- Repository layer separating offline source data from AI services
- Bible reader
- Unified offline search
- Verse study screen
- Word-study screen
- History/theology screen
- Church Fathers screen

The app must remain useful with airplane mode enabled. AI is an optional, separate feature and must never be required for Bible reading or offline study.

The production Gradle project will be added once the database fixture and Android package configuration are committed.

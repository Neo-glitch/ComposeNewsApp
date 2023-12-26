package com.loc.newsapp.domain.usecases

// use case combining both use cases
data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry
)
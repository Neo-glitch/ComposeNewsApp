package com.loc.newsapp.domain.usecases.app_entry

// use case combining both use cases
data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry
)
package com.loc.newsapp.domain.manager

import kotlinx.coroutines.flow.Flow

/**
 * can also be seen as repository in app architecture
 */
interface LocalUserManager {

    // user has gone to home screen
    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>
}
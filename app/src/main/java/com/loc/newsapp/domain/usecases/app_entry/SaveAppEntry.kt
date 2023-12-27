package com.loc.newsapp.domain.usecases.app_entry

import com.loc.newsapp.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {

    // operator fun allows calling this function by just this class name
    // i.e treating object as function
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}
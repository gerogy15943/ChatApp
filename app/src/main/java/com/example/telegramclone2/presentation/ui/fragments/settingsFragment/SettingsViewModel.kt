package com.example.telegramclone2.presentation.ui.fragments.settingsFragment

import androidx.lifecycle.ViewModel
import com.example.telegramclone2.domain.repository.FirebaseRepository
import javax.inject.Inject

class SettingsViewModel@Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {

    val firebaseUserLiveData = firebaseRepository.getFirebaseUserLiveData()

    fun signOut(){
        firebaseRepository.logOut()
    }
}
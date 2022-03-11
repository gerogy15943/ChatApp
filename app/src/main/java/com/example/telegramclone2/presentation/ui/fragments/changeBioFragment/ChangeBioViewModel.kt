package com.example.telegramclone2.presentation.ui.fragments.changeBioFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.FirebaseRepository
import javax.inject.Inject

class ChangeBioViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {
    val firebaseUserLiveData: LiveData<User?> = firebaseRepository.getFirebaseUserLiveData()

    fun changeBio(bio: String){
        firebaseRepository.changeBio(bio)
    }
}
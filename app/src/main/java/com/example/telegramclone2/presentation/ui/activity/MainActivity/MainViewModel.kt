package com.example.telegramclone2.presentation.ui.activity.MainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.FirebaseRepository
import com.example.telegramclone2.utils.AppStatus
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class MainViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {
    val loginLiveData: LiveData<Boolean> = firebaseRepository.getLoginLiveData()
    val firebaseUserLiveData: LiveData<User?> = firebaseRepository.getFirebaseUserLiveData()

    fun updateStatus(status: AppStatus){
        firebaseRepository.updateStatus(status)
    }
}
package com.example.telegramclone.ui.changeUserName

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.FirebaseRepository
import javax.inject.Inject


class ChangeUserNameViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {

    val firebaseUserLiveData: LiveData<User?> = firebaseRepository.getFirebaseUserLiveData()

    fun changeUserName(oldUserName: String, newUserName: String){
        firebaseRepository.changeUserName(oldUserName, newUserName)
    }
}
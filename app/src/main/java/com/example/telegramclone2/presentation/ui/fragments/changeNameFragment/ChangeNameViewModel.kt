package com.example.telegramclone.ui.changeName

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.FirebaseRepository
import javax.inject.Inject


class ChangeNameViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {

    val firebaseUserLiveData: LiveData<User?> = firebaseRepository.getFirebaseUserLiveData()

    fun changeName(fullName: String){
        firebaseRepository.changeName(fullName)
    }
}
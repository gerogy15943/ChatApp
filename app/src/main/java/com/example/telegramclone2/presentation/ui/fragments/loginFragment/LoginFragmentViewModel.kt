package com.example.telegramclone2.presentation.ui.fragments.loginFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {

    val loginLiveData: LiveData<User?> = firebaseRepository.getFirebaseUserLiveData()


    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.register(email, password)
            }
        }
    }
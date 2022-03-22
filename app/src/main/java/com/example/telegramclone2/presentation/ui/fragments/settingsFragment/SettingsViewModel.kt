package com.example.telegramclone2.presentation.ui.fragments.settingsFragment

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.AuthRepositrory
import com.example.telegramclone2.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel@Inject constructor(private val authRepositrory: AuthRepositrory,
                                            private val userRepository: UserRepository): ViewModel() {

    val firebaseUserLiveData = MutableLiveData<User>()


    init {
        //getUser()
    }

    suspend fun getUser(): Flow<User> {
        return userRepository.getUser()
    }

    fun logOut(){
        viewModelScope.launch(Dispatchers.IO) {
            authRepositrory.logOut()
        }
    }

    fun changePhoto(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.changePhoto(uri)
        }
    }
}
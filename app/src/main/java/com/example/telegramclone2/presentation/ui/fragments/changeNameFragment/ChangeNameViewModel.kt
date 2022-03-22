package com.example.telegramclone.ui.changeName

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChangeNameViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    suspend fun getUser(): Flow<User> {
        return userRepository.getUser()
    }

    fun changeName(fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.changeName(fullName)
        }
    }
}
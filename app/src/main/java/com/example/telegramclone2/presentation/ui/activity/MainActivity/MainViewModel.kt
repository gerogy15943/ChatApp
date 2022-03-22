package com.example.telegramclone2.presentation.ui.activity.MainActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.AuthRepositrory
import com.example.telegramclone2.domain.repository.UserRepository
import com.example.telegramclone2.utils.AppStatus
import com.example.telegramclone2.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

class MainViewModel @Inject constructor(
    private val authRepositrory: AuthRepositrory,
    private val userRepository: UserRepository
) : ViewModel() {
    private val loginLiveData = SingleLiveEvent<Boolean>()
    val firebaseUserLiveData = MutableLiveData<User>()
    private val eventChannel = Channel<Boolean>()
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        getLoginState()
    }

    suspend fun getUser(): Flow<User> {
        return userRepository.getUser()
    }

    fun getLoginState() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepositrory.loginState().collect {
                eventChannel.send(it)
            }
        }
    }

    fun updateStatus(status: AppStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateStatus(status)
        }
    }

}
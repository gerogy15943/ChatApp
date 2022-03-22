package com.example.telegramclone2.presentation.ui.fragments.loginFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegramclone2.domain.repository.AuthRepositrory
import com.example.telegramclone2.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(private val authRepositrory: AuthRepositrory): ViewModel() {

    fun register(email: String, password: String) {
        viewModelScope.launch {
            authRepositrory.register(email, password)
            }
        }
    }
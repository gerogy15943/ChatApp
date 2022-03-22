package com.example.telegramclone2.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.telegramclone2.utils.SingleLiveEvent
import kotlinx.coroutines.flow.Flow

interface AuthRepositrory {

    suspend fun register(email: String, password: String)
    suspend fun logOut()
    suspend fun loginState(): Flow<Boolean>
}
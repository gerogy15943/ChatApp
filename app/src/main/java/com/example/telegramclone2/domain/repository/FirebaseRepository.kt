package com.example.telegramclone2.domain.repository

import androidx.lifecycle.LiveData
import com.example.telegramclone2.data.models.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun register(email: String, password: String)
    fun getFirebaseUserLiveData(): LiveData<User?>
    fun getLoginLiveData(): LiveData<Boolean>
    fun logOut()
    fun changeName(fullname: String)
    fun changeUserName(oldUsername: String, newUserName: String)
}
package com.example.telegramclone2.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.utils.AppStatus
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun register(email: String, password: String)
    fun getFirebaseUserLiveData(): LiveData<User?>
    fun getLoginLiveData(): LiveData<Boolean>
    fun logOut()
    fun changeName(fullname: String)
    fun changeUserName(oldUsername: String, newUserName: String)
    fun changeBio(bio: String)
    fun changePhoto(uri: Uri)
    fun updateStatus(status: AppStatus)
}
package com.example.telegramclone2.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.utils.AppStatus
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun changeName(fullname: String)
    suspend fun changeUserName(oldUsername: String, newUserName: String)
    suspend fun changeBio(bio: String)
    suspend fun changePhoto(uri: Uri)
    suspend fun updateStatus(status: AppStatus)
    suspend fun getUser() : Flow<User>
}
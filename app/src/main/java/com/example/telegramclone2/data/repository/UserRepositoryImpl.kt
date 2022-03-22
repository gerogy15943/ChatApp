package com.example.telegramclone2.data.repository

import android.net.Uri
import android.util.Log
import com.example.telegramclone2.utils.AppValueEventListener
import com.example.telegramclone2.utils.FirebaseHelper.getUrlToStorage
import com.example.telegramclone2.utils.FirebaseHelper.putImageToStorage
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.UserRepository
import com.example.telegramclone2.utils.AppStatus
import com.example.telegramclone2.utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import javax.inject.Inject
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
) : UserRepository {

    override suspend fun changeName(fullname: String) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid).child(Const.CHILD_FULLNAME)
            .setValue(fullname)
    }

    override suspend fun changeUserName(oldUserName: String, newUserName: String) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        val database = firebaseDatabase.reference
        database.child(Const.NODE_USERNAMES)
            .addListenerForSingleValueEvent(AppValueEventListener { snapshot ->
                if (snapshot.hasChild(newUserName)) {
                    return@AppValueEventListener
                } else if (snapshot.hasChild(oldUserName) && snapshot.child(oldUserName)
                        .child(uid).key.toString() == uid
                ) {
                    database.child(Const.NODE_USERNAMES).child(oldUserName).removeValue()
                }
                database.child(Const.NODE_USERNAMES).child(newUserName).setValue(uid)
                database.child(Const.NODE_USERS).child(uid).child(Const.CHILD_USERNAME)
                    .setValue(newUserName)
            })
    }

    override suspend fun changeBio(bio: String) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid).child(Const.CHILD_BIO)
            .setValue(bio)
    }

    override suspend fun changePhoto(uri: Uri) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        val path = firebaseStorage.reference.child(Const.FOLDER_PROFILE_IMAGE).child(uid)
        putImageToStorage(uri, path) {
            getUrlToStorage(path) {
                firebaseDatabase.reference.child(Const.NODE_USERS).child(uid)
                    .child(Const.CHILD_PHOTO_URL)
                    .setValue(it)
                    .addOnFailureListener { Log.e("TAG", it.message.toString()) }
            }
        }
    }

    override suspend fun updateStatus(status: AppStatus) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid).child(Const.CHILD_STATUS)
            .setValue(status.state)
    }

    override suspend fun getUser(): Flow<User> = callbackFlow{
        if (firebaseAuth.currentUser != null) {
            firebaseDatabase.reference.child(Const.NODE_USERS)
                .addValueEventListener(AppValueEventListener {
                        trySend(it.child(firebaseAuth.currentUser!!.uid).getValue(User::class.java)
                            ?: User())
                })
        }
        awaitClose { cancel() }
    }

}
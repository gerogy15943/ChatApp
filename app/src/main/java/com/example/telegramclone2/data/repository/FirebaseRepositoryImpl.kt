package com.example.telegramclone2.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.telegramclone2.utils.AppValueEventListener
import com.example.telegramclone2.utils.FirebaseHelper.getUrlToStorage
import com.example.telegramclone2.utils.FirebaseHelper.putImageToStorage
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.FirebaseRepository
import com.example.telegramclone2.utils.AppStatus
import com.example.telegramclone2.utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject


class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
) : FirebaseRepository {
    private val loginLiveData = MutableLiveData<Boolean>()

    private val firebaseUserLiveData = MutableLiveData<User?>().apply {
        if (firebaseAuth.currentUser != null) {
            loginLiveData.value = true
            firebaseDatabase.reference.child(Const.NODE_USERS).child(firebaseAuth.currentUser!!.uid)
                .addValueEventListener(AppValueEventListener {
                        value = it.getValue(User::class.java) ?: User()
                })
        } else
            value = null
    }

    override fun getFirebaseUserLiveData(): LiveData<User?> {
        return firebaseUserLiveData
    }

    override fun getLoginLiveData(): LiveData<Boolean> {
        return loginLiveData
    }

    override fun logOut() {
        firebaseAuth.signOut()
        loginLiveData.postValue(false)
    }

    override fun changeName(fullname: String) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid).child(Const.CHILD_FULLNAME)
            .setValue(fullname)
    }

    override fun changeUserName(oldUserName: String, newUserName: String) {
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

    override fun changeBio(bio: String) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid).child(Const.CHILD_BIO)
            .setValue(bio)
    }

    override fun changePhoto(uri: Uri) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        val path = firebaseStorage.reference.child(Const.FOLDER_PROFILE_IMAGE).child(uid)
        putImageToStorage(uri, path){
            getUrlToStorage(path){
                firebaseDatabase.reference.child(Const.NODE_USERS).child(uid)
                    .child(Const.CHILD_PHOTO_URL)
                    .setValue(it)
                    .addOnFailureListener { Log.e("TAG", it.message.toString()) }
            }
        }
    }

    override fun updateStatus(status: AppStatus) {
        val uid = firebaseAuth.currentUser?.uid.toString()
        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid).child(Const.CHILD_STATUS).setValue(status.state)
    }


    override fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = firebaseAuth.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[Const.CHILD_ID] = uid
                dateMap[Const.CHILD_EMAIL] = email
                dateMap[Const.CHILD_USERNAME] = uid
                firebaseDatabase.reference.child(Const.NODE_USERS).child(uid)
                    .updateChildren(dateMap).addOnSuccessListener {
                        loginLiveData.postValue(true)
                }
                firebaseDatabase.reference.child(Const.NODE_USERS).child(uid)
                    .addListenerForSingleValueEvent(AppValueEventListener { snapshot ->
                            firebaseUserLiveData.postValue(snapshot.getValue(User::class.java))
                    })


            } else
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener{
                        loginLiveData.postValue(true)
                        val uid = firebaseAuth.currentUser?.uid.toString()
                        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid)
                            .addListenerForSingleValueEvent(AppValueEventListener { snapshot ->
                                    firebaseUserLiveData.postValue(snapshot.getValue(User::class.java))
                            })
                }
        }
    }


/*override suspend fun register(email: String, password: String): Flow<Boolean> = callbackFlow{
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful)
                trySend(true)
        }
        awaitClose { cancel()}
        }*/
}
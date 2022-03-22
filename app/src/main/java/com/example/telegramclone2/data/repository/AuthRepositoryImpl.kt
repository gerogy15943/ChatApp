package com.example.telegramclone2.data.repository


import com.example.telegramclone2.domain.repository.AuthRepositrory
import com.example.telegramclone2.utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : AuthRepositrory {

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }

    override suspend fun loginState(): Flow<Boolean> = callbackFlow{
        firebaseAuth.addAuthStateListener { auth ->
                trySend(auth.currentUser != null)
        }
        awaitClose { cancel() }
    }

    override suspend fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = firebaseAuth.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[Const.CHILD_ID] = uid
                dateMap[Const.CHILD_EMAIL] = email
                dateMap[Const.CHILD_USERNAME] = uid
                firebaseDatabase.reference.child(Const.NODE_USERS).child(uid)
                    .updateChildren(dateMap)
            } else
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    val uid = firebaseAuth.currentUser?.uid.toString()
                    firebaseDatabase.reference.child(Const.NODE_USERS)
                        .child(uid)
                        .child(Const.CHILD_LOGIN_DATE)
                        .setValue(
                            Date().time
                        )
                }
        }
    }
}
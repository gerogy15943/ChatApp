package com.example.telegramclone2.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.domain.repository.FirebaseRepository
import com.example.telegramclone2.utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject


class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : FirebaseRepository {
    private val loginLiveData = MutableLiveData<Boolean>()

    private val firebaseUserLiveData = MutableLiveData<User?>().apply {
        if(firebaseAuth.currentUser != null) {
            loginLiveData.value = true
            firebaseDatabase.reference.child(Const.NODE_USERS).child(firebaseAuth.currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        value = snapshot.getValue(User::class.java) ?: User()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
        else
            value = null
    }

    override fun getFirebaseUserLiveData(): LiveData<User?>{
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
        database.child(Const.NODE_USERNAMES).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(newUserName)) {
                    return
                }
                else if(snapshot.hasChild(oldUserName) && snapshot.child(oldUserName).child(uid).key.toString() == uid){
                    database.child(Const.NODE_USERNAMES).child(oldUserName).removeValue()
                }
                database.child(Const.NODE_USERNAMES).child(newUserName).setValue(uid)
                database.child(Const.NODE_USERS).child(uid).child(Const.CHILD_USERNAME).setValue(newUserName)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override suspend fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                val uid = firebaseAuth.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[Const.CHILD_ID] = uid
                dateMap[Const.CHILD_EMAIL] = email
                dateMap[Const.CHILD_USERNAME] = uid
                firebaseDatabase.reference.child(Const.NODE_USERS).child(uid).updateChildren(dateMap).addOnCompleteListener {
                    if(it.isSuccessful)
                        loginLiveData.postValue(true)
                }

            }
            else
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful) {
                        loginLiveData.postValue(true)
                        val uid = firebaseAuth.currentUser?.uid.toString()
                        firebaseDatabase.reference.child(Const.NODE_USERS).child(uid)
                            .addListenerForSingleValueEvent(object: ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    firebaseUserLiveData.postValue(snapshot.getValue(User::class.java))
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })
                    }
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
package com.example.telegramclone2.utils

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.StorageReference

object FirebaseHelper {

    fun putImageToStorage(uri: Uri, path: StorageReference, function: (path: StorageReference) -> Unit){
        path.putFile(uri)
            .addOnSuccessListener { function(path) }
            .addOnFailureListener { Log.e("TAG", it.message.toString()) }
    }

    fun getUrlToStorage(path: StorageReference, function: (url: String) -> Unit){
        path.downloadUrl
            .addOnSuccessListener { function(it.toString()) }
            .addOnFailureListener { Log.e("TAG", it.message.toString()) }
    }
}
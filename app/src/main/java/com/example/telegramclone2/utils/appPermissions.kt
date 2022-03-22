package com.example.telegramclone2.utils

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.telegramclone2.presentation.ui.activity.MainActivity.MainActivity

const val READ_CONTACTS = android.Manifest.permission.READ_CONTACTS
const val PERMISSION_REQUEST = 200

fun checkPermissions(activity: MainActivity, permission: String): Boolean{
    return if(Build.VERSION.SDK_INT >=23 && ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(activity, arrayOf(permission), PERMISSION_REQUEST )
        false
    }
    else
        true
}
package com.example.telegramclone2.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AppValueEventListener(val function: (DataSnapshot) -> Unit): ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        function(snapshot)
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }
}
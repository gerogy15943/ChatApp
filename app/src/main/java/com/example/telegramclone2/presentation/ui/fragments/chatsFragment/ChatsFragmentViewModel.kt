package com.example.telegramclone2.presentation.ui.fragments.chatsFragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegramclone2.domain.repository.FirebaseRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatsFragmentViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    init {
        //checkUser()
    }

    /*fun checkUser() {
        viewModelScope.launch {
            _loginState.postValue(firebaseRepository.checkUser())
        }
    }*/
}
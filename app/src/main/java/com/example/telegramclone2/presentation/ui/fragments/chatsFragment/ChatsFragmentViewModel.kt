package com.example.telegramclone2.presentation.ui.fragments.chatsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telegramclone2.domain.repository.UserRepository
import javax.inject.Inject

class ChatsFragmentViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

}
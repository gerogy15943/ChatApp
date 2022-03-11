package com.example.telegramclone2.di

import com.example.telegramclone.ui.changeName.ChangeNameFragment
import com.example.telegramclone.ui.changeUserName.ChangeBioFragment
import com.example.telegramclone.ui.changeUserName.ChangeUserNameFragment
import com.example.telegramclone2.presentation.ui.activity.MainActivity.MainActivity
import com.example.telegramclone2.presentation.ui.fragments.chatsFragment.ChatsFragment
import com.example.telegramclone2.presentation.ui.fragments.loginFragment.LoginFragment
import com.example.telegramclone2.presentation.ui.fragments.settingsFragment.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, FirebaseModule::class, DataModule::class])
interface AppComponent {
    fun inject(fragment: ChatsFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: SettingsFragment)
    fun inject(activity: MainActivity)
    fun inject(fragment: ChangeNameFragment)
    fun inject(fragment: ChangeUserNameFragment)
    fun inject(fragment: ChangeBioFragment)
}
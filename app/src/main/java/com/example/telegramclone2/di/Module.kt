package com.example.telegramclone2.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telegramclone.ui.changeName.ChangeNameViewModel
import com.example.telegramclone.ui.changeUserName.ChangeUserNameViewModel
import com.example.telegramclone2.data.repository.FirebaseRepositoryImpl
import com.example.telegramclone2.domain.repository.FirebaseRepository
import com.example.telegramclone2.presentation.ui.activity.MainActivity.MainViewModel
import com.example.telegramclone2.presentation.ui.fragments.changeBioFragment.ChangeBioViewModel
import com.example.telegramclone2.presentation.ui.fragments.chatsFragment.ChatsFragmentViewModel
import com.example.telegramclone2.presentation.ui.fragments.loginFragment.LoginFragmentViewModel
import com.example.telegramclone2.presentation.ui.fragments.settingsFragment.SettingsViewModel
import com.example.telegramclone2.presentation.viewmodelFactory.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context{
        return context
    }
}

@Module
class FirebaseModule{

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun providePhoneAuthProvider(): PhoneAuthProvider{
        return PhoneAuthProvider.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }
}

@Module
class DataModule{

    @Singleton
    @Provides
    fun provideFirebaseRepository(firebaseAuth: FirebaseAuth,
                                  firebaseDatabase: FirebaseDatabase,
                                  firebaseStorage: FirebaseStorage): FirebaseRepository{
        return FirebaseRepositoryImpl(firebaseAuth, firebaseDatabase, firebaseStorage)
    }
}

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChatsFragmentViewModel::class)
    abstract fun bindChatsFragmentViewModel(chatsFragmentViewModel: ChatsFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    abstract fun bindLoginFragmentViewModel(loginViewmodel: LoginFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeNameViewModel::class)
    abstract fun bindChangeNameViewModel(changeNameViewModel: ChangeNameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeUserNameViewModel::class)
    abstract fun bindChangeUserNameViewModel(changeUserNameViewModel: ChangeUserNameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeBioViewModel::class)
    abstract fun bindChangeBioViewModel(changeBioViewModel: ChangeBioViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
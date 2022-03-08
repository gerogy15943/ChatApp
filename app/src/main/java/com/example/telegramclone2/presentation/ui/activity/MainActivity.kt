package com.example.telegramclone2.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.ActivityMainBinding
import com.example.telegramclone2.presentation.ui.fragments.loginFragment.LoginFragmentViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.chatsFragment
            ), drawerLayout
        )
        (applicationContext as App).appComponent.inject(this)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel.loginLiveData.observe(this, Observer {
            if(it) {
                Log.d("TAG", "activity")
                navController.navigate(R.id.action_loginFragment_to_chatsFragment)
            }
            else {
                navController.navigate(R.id.action_global_loginFragment)
            }
            })

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
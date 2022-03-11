package com.example.telegramclone2.presentation.ui.activity.MainActivity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.databinding.ActivityMainBinding
import com.example.telegramclone2.utils.AppStatus
import com.squareup.picasso.Picasso
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

        viewModel.firebaseUserLiveData.observe(this, Observer {
            if(it != null) {
                val imageView = drawerLayout.findViewById<ImageView>(R.id.imageView_header)
                val fullName = drawerLayout.findViewById<TextView>(R.id.fullname_header)
                val email = drawerLayout.findViewById<TextView>(R.id.email_header)
                val photoUrl = it?.photourl
                if(photoUrl.isNotEmpty())
                    Picasso.get().load(it?.photourl).into(imageView)
                fullName.setText(it?.fullname)
                email.setText(it?.email)
            }
        })

        viewModel.loginLiveData.observe(this, Observer {
            if(it)
                navController.navigate(R.id.action_loginFragment_to_chatsFragment)
            else
                navController.navigate(R.id.action_global_loginFragment)
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

    override fun onStart() {
        super.onStart()
        viewModel.updateStatus(AppStatus.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateStatus(AppStatus.OFFLINE)
    }
}
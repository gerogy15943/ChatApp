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
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.data.models.User
import com.example.telegramclone2.databinding.ActivityMainBinding
import com.example.telegramclone2.utils.AppStatus
import com.example.telegramclone2.utils.PERMISSION_REQUEST
import com.example.telegramclone2.utils.READ_CONTACTS
import com.example.telegramclone2.utils.checkPermissions
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val imageView = navView.getHeaderView(0).findViewById<ImageView>(R.id.imageView_header)
        val fullName = navView.getHeaderView(0).findViewById<TextView>(R.id.fullname_header)
        val email = navView.getHeaderView(0).findViewById<TextView>(R.id.email_header)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.chatsFragment
            ), drawerLayout
        )
        (applicationContext as App).appComponent.inject(this)
        setupActionBarWithNavController(navController, appBarConfiguration)
        (navView as NavigationView).setupWithNavController(navController)

        lifecycleScope.launchWhenStarted() {
            viewModel.getUser().collect {
                it.let {
                    val photoUrl = it.photourl
                    if (photoUrl.isNotEmpty())
                        Picasso.get().load(photoUrl).into(imageView)
                    fullName.text = it.fullname
                    email.text = it.email
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect {
                if (it)
                    navController.navigate(R.id.action_loginFragment_to_chatsFragment)
                else
                    navController.navigate(R.id.action_global_loginFragment)
            }
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_contacts -> {
                    navController.navigate(R.id.contactFragment)
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
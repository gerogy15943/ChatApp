package com.example.telegramclone2.presentation.ui.fragments.settingsFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.FragmentSettingsBinding
import javax.inject.Inject


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        (context?.applicationContext as App).appComponent.inject(this)
        viewModel.firebaseUserLiveData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                settingsBio.setText(it?.bio)
                settingsEmail.setText(it?.email)
                settingsFullname.setText(it?.fullname)
                settingsStatus.setText(it?.status)
                settingsLogin.setText(it?.username)
            }
        })
        binding.settingsBtnChangeLogin.setOnClickListener {
            findNavController().navigate(R.id.changeUserNameFragment)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.main, menu)}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_exit -> {
                viewModel.signOut()
            }
            R.id.settings_menu_change_name -> {
                findNavController().navigate(R.id.changeNameFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
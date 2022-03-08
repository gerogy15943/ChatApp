package com.example.telegramclone.ui.changeUserName

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.FragmentChangeUserNameBinding
import com.example.telegramclone2.presentation.ui.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class ChangeUserNameFragment : Fragment() {

    lateinit var binding: FragmentChangeUserNameBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ChangeUserNameViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeUserNameBinding.inflate(layoutInflater, container, false)
        binding.settingsInputUsername.setText(viewModel.firebaseUserLiveData.value?.username)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_confirm_change -> {
                val oldUserName = viewModel.firebaseUserLiveData.value!!.username
                val newUserName = binding.settingsInputUsername.text.toString()
                if (newUserName.isEmpty()) {
                    binding.settingsInputUsername.error = "Поле пустое"
                } else {
                    if(!viewModel.changeUserName(oldUserName, newUserName))
                        findNavController().popBackStack()
                    else
                        Snackbar.make(requireActivity().findViewById(R.id.root_layout), "Этот логин занят", Snackbar.LENGTH_SHORT)
                }
            }
            R.id.home -> {
            }
        }
        return true
    }
}
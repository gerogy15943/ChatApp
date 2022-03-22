package com.example.telegramclone.ui.changeName

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.FragmentChangeNameBinding
import com.example.telegramclone2.presentation.ui.activity.MainActivity.MainActivity
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ChangeNameFragment : Fragment() {
    private lateinit var binding: FragmentChangeNameBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ChangeNameViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeNameBinding.inflate(layoutInflater)
        (context?.applicationContext as App).appComponent.inject(this)
        lifecycleScope.launchWhenStarted {
            viewModel.getUser().collect { user ->
                if(user.fullname.isNotEmpty()){
                    val fullName = user.fullname.split(" ")
                    binding.settingsInputName.setText(fullName?.get(0))
                    binding.settingsInputSurname.setText(fullName?.get(1))
                }
            }
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_confirm_change -> {
                val name = binding.settingsInputName.text.toString()
                val surname = binding.settingsInputSurname.text.toString()
                if (name.isEmpty())
                    binding.settingsInputName.error = "Поле не может быть пустым"
                else if (surname.isEmpty())
                    binding.settingsInputSurname.error = "Поле не может быть пустым"
                else {
                    val fullName = "$name $surname"
                    viewModel.changeName(fullName)
                    findNavController().popBackStack()
                }
            }
        }
        return true
    }


}
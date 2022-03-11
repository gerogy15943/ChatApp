package com.example.telegramclone.ui.changeUserName

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.FragmentChangeBioBinding
import com.example.telegramclone2.presentation.ui.activity.MainActivity.MainActivity
import com.example.telegramclone2.presentation.ui.fragments.changeBioFragment.ChangeBioViewModel
import javax.inject.Inject


class ChangeBioFragment : Fragment() {

    lateinit var binding: FragmentChangeBioBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ChangeBioViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (context?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeBioBinding.inflate(layoutInflater, container, false)
        binding.settingsInputBio.setText(viewModel.firebaseUserLiveData.value?.bio)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_confirm_change -> {
                val userBio = binding.settingsInputBio.text.toString()
                if (userBio.isEmpty()) {
                    binding.settingsInputBio.error = "Поле пустое"
                } else {
                    viewModel.changeBio(userBio)
                    findNavController().popBackStack()
                }
            }
            R.id.home -> {
            }
        }
        return true
    }
}
package com.example.telegramclone2.presentation.ui.fragments.chatsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.FragmentChatsBinding
import javax.inject.Inject


class ChatsFragment : Fragment() {

    lateinit var binding: FragmentChatsBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ChatsFragmentViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater)
        (context?.applicationContext as App).appComponent.inject(this)

        /*viewModel.loginState.observe(viewLifecycleOwner, Observer {
            if(!it)
                findNavController().navigate(R.id.enterPhoneFragment)
        })*/


        return binding.root
    }

}
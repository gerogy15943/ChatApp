package com.example.telegramclone2.presentation.ui.fragments.loginFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.FragmentEnterPhoneBinding
import com.example.telegramclone2.presentation.ui.activity.MainActivity.MainActivity
import javax.inject.Inject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentEnterPhoneBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginFragmentViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (context?.applicationContext as App).appComponent.inject(this)
        binding = FragmentEnterPhoneBinding.inflate(layoutInflater)
        binding.registerBtnNext.setOnClickListener {
            register()
        }

        return binding.root
    }



    fun register() {
        if (binding.registerInputEmail.text!!.isEmpty())
            binding.registerInputEmail.error = "Введите email"
        else if (binding.registerInputPassword.text!!.isEmpty())
            binding.registerInputPassword.error = "Введите пароль"
        else {
            val email = binding.registerInputEmail.text.toString()
            val password = binding.registerInputPassword.text.toString()
            viewModel.register(email, password)
        }

    }
}
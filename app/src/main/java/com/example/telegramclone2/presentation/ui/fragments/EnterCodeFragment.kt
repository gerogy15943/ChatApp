package com.example.telegramclone2.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.example.telegramclone2.R
import com.example.telegramclone2.databinding.FragmentEnterCodeBinding
import com.example.telegramclone2.presentation.ui.activity.MainActivity

class EnterCodeFragment : Fragment() {

    lateinit var binding: FragmentEnterCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterCodeBinding.inflate(layoutInflater)


        /*binding.registerInputCode.doAfterTextChanged {
            if(it.length == 6)

        }*/
        return binding.root
    }


}
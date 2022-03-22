package com.example.telegramclone2.presentation.ui.fragments.contactListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.telegramclone2.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    lateinit var binding: FragmentContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContactBinding.inflate(layoutInflater)
        return binding.root
    }

}
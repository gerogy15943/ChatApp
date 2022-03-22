package com.example.telegramclone2.presentation.ui.fragments.settingsFragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.telegramclone2.R
import com.example.telegramclone2.app.App
import com.example.telegramclone2.databinding.FragmentSettingsBinding
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.coroutines.flow.collect
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
        /*viewModel.firebaseUserLiveData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if(it?.photourl!!.isNotEmpty())
                    Picasso.get().load(it?.photourl).into(profileImage)
                settingsBio.setText(it?.bio)
                settingsEmail.setText(it?.email)
                settingsFullname.setText(it?.fullname)
                settingsStatus.setText(it?.status)
                settingsLogin.setText(it?.username)
            }
        })*/

        lifecycleScope.launchWhenStarted {
            viewModel.getUser().collect {
                binding.apply {
                    if(it?.photourl!!.isNotEmpty())
                        Picasso.get().load(it?.photourl).into(profileImage)
                    settingsBio.setText(it?.bio)
                    settingsEmail.setText(it?.email)
                    settingsFullname.setText(it?.fullname)
                    settingsStatus.setText(it?.status)
                    settingsLogin.setText(it?.username)
                }
            }
        }

        binding.settingsBtnChangeLogin.setOnClickListener {
            findNavController().navigate(R.id.changeUserNameFragment)
        }

        binding.settingsBtnChangeBio.setOnClickListener {
            findNavController().navigate(R.id.changeBioFragment)
        }

        binding.settingsChangePhoto.setOnClickListener {changePhotoUser()}
        return binding.root
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600,600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(requireActivity(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.main, menu)}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val uri = CropImage.getActivityResult(data).uri
            viewModel.changePhoto(uri)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_exit -> {
                viewModel.logOut()
            }
            R.id.settings_menu_change_name -> {
                findNavController().navigate(R.id.changeNameFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
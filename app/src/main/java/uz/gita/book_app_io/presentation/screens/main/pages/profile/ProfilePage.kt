package uz.gita.book_app_io.presentation.screens.main.pages.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.book_app_io.MainActivity
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.PageProfileBinding
import uz.gita.book_app_io.presentation.dialogs.ChangeNameDialog
import uz.gita.book_app_io.presentation.viewmodels.ProfileViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.ProfileViewModelImpl
import uz.gita.book_app_io.utils.Constants

// Created by Jamshid Isoqov an 10/26/2022
@AndroidEntryPoint
class ProfilePage : Fragment(R.layout.page_profile) {

    private val binding: PageProfileBinding by viewBinding()

    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl>()

    private var mProfileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.changeNameLiveData.observe(this, changeNameObserver)
        viewModel.changeImageLiveData.observe(this, changeImageObserver)
        viewModel.contactLiveData.observe(this, contactObserver)
        viewModel.supportLiveData.observe(this, supportObserver)
        viewModel.backLiveData.observe(this, backListener)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            imgUser.setOnClickListener {
                viewModel.changeImage()

            }
            tvChangeUserName.setOnClickListener {
                viewModel.changeName()
            }
            tvChangeImageIcon.setOnClickListener {
                viewModel.changeImage()
            }

            tvHelp.setOnClickListener {
                viewModel.helpClicked()
            }
            tvSupportUs.setOnClickListener {
                viewModel.supportClicked()
            }


        }
        viewModel.nameLiveData.observe(viewLifecycleOwner, nameObserver)
        viewModel.imageLiveData.observe(viewLifecycleOwner, imageObserver)
    }

    private val nameObserver = Observer<String> {
        binding.tvUserName.text = it
    }

    private val imageObserver = Observer<String> {
        Glide.with(requireContext())
            .load(it)
            .placeholder(R.drawable.user)
            .into(binding.imgUser)
    }

    private val changeNameObserver = Observer<Unit> {
        val dialog = ChangeNameDialog(requireContext(), viewModel.nameLiveData.value!!)
        dialog.show()
        dialog.setChangeListener {
            viewModel.setName(it)
        }
    }

    private val backListener = Observer<Unit> {
        findNavController().navigateUp()
    }

    private val profileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                viewModel.setImage(uri.toString())
                mProfileUri = uri
            }
        }

    private val changeImageObserver = Observer<Unit> {
        ImagePicker.with(requireActivity())
            .crop()
            .cropOval()
            .maxResultSize(512, 512, true)
            .provider(ImageProvider.BOTH)
            .createIntentFromDialog {
                profileLauncher.launch(it)
            }


    }


    private val contactObserver = Observer<Unit> {
        val intent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.linkedin.com/in/shahrizod-qosimov-8669a6228/")
            )
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(Intent.createChooser(intent, "Choose an Linkedin client :"))
    }

    private val supportObserver = Observer<Unit> {
        Constants.goToPlayMarket(activity as MainActivity)
    }


}
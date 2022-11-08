package uz.gita.book_app_io.presentation.screens.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.ScreenRegisterBinding
import uz.gita.book_app_io.presentation.viewmodels.RegisterViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.RegisterViewModelImpl
import uz.gita.book_app_io.utils.snackBar
import uz.gita.book_app_io.utils.toast

// Created by Jamshid Isoqov an 10/26/2022
@OptIn(FlowPreview::class)
@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_register) {

    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()

    private val viewBinding: ScreenRegisterBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.tvNotRegister.clicks()
            .debounce(100L)
            .onEach {
                viewModel.navigateToLogin()
            }.launchIn(lifecycleScope)

        viewBinding.apply {
            btnRegister.clicks()
                .debounce(100L)
                .onEach {
                    val name = inputName.text.toString()
                    val password = inputPassword.text.toString()
                    val confirmPassword = inputConfirmPassword.text.toString()
                    if (name.isNotEmpty()) {
                        if (password.isNotEmpty()) {
                            if (password != confirmPassword) {
                                snackBar(resources.getString(R.string.error_password_input))
                            } else {
                                viewModel.register(name, password)
                            }
                        } else {
                            toast("Password is required")
                        }
                    } else {
                        toast("Name is required")
                    }
                }.launchIn(lifecycleScope)
        }
    }
}
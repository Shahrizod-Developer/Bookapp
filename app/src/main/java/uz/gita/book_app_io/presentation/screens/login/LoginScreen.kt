package uz.gita.book_app_io.presentation.screens.login

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
import uz.gita.book_app_io.databinding.ScreenLoginBinding
import uz.gita.book_app_io.presentation.viewmodels.LoginViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.LoginViewModelImpl

@OptIn(FlowPreview::class)
@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {

    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()

    private val viewBinding: ScreenLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnLogin.clicks().debounce(100L).onEach {
            viewModel.login(
                viewBinding.inputName.text.toString(),
                viewBinding.inputPassword.text.toString()
            )
        }.launchIn(lifecycleScope)

        viewBinding.tvNotRegister.clicks().debounce(100L).onEach {
            viewModel.navigateToRegister()
        }.launchIn(lifecycleScope)
    }
}
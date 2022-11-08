package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.book_app_io.data.prefs.MySharedPref
import uz.gita.book_app_io.directions.SplashScreenDirection
import uz.gita.book_app_io.presentation.viewmodels.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val direction: SplashScreenDirection,
    private val mySharedPref: MySharedPref
) : SplashViewModel, ViewModel() {
    override fun navigate() {
        viewModelScope.launch {
            if (mySharedPref.password.isEmpty()) {
                direction.navigateToLogin()
            } else {
                direction.navigateToHome()
            }
        }
    }
}
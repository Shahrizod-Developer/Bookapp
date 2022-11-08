package uz.gita.book_app_io.directions.impl

import uz.gita.book_app_io.directions.RegisterScreenDirection
import uz.gita.book_app_io.navigation.Navigator
import uz.gita.book_app_io.presentation.screens.register.RegisterScreenDirections
import javax.inject.Inject

class RegisterScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : RegisterScreenDirection {
    override suspend fun navigateToMain() {
        navigator.navigateTo(RegisterScreenDirections.actionRegisterScreenToMainScreen())
    }

    override suspend fun navigateToLogin() {
        navigator.navigateTo(RegisterScreenDirections.actionRegisterScreenToLoginScreen())
    }
}
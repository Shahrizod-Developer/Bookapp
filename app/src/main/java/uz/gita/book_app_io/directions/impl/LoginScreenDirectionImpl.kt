package uz.gita.book_app_io.directions.impl

import uz.gita.book_app_io.directions.LoginScreenDirection
import uz.gita.book_app_io.navigation.Navigator
import uz.gita.book_app_io.presentation.screens.login.LoginScreenDirections
import javax.inject.Inject

class LoginScreenDirectionImpl @Inject constructor(private val navigator: Navigator) :
    LoginScreenDirection {
    override suspend fun navigateToMain() {
        navigator.navigateTo(LoginScreenDirections.actionLoginScreenToMainScreen())
    }

    override suspend fun navigateToRegister() {
        navigator.navigateTo(LoginScreenDirections.actionLoginScreenToRegisterScreen())
    }
}
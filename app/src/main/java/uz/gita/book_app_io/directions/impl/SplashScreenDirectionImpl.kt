package uz.gita.book_app_io.directions.impl

import uz.gita.book_app_io.directions.SplashScreenDirection
import uz.gita.book_app_io.navigation.Navigator
import uz.gita.book_app_io.presentation.screens.splash.SplashScreenDirections
import javax.inject.Inject

class SplashScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : SplashScreenDirection {
    override suspend fun navigateToHome() {
        navigator.navigateTo(SplashScreenDirections.actionSplashScreenToMainScreen())
    }

    override suspend fun navigateToLogin() {
        navigator.navigateTo(SplashScreenDirections.actionSplashScreenToLoginScreen())
    }
}
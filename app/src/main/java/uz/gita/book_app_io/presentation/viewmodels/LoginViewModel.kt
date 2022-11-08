package uz.gita.book_app_io.presentation.viewmodels

interface LoginViewModel {

    fun login(name: String, password: String)

    fun navigateToRegister()

}
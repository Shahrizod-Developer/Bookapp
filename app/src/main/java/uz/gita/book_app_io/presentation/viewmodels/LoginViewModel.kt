package uz.gita.book_app_io.presentation.viewmodels

// Created by Jamshid Isoqov an 10/27/2022
interface LoginViewModel {

    fun login(name: String, password: String)

    fun navigateToRegister()

}
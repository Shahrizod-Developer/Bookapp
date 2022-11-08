package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.data.prefs.MySharedPref
import uz.gita.book_app_io.data.remote.models.UserData
import uz.gita.book_app_io.directions.LoginScreenDirection
import uz.gita.book_app_io.presentation.viewmodels.LoginViewModel
import uz.gita.book_app_io.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val repository: BookRepository,
    private val mySharedPref: MySharedPref,
    private val direction: LoginScreenDirection,
    private val base: BaseViewModel
) : LoginViewModel, ViewModel(){

    override fun login(name: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            base.loadingSharedFlow.emit(true)
            val res = repository.login(UserData(name = name, password = password))
            res.onError {
                base.errorSharedFlow.emit(it.message!!)
            }.onSuccess {
                mySharedPref.name = name
                mySharedPref.password = password
                mySharedPref.image = it.image
                mySharedPref.id = it.id

                direction.navigateToMain()
            }.onMessage {
                base.messageSharedFlow.emit(it)
            }
            base.loadingSharedFlow.emit(false)
        }
    }

    override fun navigateToRegister() {
        viewModelScope.launch {
            direction.navigateToRegister()
        }
    }
}
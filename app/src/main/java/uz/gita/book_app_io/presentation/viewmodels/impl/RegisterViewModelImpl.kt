package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.data.prefs.MySharedPref
import uz.gita.book_app_io.data.remote.models.UserData
import uz.gita.book_app_io.directions.RegisterScreenDirection
import uz.gita.book_app_io.presentation.viewmodels.RegisterViewModel
import uz.gita.book_app_io.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val repository: BookRepository,
    private val directions: RegisterScreenDirection,
    private val mySharedPref: MySharedPref,
    private val base: BaseViewModel
) : RegisterViewModel, ViewModel() {

    override fun register(name: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            base.loadingSharedFlow.emit(true)
            repository.registerUser(userData = UserData(name = name, password = password))
                .collectLatest {
                    base.loadingSharedFlow.emit(false)
                    it.onSuccess {
                        mySharedPref.name = name
                        mySharedPref.password = password
                        mySharedPref.id = it.id
                        directions.navigateToMain()
                    }.onMessage { message ->
                        base.messageSharedFlow.emit(message)
                    }.onError { error ->
                        base.errorSharedFlow.emit(error.localizedMessage ?: "Unknown error")
                    }
                }
        }
    }

    override fun navigateToLogin() {
        viewModelScope.launch {
            directions.navigateToLogin()
        }
    }
}
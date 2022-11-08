package uz.gita.book_app_io.base.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow

interface BaseViewModel {

    val loadingSharedFlow: MutableSharedFlow<Boolean>

    val messageSharedFlow: MutableSharedFlow<String>

    val errorSharedFlow: MutableSharedFlow<String>

}
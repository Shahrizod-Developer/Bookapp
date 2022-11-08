package uz.gita.book_app_io.base.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow

// Created by Jamshid Isoqov an 10/27/2022
interface BaseViewModel {

    val loadingSharedFlow: MutableSharedFlow<Boolean>

    val messageSharedFlow: MutableSharedFlow<String>

    val errorSharedFlow: MutableSharedFlow<String>

}
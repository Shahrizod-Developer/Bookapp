package uz.gita.book_app_io.presentation.viewmodels

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import uz.gita.book_app_io.data.local.entity.BookEntity

// Created by Jamshid Isoqov an 10/27/2022
interface HomeViewModel {

    val booksStateFlow: SharedFlow<List<BookEntity>>

    fun openBookDetails(bookEntity: BookEntity)

}
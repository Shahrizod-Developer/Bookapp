package uz.gita.book_app_io.presentation.viewmodels

import kotlinx.coroutines.flow.StateFlow
import uz.gita.book_app_io.data.local.entity.BookEntity

// Created by Jamshid Isoqov an 10/27/2022
interface SavedViewModel {

    val getAllSavedBooks: StateFlow<List<BookEntity>>

    fun openBookDetails(bookEntity: BookEntity)

}

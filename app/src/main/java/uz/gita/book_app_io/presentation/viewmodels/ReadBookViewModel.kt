package uz.gita.book_app_io.presentation.viewmodels

import uz.gita.book_app_io.data.local.entity.BookEntity

interface ReadBookViewModel {

    fun updateBook(bookEntity: BookEntity)


}
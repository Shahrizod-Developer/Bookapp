package uz.gita.book_app_io.directions

import uz.gita.book_app_io.data.local.entity.BookEntity

interface BookDetailScreenDirection {

    suspend fun navigateToReadBook(bookEntity: BookEntity)

}
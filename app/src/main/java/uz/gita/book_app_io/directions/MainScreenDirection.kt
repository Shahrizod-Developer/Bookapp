package uz.gita.book_app_io.directions

import uz.gita.book_app_io.data.local.entity.BookEntity

interface MainScreenDirection {

    suspend fun navigateToBookDetails(bookEntity: BookEntity)

}
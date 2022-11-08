package uz.gita.book_app_io.presentation.screens.favorite

import kotlinx.coroutines.flow.SharedFlow
import uz.gita.book_app_io.data.local.entity.BookEntity



interface FavoriteViewModel {

    val booksStateFlow: SharedFlow<List<BookEntity>>

    fun openBookDetails(bookEntity: BookEntity)

}
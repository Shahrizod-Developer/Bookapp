package uz.gita.book_app_io.presentation.viewmodels

import kotlinx.coroutines.flow.StateFlow
import uz.gita.book_app_io.data.local.entity.BookEntity

interface SavedViewModel {

    val getAllSavedBooks: StateFlow<List<BookEntity>>

    fun openBookDetails(bookEntity: BookEntity)

}

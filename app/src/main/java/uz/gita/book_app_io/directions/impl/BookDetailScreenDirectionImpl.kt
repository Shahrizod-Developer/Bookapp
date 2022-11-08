package uz.gita.book_app_io.directions.impl

import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.directions.BookDetailScreenDirection
import uz.gita.book_app_io.navigation.Navigator
import uz.gita.book_app_io.presentation.screens.details.BookDetailsScreenDirections
import javax.inject.Inject

class BookDetailScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : BookDetailScreenDirection {
    override suspend fun navigateToReadBook(bookEntity: BookEntity) {
        navigator.navigateTo(BookDetailsScreenDirections.actionBookDetailsScreenToReadBookScreen(bookEntity))
    }
}
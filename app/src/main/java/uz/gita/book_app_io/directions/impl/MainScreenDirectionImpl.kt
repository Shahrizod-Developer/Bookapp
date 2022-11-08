package uz.gita.book_app_io.directions.impl

import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.directions.MainScreenDirection
import uz.gita.book_app_io.navigation.Navigator
import uz.gita.book_app_io.presentation.screens.main.MainScreenDirections
import javax.inject.Inject

class MainScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : MainScreenDirection {
    override suspend fun navigateToBookDetails(bookEntity: BookEntity) {
        navigator.navigateTo(MainScreenDirections.actionMainScreenToBookDetailsScreen(bookEntity))
    }
}
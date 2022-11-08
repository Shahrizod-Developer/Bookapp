package uz.gita.book_app_io.directions.impl

import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.directions.FavoriteScreenDayraction
import uz.gita.book_app_io.navigation.Navigator
import uz.gita.book_app_io.presentation.screens.main.MainScreenDirections
import javax.inject.Inject

class FavoriteScreenDayractionImpl @Inject constructor(
    private val navigator: Navigator
) : FavoriteScreenDayraction {
    override suspend fun navigateToBookDetails(bookEntity: BookEntity) {
        navigator.navigateTo(MainScreenDirections.actionMainScreenToBookDetailsScreen(bookEntity))
    }
}
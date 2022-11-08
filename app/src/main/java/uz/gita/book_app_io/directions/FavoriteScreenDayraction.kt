package uz.gita.book_app_io.directions

import uz.gita.book_app_io.data.local.entity.BookEntity

//Created By Khayrullo Matkarimov  11/4/2022

interface FavoriteScreenDayraction {
    suspend fun navigateToBookDetails(bookEntity: BookEntity)

}
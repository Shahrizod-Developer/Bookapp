package uz.gita.book_app_io.directions

import uz.gita.book_app_io.data.local.entity.BookEntity

// Created by Jamshid Isoqov an 10/27/2022
interface BookDetailScreenDirection {

    suspend fun navigateToReadBook(bookEntity: BookEntity)

}
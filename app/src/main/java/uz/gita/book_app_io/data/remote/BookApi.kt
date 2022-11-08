package uz.gita.book_app_io.data.remote

import kotlinx.coroutines.flow.Flow
import uz.gita.book_app_io.data.remote.models.BookData
import uz.gita.book_app_io.data.remote.models.ResultData
import uz.gita.book_app_io.data.remote.models.UserData

// Created by Jamshid Isoqov an 10/26/2022
interface BookApi {

    fun getAllBooks(): Flow<ResultData<List<BookData>>>

    suspend fun updateRatings(bookData: BookData): ResultData<BookData>

    suspend fun registerUser(userData: UserData): Flow<ResultData<UserData>>

    suspend fun loginUser(userData: UserData): ResultData<UserData>

    suspend fun updateUser()

    suspend fun uploadImage(path:String):String

}
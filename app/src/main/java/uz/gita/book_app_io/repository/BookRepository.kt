package uz.gita.book_app_io.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.data.remote.models.ResultData
import uz.gita.book_app_io.data.remote.models.UserData
import uz.gita.book_app_io.repository.impl.Result

// Created by Jamshid Isoqov an 10/26/2022
interface BookRepository {

    fun getAllBooks(): Flow<List<BookEntity>>
    fun isDownloaded(bookEntity: BookEntity): Flow<Boolean>

    fun getAllSavedBooks(): Flow<List<BookEntity>>

    fun refreshData(): Flow<ResultData<Boolean>>

    fun downloadBook(bookEntity: BookEntity): Flow<ResultData<Result>>

    suspend fun login(userData: UserData): ResultData<UserData>

    suspend fun registerUser(userData: UserData): Flow<ResultData<UserData>>

    suspend fun updateBook(bookEntity: BookEntity)

    suspend fun getBookById(id: String): Flow<BookEntity>

    suspend fun updateUser()

    suspend fun uploadImage(path:String):String

}
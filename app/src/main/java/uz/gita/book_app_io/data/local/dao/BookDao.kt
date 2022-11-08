package uz.gita.book_app_io.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.book_app_io.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(bookEntity: BookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(list: List<BookEntity>)

    @Update
    suspend fun updateBooks(bookEntity: BookEntity)

    @Delete
    suspend fun deleteBooks(bookEntity: BookEntity)

    @Query("SELECT*FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT*FROM books WHERE isDownload==1")
    fun getAllDownloadBooks(): Flow<List<BookEntity>>

    @Query("SELECT*FROM books WHERE id=:id LIMIT 1")
    fun getBooksById(id: String): Flow<BookEntity>


}
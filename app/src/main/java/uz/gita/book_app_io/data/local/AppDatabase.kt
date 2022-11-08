package uz.gita.book_app_io.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.book_app_io.data.local.dao.BookDao
import uz.gita.book_app_io.data.local.entity.BookEntity

@Database(entities = [BookEntity::class], version = 3, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

}
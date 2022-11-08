package uz.gita.book_app_io.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.book_app_io.data.local.AppDatabase
import uz.gita.book_app_io.data.local.dao.BookDao
import uz.gita.book_app_io.data.prefs.MySharedPref
import uz.gita.book_app_io.data.remote.BookApi
import uz.gita.book_app_io.data.remote.impl.BookApiImpl
import javax.inject.Singleton

// Created by Jamshid Isoqov an 10/26/2022
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "book_data"

    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, DATABASE_NAME)
            .build()

    @[Provides Singleton]
    fun provideBookDao(appDatabase: AppDatabase): BookDao = appDatabase.bookDao()


    @[Provides Singleton]
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    @[Provides Singleton]
    fun provideSharedPrefs(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): MySharedPref =
        MySharedPref(context, sharedPreferences)

    @[Provides Singleton]
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

    @[Provides Singleton]
    fun provideBookApi(fireStore: FirebaseFirestore,mySharedPref: MySharedPref): BookApi = BookApiImpl(fireStore,mySharedPref)

}
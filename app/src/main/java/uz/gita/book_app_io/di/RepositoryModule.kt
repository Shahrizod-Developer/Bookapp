package uz.gita.book_app_io.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.book_app_io.repository.BookRepository
import uz.gita.book_app_io.repository.impl.BookRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindsBookRepository(impl: BookRepositoryImpl): BookRepository
}
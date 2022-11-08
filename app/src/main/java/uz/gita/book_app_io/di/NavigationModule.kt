package uz.gita.book_app_io.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.book_app_io.navigation.NavigationDispatcher
import uz.gita.book_app_io.navigation.NavigationHandler
import uz.gita.book_app_io.navigation.Navigator

// Created by Jamshid Isoqov an 10/9/2022

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun navigator(dispatcher: NavigationDispatcher): Navigator

    @Binds
    fun navigatorHandler(dispatcher: NavigationDispatcher): NavigationHandler
}
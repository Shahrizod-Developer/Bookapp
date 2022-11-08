package uz.gita.book_app_io.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.book_app_io.directions.*
import uz.gita.book_app_io.directions.impl.*

// Created by Jamshid Isoqov an 10/26/2022
@Module
@InstallIn(ViewModelComponent::class)
interface DirectionsModule {


    @Binds
    fun bindSplashScreen(impl: SplashScreenDirectionImpl): SplashScreenDirection

    @Binds
    fun bindLoginScreen(impl: LoginScreenDirectionImpl): LoginScreenDirection
    

    @Binds
    fun bindRegisterScreen(impl: RegisterScreenDirectionImpl): RegisterScreenDirection

    @Binds
    fun bindMainScreen(impl: MainScreenDirectionImpl): MainScreenDirection

    @Binds
    fun bindFavoriteScreen(impl: FavoriteScreenDayractionImpl): FavoriteScreenDayraction


    @Binds
    fun bindBookDetailsScreenImpl(impl: BookDetailScreenDirectionImpl): BookDetailScreenDirection


}
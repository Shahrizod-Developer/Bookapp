package uz.gita.book_app_io.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.base.viewmodel.impl.BaseViewModelImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    @Singleton
    fun provideBaseViewModel(impl: BaseViewModelImpl): BaseViewModel = impl

}
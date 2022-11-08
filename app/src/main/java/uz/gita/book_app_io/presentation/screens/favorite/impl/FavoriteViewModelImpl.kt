package uz.gita.book_app_io.presentation.screens.favorite.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.directions.FavoriteScreenDayraction
import uz.gita.book_app_io.directions.MainScreenDirection
import uz.gita.book_app_io.presentation.screens.favorite.FavoriteViewModel
import uz.gita.book_app_io.repository.BookRepository
import javax.inject.Inject
@HiltViewModel
class FavoriteViewModelImpl @Inject constructor(
    private val repository: BookRepository,
    private val baseViewModel: BaseViewModel,
    private val direction: FavoriteScreenDayraction

) : FavoriteViewModel,ViewModel() {

    override val booksStateFlow = MutableSharedFlow<List<BookEntity>>()

    override fun openBookDetails(bookEntity: BookEntity) {
        viewModelScope.launch {
            direction.navigateToBookDetails(bookEntity)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            baseViewModel.loadingSharedFlow.emit(true)
            repository.refreshData().collectLatest {
                it.onSuccess {

                }.onMessage { message ->
                    baseViewModel.messageSharedFlow.emit(message)
                }.onError { error ->
                    baseViewModel.errorSharedFlow.emit(error.localizedMessage!!)
                }
            }
        }

        viewModelScope.launch {
            repository.getAllBooks().collectLatest {
                baseViewModel.loadingSharedFlow.emit(false)
                booksStateFlow.emit(it)
            }
        }
    }
}
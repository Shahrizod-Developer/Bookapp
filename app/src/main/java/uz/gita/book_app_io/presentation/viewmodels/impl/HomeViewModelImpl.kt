package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.directions.MainScreenDirection
import uz.gita.book_app_io.presentation.viewmodels.HomeViewModel
import uz.gita.book_app_io.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val repository: BookRepository,
    private val baseViewModel: BaseViewModel,
    private val direction: MainScreenDirection
) : HomeViewModel, ViewModel() {

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
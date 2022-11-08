package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.directions.BookDetailScreenDirection
import uz.gita.book_app_io.presentation.viewmodels.BookDetailsViewModel
import uz.gita.book_app_io.repository.BookRepository
import uz.gita.book_app_io.repository.impl.Result
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModelImpl @Inject constructor(
    private val repository: BookRepository,
    private val baseViewModel: BaseViewModel,
    private val direction: BookDetailScreenDirection
) : BookDetailsViewModel, ViewModel() {
    override val bookFlow = MutableSharedFlow<BookEntity>()
    override val isDownload = MutableSharedFlow<Boolean>()

    override fun getBooksById(bookEntity: BookEntity) {
        viewModelScope.launch {
            repository.getBookById(bookEntity.id).collect {
                bookFlow.emit(it)
            }
        }
    }

    override fun isDownloaded(bookEntity: BookEntity) {
        repository.isDownloaded(bookEntity)
        viewModelScope.launch {
            repository.isDownloaded(bookEntity).collect {
                isDownload.emit(it)
            }
        }
    }

    override fun downloadBook(bookEntity: BookEntity) {
        viewModelScope.launch {
            repository.downloadBook(bookEntity).collectLatest {
                it.onSuccess { result ->
                    when (result) {
                        Result.Start -> {

                        }
                        is Result.Progress -> {
                            repository.updateBook(bookEntity.copy(download = ((result.current * 100) / result.total).toInt()))
                        }
                        is Result.Error -> {
                            baseViewModel.errorSharedFlow.emit(result.message)
                        }
                        is Result.End -> {
                            repository.updateBook(
                                bookEntity.copy(
                                    isDownload = 1,
                                    downloadUrl = result.filename
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override fun openReadBook(bookEntity: BookEntity) {
        viewModelScope.launch {
            direction.navigateToReadBook(bookEntity)
        }
    }

    override fun updateBook(bookEntity: BookEntity) {
        viewModelScope.launch {
            repository.updateBook(bookEntity)
        }
    }
}
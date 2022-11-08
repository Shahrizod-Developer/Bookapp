package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.presentation.viewmodels.ReadBookViewModel
import uz.gita.book_app_io.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class ReadBookViewModelImpl @Inject constructor(
    private val repository: BookRepository,
) : ReadBookViewModel, ViewModel() {

    override fun updateBook(bookEntity: BookEntity) {
        viewModelScope.launch {
            repository.updateBook(bookEntity)
        }
    }
}
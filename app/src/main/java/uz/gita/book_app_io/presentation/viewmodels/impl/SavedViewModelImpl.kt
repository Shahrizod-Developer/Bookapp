package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.directions.MainScreenDirection
import uz.gita.book_app_io.presentation.viewmodels.SavedViewModel
import uz.gita.book_app_io.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class SavedViewModelImpl @Inject constructor(
    private val repository: BookRepository,
    private val direction: MainScreenDirection
) : SavedViewModel, ViewModel() {

    override val getAllSavedBooks = MutableStateFlow<List<BookEntity>>(emptyList())

    override fun openBookDetails(bookEntity: BookEntity) {
        viewModelScope.launch {
            direction.navigateToBookDetails(bookEntity)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllSavedBooks().collectLatest {
                getAllSavedBooks.emit(it)
            }
        }
    }
}
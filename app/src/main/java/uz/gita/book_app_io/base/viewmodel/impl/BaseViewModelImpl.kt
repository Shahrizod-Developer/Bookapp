package uz.gita.book_app_io.base.viewmodel.impl

import kotlinx.coroutines.flow.MutableSharedFlow
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import javax.inject.Inject

open class BaseViewModelImpl @Inject constructor() : BaseViewModel {

    override val loadingSharedFlow = MutableSharedFlow<Boolean>()

    override val messageSharedFlow = MutableSharedFlow<String>()

    override val errorSharedFlow = MutableSharedFlow<String>()

}
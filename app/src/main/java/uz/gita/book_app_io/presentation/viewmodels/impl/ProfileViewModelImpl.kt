package uz.gita.book_app_io.presentation.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.data.prefs.MySharedPref
import uz.gita.book_app_io.data.remote.models.UserData
import uz.gita.book_app_io.presentation.viewmodels.ProfileViewModel
import uz.gita.book_app_io.repository.BookRepository
import uz.gita.book_app_io.utils.hasConnection
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val sharedPreference: MySharedPref,
    private val repository: BookRepository,
    private val baseViewModel: BaseViewModel
) : ViewModel(), ProfileViewModel {

    override val nameLiveData: MutableLiveData<String> =
        MutableLiveData(sharedPreference.name)

    override val imageLiveData: MutableLiveData<String> =
        MutableLiveData(sharedPreference.image)

    override val backLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val changeNameLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val changeImageLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val aboutUsLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val contactLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val supportLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun changeName() {
        changeNameLiveData.postValue(Unit)
    }

    override fun changeImage() {
        changeImageLiveData.postValue(Unit)
    }

    override fun aboutClicked() {
        aboutUsLiveData.postValue(Unit)
    }

    override fun helpClicked() {
        contactLiveData.postValue(Unit)
    }

    override fun supportClicked() {
        supportLiveData.postValue(Unit)
    }

    override fun setName(name: String) {
        viewModelScope.launch {
            sharedPreference.name = name
            nameLiveData.value = (name)

        }
    }

    override fun setImage() {

    }

    override fun setImage(str: String) {
        viewModelScope.launch {
            if (hasConnection()) {
                val def = repository.uploadImage(str)
                sharedPreference.image = def
                imageLiveData.postValue(def)
                repository.updateUser()
            } else {
                baseViewModel.messageSharedFlow.emit("No internet connection")
            }
        }
    }

    override fun backClick() {
        backLiveData.postValue(Unit)
    }
}
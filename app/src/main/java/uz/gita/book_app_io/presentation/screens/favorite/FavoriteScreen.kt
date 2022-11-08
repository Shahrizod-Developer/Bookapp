package uz.gita.book_app_io.presentation.screens.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import uz.gita.book_app_io.R
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.databinding.FavoriteScreenBinding
import uz.gita.book_app_io.databinding.PageHomeBinding
import uz.gita.book_app_io.directions.impl.FavoriteScreenDayractionImpl
import uz.gita.book_app_io.presentation.screens.favorite.impl.FavoriteViewModelImpl
import uz.gita.book_app_io.presentation.screens.main.pages.home.BooksAdapter
import uz.gita.book_app_io.presentation.viewmodels.HomeViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.HomeViewModelImpl

@AndroidEntryPoint
class FavoriteScreen : Fragment(R.layout.favorite_screen) {

    private val viewModel: FavoriteViewModel by viewModels<FavoriteViewModelImpl>()

    private val viewBinding: FavoriteScreenBinding by viewBinding()

    private val adapter: BooksAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BooksAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.listBooks.adapter = adapter
        viewModel.booksStateFlow.map {
            val a = ArrayList<BookEntity>()
            it.forEach { it1->
                if(it1.favorite==1){
                    a.add(it1)
                }
            }
            a
        }.onEach {
            if(it.size==0){
                viewBinding.imageSplash.visibility = View.VISIBLE
            }else{
                viewBinding.imageSplash.visibility = View.INVISIBLE

            }
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.setItemClickListener {
            viewModel.openBookDetails(it)
        }

    }

}
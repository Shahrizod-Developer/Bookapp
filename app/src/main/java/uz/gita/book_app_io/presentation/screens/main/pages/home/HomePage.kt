package uz.gita.book_app_io.presentation.screens.main.pages.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.PageHomeBinding
import uz.gita.book_app_io.presentation.viewmodels.HomeViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.HomeViewModelImpl

// Created by Jamshid Isoqov an 10/26/2022
@AndroidEntryPoint
class HomePage : Fragment(R.layout.page_home) {

    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    private val viewBinding: PageHomeBinding by viewBinding()

    private val adapter: BooksAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BooksAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.listBooks.adapter = adapter
        viewModel.booksStateFlow.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.setItemClickListener {
            viewModel.openBookDetails(it)
        }

    }

}
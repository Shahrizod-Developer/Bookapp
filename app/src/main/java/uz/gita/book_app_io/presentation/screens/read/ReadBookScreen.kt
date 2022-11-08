package uz.gita.book_app_io.presentation.screens.read

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.book_app_io.App
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.ScreenReadBookBinding
import uz.gita.book_app_io.presentation.viewmodels.ReadBookViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.ReadBookViewModelImpl
import uz.gita.book_app_io.utils.showErrorDialog
import java.io.File

@AndroidEntryPoint
class ReadBookScreen : Fragment(R.layout.screen_read_book) {


    private val viewModel: ReadBookViewModel by viewModels<ReadBookViewModelImpl>()


    private val args: ReadBookScreenArgs by navArgs()

    private var maxBook = 0
    private val viewBinding: ScreenReadBookBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        maxBook = args.books.currentPage

        viewBinding.tvBookName.text = args.books.name
        viewBinding.tvBookName.setSingleLine()


        viewBinding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
        try {
            val uri = FileProvider.getUriForFile(
                requireContext(), "uz.gita.book_app_io" + ".provider",
                File(App.instance.filesDir, args.books.name + ".pdf")
            )

            val isNight = Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            viewBinding.pdfViewer
                .fromUri(uri)
                .nightMode(isNight)
                .defaultPage(args.books.currentPage)
                .pageSnap(true)
                .onPageScroll { page, _ ->
                    if (page > maxBook) {
                        maxBook = page
                        viewModel.updateBook(args.books.copy(currentPage = maxBook))
                    }
                }
                .load()
        } catch (e: Exception) {
            viewModel.updateBook(
                bookEntity = args.books.copy(
                    isDownload = args.books.isDownload,
                    currentPage = 0
                )
            )
            showErrorDialog(e.message!!) {
                findNavController().navigateUp()
            }
        }
    }
}
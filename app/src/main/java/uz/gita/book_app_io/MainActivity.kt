package uz.gita.book_app_io

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.arefbhrn.eprdownloader.EPRDownloader
import com.arefbhrn.eprdownloader.EPRDownloaderConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.book_app_io.base.viewmodel.BaseViewModel
import uz.gita.book_app_io.navigation.NavigationHandler
import uz.gita.book_app_io.presentation.dialogs.ProgressDialog
import uz.gita.book_app_io.utils.showErrorDialog
import uz.gita.book_app_io.utils.showMessageDialog
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    @Inject
    lateinit var viewModel: BaseViewModel

    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navigationHandler.navigationStack
            .onEach { it.invoke(fragment.findNavController()) }
            .launchIn(lifecycleScope)

        viewModel.loadingSharedFlow.onEach {
            if (it) showProgress() else hideProgress()
        }.launchIn(lifecycleScope)

        viewModel.errorSharedFlow.onEach {
            showErrorDialog(it)
        }.launchIn(lifecycleScope)

        viewModel.messageSharedFlow.onEach {
            showMessageDialog(it)
        }.launchIn(lifecycleScope)

        dialog = ProgressDialog(this)

        val config = EPRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .setDatabaseEnabled(true)
            .build()
        EPRDownloader.initialize(applicationContext, config)
    }

    fun showProgress() {
        dialog.show()
    }


    fun hideProgress() {
        dialog.cancel()
    }
}
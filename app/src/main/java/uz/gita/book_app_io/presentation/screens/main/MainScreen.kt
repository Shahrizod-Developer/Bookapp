package uz.gita.book_app_io.presentation.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.ScreenMainBinding

class MainScreen : Fragment(R.layout.screen_main) {

    private val viewBinding: ScreenMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when (viewBinding.pagerMain.currentItem) {
                0 -> {
                    val a = Intent(Intent.ACTION_MAIN)
                    a.addCategory(Intent.CATEGORY_HOME)
                    a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(a)
                }
                1 -> {
                    viewBinding.pagerMain.currentItem = 0
                }
                2 -> {
                    viewBinding.pagerMain.currentItem = 1
                }
                3 -> {
                    viewBinding.pagerMain.currentItem = 2
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.pagerMain.adapter = MainPagerAdapter(requireActivity())
        viewBinding.pagerMain.isUserInputEnabled = false

        viewBinding.bottomNavBarMain.setOnItemSelectedListener {
            viewBinding.pagerMain.setCurrentItem(
                when (it.itemId) {
                    R.id.home_menu -> {
                        0
                    }
                    R.id.saved_menu -> {
                        1
                    }
                    R.id.star_menu -> {
                        2
                    }
//                    R.id.search_menu -> {
//                        3
//                    }
                    else -> {
                        4
                    }
                }, true
            )
            true
        }

    }


}
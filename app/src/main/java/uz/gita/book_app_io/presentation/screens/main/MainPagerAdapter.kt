package uz.gita.book_app_io.presentation.screens.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.book_app_io.presentation.screens.favorite.FavoriteScreen
import uz.gita.book_app_io.presentation.screens.main.pages.home.HomePage
import uz.gita.book_app_io.presentation.screens.main.pages.profile.ProfilePage
import uz.gita.book_app_io.presentation.screens.main.pages.saved.SavedPage
import uz.gita.book_app_io.presentation.screens.search.SearchScreen

// Created by Jamshid Isoqov an 10/27/2022
class MainPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomePage()
        1 -> SavedPage()
        2 -> FavoriteScreen()
//        3 -> SearchScreen()
        else -> ProfilePage()
    }
}
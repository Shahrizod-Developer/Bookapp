package uz.gita.book_app_io.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// Created by Jamshid Isoqov an 10/27/2022

fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(this.context).inflate(resId, this, false)
}

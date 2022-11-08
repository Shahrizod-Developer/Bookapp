package uz.gita.book_app_io.data.remote.models

import java.util.*

data class UserData(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val password: String,
    val image:String=""
)
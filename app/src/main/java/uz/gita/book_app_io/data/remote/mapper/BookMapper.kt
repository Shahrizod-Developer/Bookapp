package uz.gita.book_app_io.data.remote.mapper

import com.google.firebase.firestore.DocumentSnapshot
import uz.gita.book_app_io.data.remote.models.BookData
import uz.gita.book_app_io.data.remote.models.UserData

// Created by Jamshid Isoqov an 10/26/2022
object BookMapper {
    fun DocumentSnapshot.toBookData() = BookData(
        id = this.id,
        name = this["name"].toString(),
        author = this["author"].toString(),
        pages = this["pages"].toString().toInt(),
        description = this["description"].toString(),
        imageUrl = this["image_url"].toString(),
        storageUrl = this["storage_url"].toString()
    )

    fun DocumentSnapshot.toUserData() = UserData(
        id = id,
        name = this["name"].toString(),
        password = this["password"].toString(),
        image = this["image"].toString()
    )

}
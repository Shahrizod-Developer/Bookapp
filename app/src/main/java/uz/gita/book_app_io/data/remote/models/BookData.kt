package uz.gita.book_app_io.data.remote.models

import uz.gita.book_app_io.data.local.entity.BookEntity

data class BookData(
    val id: String,
    val name: String,
    val author: String,
    val pages: Int,
    val description: String,
    val imageUrl: String,
    val storageUrl: String
) {
    fun toBookEntity() = BookEntity(
        id = id,
        name = name,
        author = author,
        pages = pages,
        description = description,
        imageUrl = imageUrl,
        storageUrl = storageUrl,
    )
}
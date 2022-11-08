package uz.gita.book_app_io.repository.impl

import android.content.Context
import android.os.Environment
import com.arefbhrn.eprdownloader.EPRDownloader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import uz.gita.book_app_io.data.local.dao.BookDao
import uz.gita.book_app_io.data.local.entity.BookEntity
import uz.gita.book_app_io.data.remote.BookApi
import uz.gita.book_app_io.data.remote.models.ResultData
import uz.gita.book_app_io.data.remote.models.UserData
import uz.gita.book_app_io.repository.BookRepository
import uz.gita.book_app_io.utils.hasConnection
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val dao: BookDao,
    private val bookApi: BookApi,
    @ApplicationContext private val mContext: Context
) : BookRepository {

    private val dirPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    override fun getAllBooks(): Flow<List<BookEntity>> = dao.getAllBooks()
    override fun isDownloaded(bookEntity: BookEntity) = flow {
        if (bookEntity.isDownload == 1) {
            emit(true)
        } else {
            emit(false)
        }
    }

    override fun getAllSavedBooks(): Flow<List<BookEntity>> = dao.getAllDownloadBooks()

    override fun refreshData(): Flow<ResultData<Boolean>> = channelFlow {
        bookApi.getAllBooks().collectLatest { result ->
            result.onSuccess { books ->
                dao.insertBooks(books.map { it.toBookEntity() })
                send(ResultData.Success(true))
            }.onMessage {
                send(ResultData.Message(it))
            }.onError {
                send(ResultData.Error(it))
            }
        }
    }

    override fun downloadBook(bookEntity: BookEntity) = callbackFlow<ResultData<Result>> {
        if (hasConnection()) {
            EPRDownloader.download(
                bookEntity.storageUrl,
                mContext.filesDir.path,
                "${bookEntity.name}.pdf"
            )
                .setTag(bookEntity.id)
                .build()
                .addOnStartOrResumeListener {
                    trySend(ResultData.Success(Result.Start))
                }
                .addOnProgressListener {
                    trySend(ResultData.Success(Result.Progress(it.currentBytes, it.totalBytes)))
                }.addOnDownloadListener(object : com.arefbhrn.eprdownloader.OnDownloadListener {
                    override fun onDownloadComplete() {
                        trySend(ResultData.Success(Result.End(bookEntity.name)))
                    }

                    override fun onError(error: com.arefbhrn.eprdownloader.Error?) {
                        trySend(
                            ResultData.Success(
                                Result.Error(
                                    error?.serverErrorMessage ?: "Unknown error"
                                )
                            )
                        )
                    }
                })
                .start()


        } else {
            trySend(ResultData.Success(Result.Error("No internet connection")))
        }
        awaitClose {
        }
    }

    override suspend fun login(userData: UserData): ResultData<UserData> {
        return bookApi.loginUser(userData)
    }

    override suspend fun registerUser(userData: UserData) = bookApi.registerUser(userData)

    override suspend fun updateBook(bookEntity: BookEntity) = dao.updateBooks(bookEntity)

    override suspend fun getBookById(id: String): Flow<BookEntity> = dao.getBooksById(id)

    override suspend fun updateUser() = bookApi.updateUser()

    override suspend fun uploadImage(path: String): String = bookApi.uploadImage(path)
}

sealed interface Result {

    object Start : Result
    class End(val filename: String) : Result
    class Progress(val current: Long, val total: Long) : Result
    class Error(val message: String) : Result

}
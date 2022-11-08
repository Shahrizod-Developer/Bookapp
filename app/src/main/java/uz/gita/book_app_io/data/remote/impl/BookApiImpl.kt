package uz.gita.book_app_io.data.remote.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import uz.gita.book_app_io.data.prefs.MySharedPref
import uz.gita.book_app_io.data.remote.BookApi
import uz.gita.book_app_io.data.remote.mapper.BookMapper.toBookData
import uz.gita.book_app_io.data.remote.mapper.BookMapper.toUserData
import uz.gita.book_app_io.data.remote.models.BookData
import uz.gita.book_app_io.data.remote.models.ResultData
import uz.gita.book_app_io.data.remote.models.UserData
import uz.gita.book_app_io.utils.exceptions.NoInternetConnection
import uz.gita.book_app_io.utils.hasConnection
import java.util.*
import javax.inject.Inject

class BookApiImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val mySharedPref: MySharedPref
) : BookApi {
    override fun getAllBooks() = callbackFlow<ResultData<List<BookData>>> {
        if (hasConnection()) {
            fireStore.collection("books")
                .get()
                .addOnSuccessListener {
                    val list = it.documents.map { dataSnapshot ->
                        dataSnapshot.toBookData()
                    }
                    trySend(ResultData.Success(list))
                }.addOnFailureListener {
                    trySend(ResultData.Error(it))
                }.addOnCanceledListener {
                    trySend(ResultData.Message("Download canceled"))
                }
        } else {
            throw NoInternetConnection()
        }
        awaitClose {
        }
    }.catch { error ->
        emit(ResultData.Error(error))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateRatings(bookData: BookData): ResultData<BookData> {
        return if (hasConnection()) {
            fireStore.collection("books")
                .document(bookData.id)
                .set(bookData)
            ResultData.Success(bookData)

        } else {
            ResultData.Error(NoInternetConnection())
        }
    }

    override suspend fun registerUser(userData: UserData) = callbackFlow<ResultData<UserData>> {
        if (hasConnection()) {
            fireStore
                .collection("users")
                .document(userData.id)
                .set(userData)
                .addOnSuccessListener {
                    trySend(ResultData.Success(userData))
                }
                .addOnFailureListener {
                    trySend(ResultData.Error(it))
                }
        } else {
            trySend(ResultData.Error(NoInternetConnection()))
        }
        awaitClose { }
    }

    override suspend fun loginUser(userData: UserData): ResultData<UserData> {
        return if (hasConnection()) {
            val resultData: ResultData<UserData>
            val list = fireStore.collection("users")
                .whereEqualTo("name", userData.name)
                .whereEqualTo("password", userData.password)
                .limit(1)
                .get()
                .await().documents.map { it.toUserData() }
            resultData = if (list.isEmpty()) {
                ResultData.Message("User not found")
            } else ResultData.Success(list[0])
            resultData
        } else {
            ResultData.Error(NoInternetConnection())
        }
    }

    override suspend fun updateUser() {
        val userData =
            UserData(mySharedPref.id, mySharedPref.name, mySharedPref.password, mySharedPref.image)
        fireStore.collection("users").document(userData.id)
            .update(mapOf("image" to mySharedPref.image))
    }

    override suspend fun uploadImage(path: String): String {

        val fileName = UUID.randomUUID().toString() + ".jpg"


        val deferred = CompletableDeferred<String>()
        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

        refStorage.putFile(Uri.parse(path)).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                val imageUrl = it.toString()
                deferred.complete(imageUrl)
            }
        }
            .addOnFailureListener { e ->
                deferred.completeExceptionally(e)
            }

        return deferred.await()
    }
}
package id.wanztudio.storyapp.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.wanztudio.storyapp.core.extensions.asFlowStateEvent
import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.data.datasources.local.SharedPreferences
import id.wanztudio.storyapp.data.datasources.paging.StoryPagingSource
import id.wanztudio.storyapp.data.datasources.remote.ApiServices
import id.wanztudio.storyapp.data.models.StoryModel
import id.wanztudio.storyapp.data.models.request.PostStoryRequest
import id.wanztudio.storyapp.domain.repositories.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class StoryRepositoryImpl(
    val api: ApiServices,
    val dataStore: SharedPreferences,
    val pagingSource: StoryPagingSource,
) : StoryRepository {

    override suspend fun postStory(request: PostStoryRequest): Flow<ApiResource<String>> {
        val bearerToken = "Bearer ${dataStore.token.first()}"
        val rbDescription = request.description.toRequestBody("text/plain".toMediaType())
        val photoMultipart = MultipartBody.Part.createFormData(
            name = "photo",
            filename = request.photo.name,
            body = try {
                compressImage(request.photo)
            } catch (e: Exception) {
                request.photo
            }.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        return api.addStory(
            bearerToken = bearerToken,
            description = rbDescription,
            photo = photoMultipart
        ).asFlowStateEvent(
            mapper = { it.message ?: "Post Story Success" }
        )
    }

    override fun getStories(): LiveData<PagingData<StoryModel>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { pagingSource }
        ).liveData
    }

    private suspend fun compressImage(file: File): File = withContext(Dispatchers.IO) {
        val bitmap = BitmapFactory.decodeFile(file.path)

        var compressQuality = 100
        var streamLength: Int

        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)

        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

        return@withContext file
    }
}
package id.wanztudio.storyapp.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.data.models.StoryModel
import id.wanztudio.storyapp.data.models.request.PostStoryRequest
import kotlinx.coroutines.flow.Flow


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

interface StoryRepository {

    suspend fun postStory(request: PostStoryRequest): Flow<ApiResource<String>>

    fun getStories(): LiveData<PagingData<StoryModel>>
}
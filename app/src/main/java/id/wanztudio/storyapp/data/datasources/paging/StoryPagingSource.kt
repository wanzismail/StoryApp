package id.wanztudio.storyapp.data.datasources.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.wanztudio.storyapp.data.datasources.local.SharedPreferences
import id.wanztudio.storyapp.data.datasources.remote.ApiServices
import id.wanztudio.storyapp.data.models.StoryModel
import id.wanztudio.storyapp.data.models.response.story.toModel
import kotlinx.coroutines.flow.first


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class StoryPagingSource(
    private val api: ApiServices,
    private val dataStore: SharedPreferences,
) : PagingSource<Int, StoryModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryModel> {
        return try {
            val token = dataStore.token.first()
            val bearerToken = "Bearer $token"

            val page = params.key ?: 1

            val response = api.getStories(
                bearerToken = bearerToken,
                page = page,
                size = params.loadSize
            )

            val dataList = response.body()?.listStory.toModel()

            LoadResult.Page(
                data = dataList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (dataList.isEmpty()) null else page + 1
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryModel>): Int? =
        state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
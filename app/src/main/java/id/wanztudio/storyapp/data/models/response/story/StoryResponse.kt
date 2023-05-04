package id.wanztudio.storyapp.data.models.response.story

import com.google.gson.annotations.SerializedName
import id.wanztudio.storyapp.core.network.ApiResponse
import id.wanztudio.storyapp.data.models.StoryModel


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class StoryResponse(
    @SerializedName("listStory")
    var listStory: List<StoryItem>? = null,
) : ApiResponse()

fun List<StoryItem>?.toModel(): List<StoryModel> = this?.map {
    StoryModel(
        id = it.id.orEmpty(),
        name = it.name.orEmpty(),
        photoUrl = it.photoUrl.orEmpty(),
        description = it.description.orEmpty(),
        latitude = it.lat,
        longitude = it.lon
    )
} ?: emptyList()

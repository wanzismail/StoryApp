package id.wanztudio.storyapp.data.models.response.story

import com.google.gson.annotations.SerializedName


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

data class StoryItem(
    @SerializedName("photoUrl")
    val photoUrl: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("lon")
    val lon: Double? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("lat")
    val lat: Double? = null,
)
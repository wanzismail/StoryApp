package id.wanztudio.storyapp.data.models.request

import java.io.File


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

data class PostStoryRequest(
    val description: String,
    val photo: File,
    val lat: Double? = null,
    val long: Double? = null,
)
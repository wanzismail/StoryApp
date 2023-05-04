package id.wanztudio.storyapp.core.network

import com.google.gson.annotations.SerializedName


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

open class ApiResponse(
    @field:SerializedName("error")
    var error: Boolean? = null,

    @field:SerializedName("message")
    var message: String? = null,
)
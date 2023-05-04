package id.wanztudio.storyapp.core.network

import id.wanztudio.storyapp.core.models.CustomError


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

sealed class ApiResource<T> {
    data class OnSuccess<T>(val data: T?) : ApiResource<T>()
    data class OnError<T>(val error: CustomError) : ApiResource<T>()
}
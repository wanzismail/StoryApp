package id.wanztudio.storyapp.core.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.wanztudio.storyapp.R
import id.wanztudio.storyapp.core.models.CustomError
import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.core.network.ApiResource.OnError
import id.wanztudio.storyapp.core.network.ApiResource.OnSuccess
import id.wanztudio.storyapp.core.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

fun <T, U> Response<T>.asFlowStateEvent(mapper: (T) -> U): Flow<ApiResource<U>> {
    return flow {
        val emitData = try {
            val body = body()
            if (isSuccessful && body != null) {
                val dataMapper = mapper.invoke(body)
                OnSuccess(dataMapper)
            } else {
                OnError(CustomError.Text(message()))
            }
        } catch (e: Throwable) {
            val errorMessage: CustomError = when (e) {
                is HttpException -> {
                    try {
                        val response = Gson().fromJson<ApiResponse>(
                            e.response()?.errorBody()?.charStream(),
                            object : TypeToken<ApiResponse>() {}.type
                        )
                        CustomError.Text(response.message)
                    } catch (e: Exception) {
                        CustomError.Resource(R.string.unknown_error_caption)
                    }
                }
                else -> CustomError.Resource(R.string.unknown_error_caption)
            }
            OnError(errorMessage)
        }
        emit(emitData)
    }
}

inline fun <T, U> Response<T>.asFlowStateEventWithAction(
    crossinline mapper: (T) -> U,
    crossinline action: suspend (T) -> Unit,
): Flow<ApiResource<U>> = flow {
    try {
        val body = body()
        if (isSuccessful && body != null) {
            val dataMapper = mapper.invoke(body)
            action.invoke(body)
            emit(OnSuccess(dataMapper))
        } else {
            emit(OnError(CustomError.Text(message())))
        }
    } catch (e: Throwable) {
        val errorMessage: CustomError = when (e) {
            is HttpException -> {
                try {
                    val response = Gson().fromJson<ApiResponse>(
                        e.response()?.errorBody()?.charStream(),
                        object : TypeToken<ApiResponse>() {}.type
                    )
                    CustomError.Text(response.message)
                } catch (e: Exception) {
                    CustomError.Resource(R.string.unknown_error_caption)
                }
            }
            else -> CustomError.Resource(R.string.unknown_error_caption)
        }
        emit(OnError(errorMessage))
    }
}
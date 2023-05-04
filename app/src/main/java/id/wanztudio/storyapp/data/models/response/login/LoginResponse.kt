package id.wanztudio.storyapp.data.models.response.login

import com.google.gson.annotations.SerializedName
import id.wanztudio.storyapp.core.network.ApiResponse
import id.wanztudio.storyapp.data.models.LoginModel


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

data class LoginResponse(
    @field:SerializedName("loginResult") val loginResult: LoginResult? = null,
) : ApiResponse()

fun LoginResponse.toModel() = LoginModel(
    userId = loginResult?.userId.orEmpty(),
    name = loginResult?.name.orEmpty(),
    token = loginResult?.token.orEmpty()
)



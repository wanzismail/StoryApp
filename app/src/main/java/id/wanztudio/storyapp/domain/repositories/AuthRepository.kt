package id.wanztudio.storyapp.domain.repositories

import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.data.models.LoginModel
import id.wanztudio.storyapp.data.models.request.LoginRequest
import id.wanztudio.storyapp.data.models.request.RegisterRequest
import kotlinx.coroutines.flow.Flow


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

interface AuthRepository {
    suspend fun doLogin(request: LoginRequest): Flow<ApiResource<LoginModel>>

    suspend fun doRegister(request: RegisterRequest): Flow<ApiResource<String>>

    suspend fun doLogout(): Flow<String>
}
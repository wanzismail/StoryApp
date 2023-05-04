package id.wanztudio.storyapp.data.repositories

import id.wanztudio.storyapp.core.extensions.asFlowStateEvent
import id.wanztudio.storyapp.core.extensions.asFlowStateEventWithAction
import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.data.datasources.local.SharedPreferences
import id.wanztudio.storyapp.data.datasources.remote.ApiServices
import id.wanztudio.storyapp.data.models.LoginModel
import id.wanztudio.storyapp.data.models.request.LoginRequest
import id.wanztudio.storyapp.data.models.request.RegisterRequest
import id.wanztudio.storyapp.data.models.response.login.toModel
import id.wanztudio.storyapp.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class AuthRepositoryImpl(
    val api: ApiServices,
    val dataStore: SharedPreferences,
) : AuthRepository {
    override suspend fun doLogin(request: LoginRequest): Flow<ApiResource<LoginModel>> =
        api.login(email = request.email, password = request.password).asFlowStateEventWithAction(
            mapper = { it.toModel() },
            action = { dataStore.saveToken(it.loginResult?.token.orEmpty()) })

    override suspend fun doRegister(request: RegisterRequest): Flow<ApiResource<String>> =
        api.register(name = request.name, email = request.email, password = request.password)
            .asFlowStateEvent(mapper = { it.message ?: "Register Success" })

    override suspend fun doLogout(): Flow<String> {
        dataStore.saveToken("")
        return dataStore.token
    }

}
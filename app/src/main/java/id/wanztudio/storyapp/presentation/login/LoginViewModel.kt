package id.wanztudio.storyapp.presentation.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wanztudio.storyapp.core.models.CustomError
import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.data.models.request.LoginRequest
import id.wanztudio.storyapp.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _errorMessage = MutableLiveData<CustomError>()
    val errorMessage: LiveData<CustomError> = _errorMessage

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.doLogin(LoginRequest(email, password)).onEach { result ->
                when (result) {
                    is ApiResource.OnSuccess<*> -> {
                        _isLoading.value = false
                        _isLoggedIn.value = true
                    }
                    is ApiResource.OnError<*> -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }.launchIn(this)
        }
    }

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    val isLoginEnabled: Flow<Boolean> = combine(_email, _password) { email, password ->
        val isEmailCorrect = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordCorrect = password.isNotEmpty() && password.length > 5
        return@combine isEmailCorrect and isPasswordCorrect
    }
}
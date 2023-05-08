package id.wanztudio.storyapp.presentation.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wanztudio.storyapp.core.models.CustomError
import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.data.models.request.RegisterRequest
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
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean> = _isRegistered

    private val _errorMessage = MutableLiveData<CustomError>()
    val errorMessage: LiveData<CustomError> = _errorMessage

    fun register(request: RegisterRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.doRegister(request).onEach { result ->
                when (result) {
                    is ApiResource.OnSuccess -> {
                        _isLoading.value = false
                        _isRegistered.value = true
                    }
                    is ApiResource.OnError -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }.launchIn(this)
        }
    }

    private val _name = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    fun setName(name: String) {
        _name.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    val isRegisterEnabled: Flow<Boolean> =
        combine(_name, _email, _password) { name, email, password ->
            val isEmailCorrect =
                email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isPasswordCorrect = password.isNotEmpty() && password.length >= 8
            return@combine isEmailCorrect and isPasswordCorrect and name.isNotEmpty()
        }
}
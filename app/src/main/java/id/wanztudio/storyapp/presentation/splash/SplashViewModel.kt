package id.wanztudio.storyapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wanztudio.storyapp.data.datasources.local.SharedPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferences: SharedPreferences,
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private var isLoginStateJob: Job? = null

    init {
        initializeLogin()
    }

    private fun initializeLogin() {
        isLoginStateJob?.cancel()
        isLoginStateJob = viewModelScope.launch {
            val token = preferences.token.first()
            _isLoggedIn.value = token.isNotBlank()
        }
    }

}
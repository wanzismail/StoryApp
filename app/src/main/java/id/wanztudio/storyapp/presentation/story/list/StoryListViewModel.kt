package id.wanztudio.storyapp.presentation.story.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wanztudio.storyapp.data.models.StoryModel
import id.wanztudio.storyapp.domain.repositories.AuthRepository
import id.wanztudio.storyapp.domain.repositories.StoryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@HiltViewModel
class StoryListViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    settingRepository: StoryRepository,
) : ViewModel() {

    val storiesList: LiveData<PagingData<StoryModel>> =
        settingRepository.getStories().cachedIn(viewModelScope)

    private val _isLogout = MutableLiveData<Boolean>()
    val isLogout: LiveData<Boolean> = _isLogout

    private var logoutJob: Job? = null

    fun logout() {
        logoutJob?.cancel()
        logoutJob = viewModelScope.launch {
            authRepository.doLogout().collect { token ->
                _isLogout.value = token.isEmpty()
            }
        }
    }
}
package id.wanztudio.storyapp.presentation.story.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wanztudio.storyapp.core.models.CustomError
import id.wanztudio.storyapp.core.network.ApiResource
import id.wanztudio.storyapp.data.models.LocationModel
import id.wanztudio.storyapp.data.models.request.PostStoryRequest
import id.wanztudio.storyapp.domain.repositories.StoryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@HiltViewModel
class StoryPostViewModel @Inject constructor(
    private val repository: StoryRepository,
) : ViewModel() {

    private val _onProgress = MutableLiveData<Boolean>()
    val onProgress: LiveData<Boolean> = _onProgress

    private val _isUploaded = MutableLiveData<Boolean>()
    val isUploaded: LiveData<Boolean> = _isUploaded

    private val _errorMessage = MutableLiveData<CustomError>()
    val errorMessage: LiveData<CustomError> = _errorMessage

    private val _description = MutableStateFlow("")
    private val _file = MutableStateFlow<File?>(null)
    private val _location = MutableLiveData<LocationModel>()

    fun setDescription(description: String) {
        _description.value = description
    }

    fun setFile(file: File) {
        _file.value = file
    }

    fun setLocation(lat: Double, long: Double) {
        _location.value = LocationModel(lat, long)
    }

    val isPostEnabled: Flow<Boolean> = combine(_file, _description) { file, description ->
        return@combine file != null && description.isNotBlank()
    }

    fun postStory() {
        val request = _location.value?.let {
            PostStoryRequest(description = _description.value,
                photo = _file.value!!,
                lat = it.latitude,
                long = it.longitude)
        } ?: PostStoryRequest(description = _description.value, photo = _file.value!!)

        _onProgress.value = true

        viewModelScope.launch {
            repository.postStory(request).onEach {
                when (it) {
                    is ApiResource.OnSuccess<*> -> {
                        _onProgress.value = false
                        _isUploaded.value = true
                    }
                    is ApiResource.OnError<*> -> {
                        _onProgress.value = false
                        _errorMessage.value = it.error
                    }
                }
            }.launchIn(this)
        }
    }

}
package com.bignerdranch.android.marsphotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.marsphotos.network.MarsApi
import com.bignerdranch.android.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch
import java.lang.Exception

enum class MarsApiStatus { LOADING, ERROR, DONE }

class ViewModel : ViewModel() {
    private var _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> = _photos

    private var _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> get() = _status

    init {
        getMarsPhotos()
    }

    private fun getMarsPhotos() {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                _photos.value = MarsApi.retrofitService.getPhotos()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _photos.value = emptyList()
            }

        }

    }
}

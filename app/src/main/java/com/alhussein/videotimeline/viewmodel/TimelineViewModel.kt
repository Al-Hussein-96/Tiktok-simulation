package com.alhussein.videotimeline.viewmodel

import androidx.lifecycle.*
import com.alhussein.videotimeline.data.repository.DataRepository
import com.alhussein.videotimeline.state.PostsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _uiFlow = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val uiFlow: StateFlow<PostsUiState> = _uiFlow

    init {

        viewModelScope.launch {
            dataRepository.latestPosts.collect {
                _uiFlow.value = PostsUiState.Success(it)
            }
        }
    }


//    fun getDataList() : LiveData<ResultData<List<Post>?>> {
//        return flow {
//            emit(ResultData.Loading())
//            emit(ResultData.Success(dataRepository.latestPosts))
//        }.asLiveData(Dispatchers.IO)
//    }
}
package com.alhussein.videotimeline.viewmodel

import androidx.lifecycle.*
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.model.ResultData
import com.alhussein.videotimeline.remotedatasource.model.PostModel
import com.alhussein.videotimeline.repository.DataRepository
import com.alhussein.videotimeline.state.PostsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _uiFlow = MutableStateFlow<PostsUiState>(PostsUiState.Success(emptyList()))
    val uiFlow: StateFlow<PostsUiState> = _uiFlow

    init {

        viewModelScope.launch {
            _uiFlow.value = PostsUiState.Success(dataRepository.latestPostsCache)
        }
    }


//    fun getDataList() : LiveData<ResultData<List<Post>?>> {
//        return flow {
//            emit(ResultData.Loading())
//            emit(ResultData.Success(dataRepository.latestPosts))
//        }.asLiveData(Dispatchers.IO)
//    }
}
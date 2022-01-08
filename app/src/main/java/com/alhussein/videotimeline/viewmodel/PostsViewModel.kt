package com.alhussein.videotimeline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhussein.videotimeline.data.repository.DataRepository
import com.alhussein.videotimeline.data.repository.FireStoreRepository
import com.alhussein.videotimeline.state.PostsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val fireStoreRepository: FireStoreRepository) : ViewModel() {
    private val _uiFlow = MutableSharedFlow<PostsUiState>(replay = 10)
    val uiFlow: SharedFlow<PostsUiState> = _uiFlow

    init {

        viewModelScope.launch {

//            dataRepository.latestPosts.collect { latestPosts ->
//                _uiFlow.emit(PostsUiState.Loading)
//                _uiFlow.emit(PostsUiState.Success(latestPosts))
//
//            }
        }
    }


}
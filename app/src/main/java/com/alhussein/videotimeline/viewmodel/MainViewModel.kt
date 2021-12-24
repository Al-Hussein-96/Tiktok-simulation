package com.alhussein.videotimeline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhussein.videotimeline.intent.MainIntent
import com.alhussein.videotimeline.viewstate.MainState
import com.alhussein.videotimeline.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository):ViewModel() {
    val postIntent = Channel<MainIntent>(Channel.UNLIMITED)


    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent(){
        viewModelScope.launch {
            postIntent.consumeAsFlow().collect {
                when(it){
                    is MainIntent.FetchPosts -> fetchPost()
                }
            }
        }
    }

    private fun fetchPost(){
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Posts(repository.getPostsData())
            }catch (e: Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }


}
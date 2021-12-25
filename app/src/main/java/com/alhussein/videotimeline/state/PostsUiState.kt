package com.alhussein.videotimeline.state

import com.alhussein.videotimeline.model.Post

sealed class PostsUiState {
    object Loading : PostsUiState()
    data class Success(var posts: List<Post>) : PostsUiState()
    data class Error(var exception: Throwable) : PostsUiState()
}

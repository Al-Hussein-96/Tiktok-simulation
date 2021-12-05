package com.alhussein.videotimeline.viewstate

import com.alhussein.videotimeline.model.Post

sealed class MainState{
    object Idle : MainState()
    object Loading : MainState()
    data class Posts(val posts: List<Post>?) : MainState()
    data class Error(val error:String?) : MainState()
}

package com.alhussein.videotimeline.datasource

import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.remotedatasource.PostService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(private val postService: PostService) {

    val latestPosts: Flow<List<Post>> = flow {
        val latestPosts = postService.getPosts()
        emit(latestPosts)
    }

}
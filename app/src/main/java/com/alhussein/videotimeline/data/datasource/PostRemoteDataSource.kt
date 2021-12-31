package com.alhussein.videotimeline.data.datasource

import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.data.remote.PostService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val postService: PostService,
    private val mock: Mock
) {

    val latestPosts: Flow<List<Post>> = flow {
        val latestPosts = mock.loadMockData()
        emit(latestPosts)
    }

}
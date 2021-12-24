package com.alhussein.videotimeline.repository

import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.remotedatasource.PostService
import javax.inject.Inject

class DataRepository @Inject constructor(private val postService: PostService,private val mock: Mock) {

    suspend fun getPostsData(): List<Post> {
        return postService.getPosts()
    }
}
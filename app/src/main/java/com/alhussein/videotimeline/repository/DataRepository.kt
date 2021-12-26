package com.alhussein.videotimeline.repository

import com.alhussein.videotimeline.datasource.PostRemoteDataSource
import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.remotedatasource.PostService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) {
    private val cachePosts: MutableMap<String, Post> = mutableMapOf()

    val latestPosts: Flow<List<Post>> = postRemoteDataSource.latestPosts.onEach {
        saveInCache(it);
    }

    val latestPostsCache: List<Post> = cachePosts.toList().map {
        it.second
    }

    private fun saveInCache(posts: List<Post>) {
        println("saveOnCache")
        for (post in posts) {
            cachePosts[post.id] = post
        }
    }
}
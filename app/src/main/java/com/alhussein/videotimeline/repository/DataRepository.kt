package com.alhussein.videotimeline.repository

import com.alhussein.videotimeline.datasource.PostRemoteDataSource
import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.remotedatasource.PostService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) {
    val latestPosts: Flow<List<Post>> = postRemoteDataSource.latestPosts


}
package com.alhussein.videotimeline.di

import com.alhussein.videotimeline.datasource.PostRemoteDataSource
import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.remotedatasource.PostService
import com.alhussein.videotimeline.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {


    @Provides
    fun providesPostsRemoteDataSource(postService: PostService): PostRemoteDataSource {
        return PostRemoteDataSource(postService)
    }
}
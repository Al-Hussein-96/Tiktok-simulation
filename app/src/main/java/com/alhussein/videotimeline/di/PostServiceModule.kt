package com.alhussein.videotimeline.di

import com.alhussein.videotimeline.data.remote.PostService
import com.alhussein.videotimeline.data.remote.PostServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*


@InstallIn(SingletonComponent::class)
@Module
class PostServiceModule {

    @Provides
    fun getPostService(httpClient: HttpClient) : PostService{
        return PostServiceImpl(httpClient)
    }
}
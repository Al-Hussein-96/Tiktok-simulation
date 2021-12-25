package com.alhussein.videotimeline.di

import com.alhussein.videotimeline.datasource.PostRemoteDataSource
import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.remotedatasource.PostService
import com.alhussein.videotimeline.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesDataRepository(remoteDataSource: PostRemoteDataSource): DataRepository {
        return DataRepository(remoteDataSource)
    }
}
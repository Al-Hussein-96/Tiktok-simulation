package com.alhussein.videotimeline.di

import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesDataRepository(mock: Mock): DataRepository {
        return DataRepository(mock)
    }
}
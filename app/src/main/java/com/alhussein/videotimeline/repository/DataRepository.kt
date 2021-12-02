package com.alhussein.videotimeline.repository

import com.alhussein.videotimeline.mock.Mock
import com.alhussein.videotimeline.model.PostModel
import javax.inject.Inject

class DataRepository @Inject constructor(private val mock: Mock) {
    fun getPostsData(): ArrayList<PostModel>? {
        return mock.loadMockData()
    }
}
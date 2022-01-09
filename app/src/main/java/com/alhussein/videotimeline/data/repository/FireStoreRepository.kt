package com.alhussein.videotimeline.data.repository

import com.alhussein.videotimeline.data.datasource.FireStoreDataSource
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.state.PostsUiState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FireStoreRepository @Inject constructor(private val fireStoreDataSource: FireStoreDataSource) {

    init {
//        fireStoreDataSource.writeData()
//        fireStoreDataSource.readData()

        suspend {
            fireStoreDataSource.getPosts().collect {

            }
        }
    }


    @ExperimentalCoroutinesApi
    val latestPosts: Flow<List<Post>> = fireStoreDataSource.getPosts()

}
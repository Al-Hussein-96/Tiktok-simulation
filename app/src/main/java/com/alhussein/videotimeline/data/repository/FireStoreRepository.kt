package com.alhussein.videotimeline.data.repository

import com.alhussein.videotimeline.data.datasource.FireStoreDataSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FireStoreRepository @Inject constructor(private val fireStoreDataSource: FireStoreDataSource) {

    init {
//        fireStoreDataSource.writeData()
        fireStoreDataSource.readData()
    }
}
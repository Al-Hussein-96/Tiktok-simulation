package com.alhussein.videotimeline.data.datasource

import com.alhussein.videotimeline.data.remote.model.PostModel
import com.alhussein.videotimeline.data.remote.model.toPost
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.state.PostsUiState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class FireStoreDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    @ExperimentalCoroutinesApi
    fun getPosts(): Flow<PostsUiState> = callbackFlow {

        firebaseFirestore.collection("posts").get().addOnSuccessListener {
            for (post in it) {
                Timber.i("FirebaseFirestore: ${post.toString()}")
            }
        }


        awaitClose {
        }


    }

    fun readData() {
        firebaseFirestore.collection("posts").get().addOnSuccessListener {
            for (post in it) {
                Timber.i("FirebaseFirestore: ${post.toString()}")
            }
        }
    }

    fun writeData() {
        val dataString = "{\n" +
                "    \"_id\": \"61a5e559f342bf115f3ea87b\",\n" +
                "    \"media_base_url\": \"https://shaadoow.net/\",\n" +
                "\n" +
                "    \"recording_details\": {\n" +
                "      \"duration\": 21.4,\n" +
                "      \"cover_img_url\": \"recording/cover_image/jj9Bmh9vwJvDKyKLFjLdstJqI3WW35FppuZVLVDY.jpg\",\n" +
                "      \"type\": \"6\",\n" +
                "      \"description\": \"\",\n" +
                "      \"recording_url\": \"recording/video/EKBbcl833suIDNtHDez2EZ0V87uAE8WYk7ywi1JU.mov\",\n" +
                "      \"status\": 1,\n" +
                "      \"recording_id\": 194797,\n" +
                "      \"streaming_hls\": \"https://stream.mux.com/y2tMUZmZwKMPoP1c5MRYmOePtbEL1YfZf01srEUfvris.m3u8\"\n" +
                "    }\n" +
                "  }"
        // Create a new user with a first and last name
        val post = (Json.decodeFromString(dataString) as PostModel).toPost()

        firebaseFirestore.collection("posts").add(post).addOnSuccessListener { result ->
            Timber.i("Success: ${result.id}")
        }
    }
}
package com.alhussein.videotimeline.remotedatasource

import com.alhussein.videotimeline.model.Post
import dagger.Binds
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import javax.inject.Inject

interface PostService{
    suspend fun getPosts(): List<Post>

}
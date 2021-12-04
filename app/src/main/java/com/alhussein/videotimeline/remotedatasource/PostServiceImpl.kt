package com.alhussein.videotimeline.remotedatasource

import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.remotedatasource.model.PostModel
import com.alhussein.videotimeline.remotedatasource.model.toPost
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject
import kotlin.text.get

class PostServiceImpl @Inject constructor(private val httpClient: HttpClient):PostService {
    override suspend fun getPosts(): List<Post> {
        println("PostServiceImpl")

        return httpClient.let {
            println("URL: " + EndPoints.POSTS)
            it.get(EndPoints.POSTS)
        }
    }

}
package com.alhussein.videotimeline.data.remote

import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.data.remote.model.ResponseModel
import com.alhussein.videotimeline.data.remote.model.toPost
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import javax.inject.Inject

interface PostService {
    suspend fun getPosts(): List<Post>
}

class PostServiceImpl @Inject constructor(private val httpClient: HttpClient) : PostService {


    override suspend fun getPosts(): List<Post> {
        val responseModel: ResponseModel = try {
            httpClient.get(EndPoints.POSTS)
        } catch (e: ResponseException) {
            throw e
        } catch (t: Throwable) {
            return emptyList()
        }
        return responseModel.data.data.map {
            it.toPost()
        }
    }

}
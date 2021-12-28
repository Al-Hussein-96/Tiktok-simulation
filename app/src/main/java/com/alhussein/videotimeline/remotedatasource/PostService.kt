package com.alhussein.videotimeline.remotedatasource

import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.remotedatasource.model.ResponseModel
import com.alhussein.videotimeline.remotedatasource.model.toPost
import dagger.Binds
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
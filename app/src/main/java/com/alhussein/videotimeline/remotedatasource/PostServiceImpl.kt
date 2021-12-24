package com.alhussein.videotimeline.remotedatasource

import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.remotedatasource.model.DataModel
import com.alhussein.videotimeline.remotedatasource.model.PostModel
import com.alhussein.videotimeline.remotedatasource.model.ResponseModel
import com.alhussein.videotimeline.remotedatasource.model.toPost
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.text.get

class PostServiceImpl @Inject constructor(private val httpClient: HttpClient) : PostService {
    override suspend fun getPosts(): List<Post> {
        val responseModel: ResponseModel = httpClient.get(EndPoints.POSTS)

        return responseModel.data.data.map {
            it.toPost()
        }
    }

}
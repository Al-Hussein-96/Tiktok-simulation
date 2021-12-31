package com.alhussein.videotimeline.mock

import android.content.Context
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.data.remote.model.PostModel
import com.alhussein.videotimeline.data.remote.model.toPost
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class Mock @Inject constructor(@ApplicationContext private val context: Context) {
    fun loadMockData(): List<Post> {
        val mockData = context.resources.openRawResource(R.raw.posts_data)
        val dataString = mockData.bufferedReader().readText()

        val posts: List<Post> =
            (Json.decodeFromString(dataString) as List<PostModel>).map { it.toPost() }
//
//        val gson = Gson()
//        val postType = object : TypeToken<ArrayList<Post>>() {}.type
//
//        return gson.fromJson(dataString, postType)
        return posts
    }
}
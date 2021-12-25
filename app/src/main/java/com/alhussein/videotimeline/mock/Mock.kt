package com.alhussein.videotimeline.mock

import android.content.Context
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.model.Post
import javax.inject.Inject

class Mock @Inject constructor(private val context: Context) {
    fun loadMockData(): List<Post> {
//        val mockData = context.resources.openRawResource(R.raw.posts_data)
//        val dataString = mockData.bufferedReader().readText()
//
//        val gson = Gson()
//        val postType = object : TypeToken<ArrayList<Post>>() {}.type
//
//        return gson.fromJson(dataString, postType)
        throw Exception()
    }
}
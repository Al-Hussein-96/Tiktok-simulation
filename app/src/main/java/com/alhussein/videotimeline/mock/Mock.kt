package com.alhussein.videotimeline.mock

import android.content.Context
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.model.PostModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class Mock @Inject constructor(private val context: Context) {
    fun loadMockData(): ArrayList<PostModel>? {
        val mockData = context.resources.openRawResource(R.raw.posts_data)
        val dataString = mockData.bufferedReader().readText()

        val gson = Gson()
        val postType = object : TypeToken<ArrayList<PostModel>>() {}.type

        return gson.fromJson(dataString, postType)

    }
}
package com.alhussein.videotimeline.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataModel(
    @SerialName("data")
    val data: List<PostModel>
)

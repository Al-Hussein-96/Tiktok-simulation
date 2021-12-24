package com.alhussein.videotimeline.remotedatasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataModel(
    @SerialName("data")
    val data: List<PostModel>
)

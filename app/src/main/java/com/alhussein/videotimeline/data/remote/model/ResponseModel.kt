package com.alhussein.videotimeline.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel(
    @SerialName("success")
    val success: String,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: DataModel,
)

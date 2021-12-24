package com.alhussein.videotimeline.remotedatasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ResponseModel(
    @SerialName("success")
    val success: String,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: DataModel,
)

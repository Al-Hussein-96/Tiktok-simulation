package com.alhussein.videotimeline.data.remote.model

import com.alhussein.videotimeline.model.Post
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    @SerialName("_id")
    val id: String,

    @SerialName("media_base_url")
    val media_base_url: String,

    @SerialName("recording_details")
    val recording_details: RecordDetailsModel
)

fun PostModel.toPost() : Post{
    return Post(
        id = id,
        media_base_url = media_base_url,
        recording_details = recording_details.toRecordDetails()
    )
}

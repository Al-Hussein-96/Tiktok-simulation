package com.alhussein.videotimeline.data.remote.model

import com.alhussein.videotimeline.model.RecordDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordDetailsModel(
    @SerialName("duration")
    val duration: Double,

    @SerialName("cover_img_url")
    val cover_img_url: String,

    @SerialName("type")
    val type: Int,

    @SerialName("description")
    val description: String,

    @SerialName("recording_url")
    val recording_url: String,

    @SerialName("status")
    val status: Int,

    @SerialName("recording_id")
    val recording_id: Long,

    @SerialName("streaming_hls")
    val streaming_hls: String
)

public fun RecordDetailsModel.toRecordDetails(): RecordDetails {
    return RecordDetails(
        duration = duration,
        cover_img_url = cover_img_url,
        type = type,
        description = description,
        recording_url = recording_url,
        status = status,
        recording_id = recording_id,
        streaming_hls = streaming_hls,
    )
}

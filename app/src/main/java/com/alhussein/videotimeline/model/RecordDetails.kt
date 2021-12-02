package com.alhussein.videotimeline.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecordDetails(
    val duration: Double,
    val cover_img_url: String,
    val type: Int,
    val description: String,
    val recording_url: String,
    val status: Int,
    val recording_id: Long,
    val streaming_hls: String
) : Parcelable

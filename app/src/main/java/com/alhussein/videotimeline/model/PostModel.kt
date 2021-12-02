package com.alhussein.videotimeline.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostModel(
    val id: String,
    val media_base_url: String,
    val recording_details: RecordDetails
) : Parcelable
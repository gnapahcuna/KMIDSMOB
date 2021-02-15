package com.paiwaddev.kmids.kmidsmobile.data.model

import com.google.gson.annotations.SerializedName

data class Distance (
        @SerializedName("Distance") val Distance: Int,
        @SerializedName("FixDistance") val FixDistance: Int
)
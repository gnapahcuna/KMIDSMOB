package com.paiwaddev.kmids.kmidsmobile.data.model

import com.google.gson.annotations.SerializedName

data class StatusBus (
        @SerializedName("TranscationBusID") val TranscationBusID: Int,
        @SerializedName("PersonID") val PersonID: Int,
        @SerializedName("UpDown") val UpDown: Int,
        @SerializedName("UpDownName") val UpDownName: String,
        @SerializedName("result_status") val result_status: String,
        @SerializedName("result_status1") val result_status1: String
)
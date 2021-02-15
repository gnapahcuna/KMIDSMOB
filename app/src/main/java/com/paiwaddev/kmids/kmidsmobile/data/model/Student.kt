package com.paiwaddev.kmids.kmidsmobile.data.model

import com.google.gson.annotations.SerializedName

data class Student (
        @SerializedName("PersonID") val PersonID: Int,
        @SerializedName("TitleName") val TitleName: String,
        @SerializedName("FirstName") val FirstName: String,
        @SerializedName("LastName") val LastName: String,
        @SerializedName("StatusRegister") val StatusRegister: Int
)
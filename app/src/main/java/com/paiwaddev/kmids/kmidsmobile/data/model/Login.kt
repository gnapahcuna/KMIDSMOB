package com.paiwaddev.kmids.kmidsmobile.data.model

import com.google.gson.annotations.SerializedName

data class Login (
        @SerializedName("MobileMenuID") val MobileMenuID: Int,
        @SerializedName("Menu") val Menu: String,
        @SerializedName("MobileUserID") val MobileUserID: Int,
        @SerializedName("Name") val Name: String,
)
package com.paiwaddev.paiwadpos.data.remote

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpBuilder {
    fun build(): OkHttpClient{
        return OkHttpClient().newBuilder()
            .connectTimeout(60L,TimeUnit.SECONDS)
            .readTimeout(60L,TimeUnit.SECONDS)
            .build()
    }
}
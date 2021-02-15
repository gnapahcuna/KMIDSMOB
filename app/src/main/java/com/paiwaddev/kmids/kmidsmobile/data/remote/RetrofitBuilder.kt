package com.paiwaddev.paiwadpos.data.remote

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitBuilder(val okHttpClient: OkHttpClient,val converterFactory: Converter.Factory,val adapterFactory: CallAdapter.Factory) {
    inline fun <reified T> build(basseUrl: String): T{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(basseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(adapterFactory)
            .build()
            .create(T::class.java)
    }
}
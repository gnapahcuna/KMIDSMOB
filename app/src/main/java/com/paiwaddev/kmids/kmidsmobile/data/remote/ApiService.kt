package com.paiwaddev.paiwadpos.data.remote

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    @Headers( "Content-Type: application/json" )
    @POST("api/Getlogin")
    fun getLogin(@Body body: JsonObject)
            : Observable<List<Login>>

    @POST("api/Getstudentreg")
    fun getStudent(@Body body: JsonObject)
            : Observable<List<Student>>

    @POST("api/Getlocation")
    fun getDistanceLocation(@Body body: JsonObject)
            : Observable<Distance>

    @POST("api/Setinsertparentregister")
    fun insertParent(@Body body: JsonArray)
            : Observable<InsertParent>

    @POST("api/getstatusbus")
    fun getStatusBus(@Body body: JsonArray)
            : Observable<List<StatusBus>>
}
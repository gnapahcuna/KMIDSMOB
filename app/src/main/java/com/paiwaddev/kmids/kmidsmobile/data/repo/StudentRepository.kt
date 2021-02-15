package com.paiwaddev.paiwadpos.data.respository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.*
import io.reactivex.rxjava3.core.Observable

interface StudentRepository {

    fun getStudent(body: JsonObject): Observable<List<Student>>

    fun getDistanceLocat(body: JsonObject): Observable<Distance>

    fun insertParent(body: JsonArray): Observable<InsertParent>

    fun getStatusBus(body: JsonArray): Observable<List<StatusBus>>

    //fun getStatusBusAll(body: JsonArray): Observable<StatusBus>
}
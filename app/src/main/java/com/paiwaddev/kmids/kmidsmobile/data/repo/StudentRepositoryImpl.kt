package com.paiwaddev.paiwadpos.data.respository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.*
import com.paiwaddev.paiwadpos.data.remote.ApiService
import io.reactivex.rxjava3.core.Observable

class StudentRepositoryImpl(private val apiService: ApiService): StudentRepository {

    override fun getStudent(body: JsonObject): Observable<List<Student>> {
        return apiService.getStudent(body).map { it }
    }

    override fun getDistanceLocat(body: JsonObject): Observable<Distance> {
        return apiService.getDistanceLocation(body).map { it }
    }

    override fun insertParent(body: JsonArray): Observable<InsertParent> {
        return apiService.insertParent(body).map { it }
    }

    override fun getStatusBus(body: JsonArray): Observable<List<StatusBus>> {
        return apiService.getStatusBus(body).map { it }
    }

    /*override fun getStatusBusAll(body: JsonArray): Observable<StatusBus> {
        for (item in body){
            //apiService.getStatusBus(item)
        }
        return
    }*/

}
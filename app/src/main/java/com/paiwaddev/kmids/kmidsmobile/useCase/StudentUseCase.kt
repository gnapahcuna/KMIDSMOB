package com.paiwaddev.kmids.kmidsmobile.useCase

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.*
import com.paiwaddev.paiwadpos.data.respository.LoginRepository
import com.paiwaddev.paiwadpos.data.respository.StudentRepository
import io.reactivex.rxjava3.core.Observable

class StudentUseCase(private val repository: StudentRepository) {

    fun processGetStudentUseCase(params: JsonObject): Observable<List<Student>> {
        return  repository.getStudent(params)
    }

    fun processInsertParent(params: JsonArray): Observable<InsertParent>{
        return repository.insertParent(params)
    }

    fun processCheckDistance(params: JsonObject): Observable<Distance>{
        return repository.getDistanceLocat(params)
    }

    fun processGetStatusBus(params: JsonArray): Observable<List<StatusBus>>{
        return repository.getStatusBus(params)
    }
}
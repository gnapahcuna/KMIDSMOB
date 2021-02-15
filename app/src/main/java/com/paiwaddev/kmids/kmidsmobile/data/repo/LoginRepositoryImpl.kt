package com.paiwaddev.paiwadpos.data.respository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Distance
import com.paiwaddev.kmids.kmidsmobile.data.model.InsertParent
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.kmidsmobile.data.model.Student
import com.paiwaddev.paiwadpos.data.remote.ApiService
import io.reactivex.rxjava3.core.Observable

class LoginRepositoryImpl(private val apiService: ApiService): LoginRepository {

    override fun getLogin(body: JsonObject): Observable<List<Login>> {
        return apiService.getLogin(body).map { it }
    }

}
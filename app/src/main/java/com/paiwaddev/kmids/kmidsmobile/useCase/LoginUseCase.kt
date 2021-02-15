package com.paiwaddev.kmids.kmidsmobile.useCase

import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.paiwadpos.data.respository.LoginRepository
import io.reactivex.rxjava3.core.Observable

class LoginUseCase(private val repository: LoginRepository) {
    fun processLoginUseCase(params: JsonObject): Observable<List<Login>> {
        val resp = repository.getLogin(params)
        return resp
    }
}
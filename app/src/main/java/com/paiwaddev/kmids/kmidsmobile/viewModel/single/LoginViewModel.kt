package com.paiwaddev.kmids.kmidsmobile.viewModel.single

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.kmidsmobile.useCase.LoginUseCase
import com.paiwaddev.paiwadpos.data.respository.LoginRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class LoginViewModel(private val loginUseCase: LoginUseCase, private val mContext: Context): ViewModel() {

    private var login = MutableLiveData<List<Login>>()
    private var errorMsg = MutableLiveData<String>()
    private var isSuccess = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()
    private val _isLoading = MutableLiveData<Boolean>(false)

    val sharepref = this.mContext.getSharedPreferences("USER_KMIDS", Context.MODE_PRIVATE)

    fun errorMessage(): LiveData<String> = errorMsg

    fun loginData(): LiveData<List<Login>> = login

    fun getLogin(search: String){
        val body = JsonObject()
        body.addProperty("Search", search)

        _isLoading.value = true

        val disposable = loginUseCase.processLoginUseCase(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                login.value = item

                val menu = ArrayList<String>()
                for (std in item){
                    menu.add(std.MobileMenuID.toString())
                }
                val mySet: Set<String> = HashSet<String>(menu)
                sharepref.edit().putStringSet("MENU_ID", mySet).apply()

                _isLoading.value = false
            }, { error ->
                errorMsg.value = error.localizedMessage
                _isLoading.value = false
            })
        compositeDisposable.add(disposable)
    }

    fun isLoading(): LiveData<Boolean> = _isLoading

    fun onSavedID(id: String){
        sharepref.edit().putString("USER_ID", id).commit()
        isSuccess.value = true
    }

    fun isSavedID(): LiveData<Boolean> = isSuccess

    fun onLogout():LiveData<Boolean>{
        val isLogout = MutableLiveData<Boolean>()

        sharepref.edit().clear().commit()

        isLogout.postValue(true)

        return isLogout
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
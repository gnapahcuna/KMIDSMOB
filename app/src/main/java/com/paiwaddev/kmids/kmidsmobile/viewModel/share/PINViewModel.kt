package com.paiwaddev.kmids.kmidsmobile.viewModel.share

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PINViewModel: ViewModel() {

    private var passwords: ArrayList<Int> = ArrayList()
    private lateinit var sharepref: SharedPreferences
    private lateinit var mContext: Context

    var _mPIN = MutableLiveData<String>()
    var _mIndexPIN = MutableLiveData<Int>()
    var _mLoginFial = MutableLiveData<Boolean>()

    fun getContext(context: Context){
        this.mContext = context
        sharepref = this.mContext.getSharedPreferences("USER_KMIDS", Context.MODE_PRIVATE)
    }

    fun onChekPin(num : Button, size: Int){
        var pinCount = passwords.size

        if (pinCount != size){

            passwords.add(num.text.toString().toInt())

            _mIndexPIN.value = pinCount

            pinCount++
            if(pinCount== size){

                var pass = ""
                passwords.forEach { pass+=it.toString() }

                _mPIN.value = pass

            }
        }
    }

    var mPIN: LiveData<String> = _mPIN

    var mIndexPIN: LiveData<Int> = _mIndexPIN

    var mLoginFail = _mLoginFial

    fun onClearPin(){
        passwords.clear()
        _mLoginFial.value = true
    }

    fun onSavePIN(pin : String): LiveData<Boolean>{
        val editor: SharedPreferences.Editor = sharepref.edit()
        editor.putString("PIN", pin)
        editor.commit()

        val succes = MutableLiveData<Boolean>().apply { value = true }
        return succes
    }

    fun onLoadPIN(): LiveData<String>{
        var pin = MutableLiveData<String>().apply {
            value = sharepref.getString("PIN","")
        }
        return pin
    }

    fun onDelPin(pins: List<View>){
        if(passwords.size>0) {
            if (pins.get(passwords.size - 1).isPressed) {
                pins.get(passwords.size - 1).isPressed = false
                passwords.removeAt(passwords.size - 1)
            }
        }
    }
}
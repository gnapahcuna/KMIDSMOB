package com.paiwaddev.kmids.kmidsmobile.viewModel.single

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {


    fun onChageSwitchTouchID(sharepref: SharedPreferences,isCheck: Boolean): LiveData<Boolean>{

        val editor: SharedPreferences.Editor = sharepref.edit()
        editor.putBoolean("TouchID", isCheck)
        editor.commit()

        val succes = MutableLiveData<Boolean>().apply { value = isCheck }

        return  succes
    }

    fun onLoadIsTouchID(sharepref: SharedPreferences): LiveData<Boolean>{
        var isTouchID = MutableLiveData<Boolean>().apply {
            value = sharepref.getBoolean("TouchID",false)
        }
        return isTouchID
    }
}
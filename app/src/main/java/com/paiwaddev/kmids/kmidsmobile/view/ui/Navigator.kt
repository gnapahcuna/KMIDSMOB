package com.paiwaddev.kmids.kmidsmobile.view.ui

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.paiwaddev.kmids.kmidsmobile.R

class Navigator(private val current: Activity, private val next: Activity) {
    fun onNext(){
        val intent = Intent(current, next::class.java)
        current.startActivity(intent)
        current.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        current.finish()
    }
    fun onNext(keys: String){
        val intent = Intent(current, next::class.java)
        intent.putExtra(SpashScreenActivity.KEYS, SpashScreenActivity.WELCOME_SCREEN)
        current.startActivity(Intent(intent))
        current.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        current.finish()
    }
    fun onBack(){

    }
}
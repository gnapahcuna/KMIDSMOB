package com.paiwaddev.kmids.kmidsmobile.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.PINViewModel



class SpashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: PINViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)

        val manager = this.packageManager
        val info = manager?.getPackageInfo(
                this.packageName, 0
        )

        val versionName = info?.versionName
        val versionNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info?.longVersionCode
        } else {
            info?.versionCode
        }

        val tvVersion: TextView = findViewById(R.id.textView2)
        tvVersion.setText(versionName)


        viewModel =
                ViewModelProvider(this).get(PINViewModel::class.java)

        viewModel.getContext(this)


        Handler().postDelayed(Runnable {
            viewModel.onLoadPIN().observe(this,{ PIN ->
                println(PIN.isEmpty())
                if(PIN.isEmpty()){

                    startActivity(Intent(this, WelcomeActivity::class.java))
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                    finish()

                }else{

                    val intent = Intent(this, LockScreenActivity::class.java)
                    intent.putExtra("PIN",PIN)
                    startActivity(intent)
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                    finish()
                }

            })

        }, 3000)


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    companion object{
        var HOME_SCREEN = "com.paiwaddev.kmids.kmidsmobile.home"
        var WELCOME_SCREEN = "com.paiwaddev.kmids.kmidsmobile.welcome"
        var LOCK_SCREEN = "com.paiwaddev.kmids.kmidsmobile.lock"
        var KEYS = "SCREENS"
    }
}